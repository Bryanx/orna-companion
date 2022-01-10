package nl.bryanderidder.ornaguide.pet.ui.list

import androidx.databinding.Bindable
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import nl.bryanderidder.ornaguide.pet.model.Pet
import nl.bryanderidder.ornaguide.pet.persistence.PetRepository
import nl.bryanderidder.ornaguide.pet.ui.list.filter.PetFilter
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil
import nl.bryanderidder.ornaguide.shared.util.asLiveDataIO

class PetListViewModel(
    private val repository: PetRepository,
    sharedPrefs: SharedPrefsUtil
) : BindingViewModel() {

    @get:Bindable
    var toastMessage: String? by bindingProperty(null)
        private set

    @get:Bindable
    var isLoading: Boolean by bindingProperty(false)
        private set

    @get:Bindable
    var resultCount: Int by bindingProperty(0)
        private set

    val allPossibleTiers: LiveData<List<Int>> by lazy {
        repository.fetchAllPossibleTiers().asLiveDataIO(viewModelScope)
    }

    val allPossibleStats: List<String> = Pet.STATS

    val petList: MutableLiveData<List<Pet>> = MutableLiveData()

    var sessionPetFilter: MutableLiveData<PetFilter> =
        MutableLiveData(PetFilter(tiers = listOf(sharedPrefs.getDefaultTier())))
    var petFilter: MutableLiveData<PetFilter> =
        MutableLiveData(PetFilter(tiers = listOf(sharedPrefs.getDefaultTier())))

    init {
        loadItems()
    }

    private fun loadItems() = viewModelScope.launch {
        isLoading = true
        delay(200L)
        repository.getPetListFromDb(
            onComplete = { isLoading = false },
            onError = { toastMessage = it }
        ).collect {
            val newList = petFilter.value?.applyFilter(it)
            petList.postValue(newList)
            resultCount = sessionPetFilter.value?.countFilterResults(newList) ?: 0
        }
    }

    fun updateSelectedTiers(tiers: List<String>) =
        updateFilter(sessionPetFilter.value?.copy(tiers = tiers.map(String::toInt).toList()))

    fun updateSelectedStats(stats: List<String>) =
        updateFilter(sessionPetFilter.value?.copy(stats = stats))

    fun onClearFilters() = updateFilter(PetFilter())

    fun updateFilter(filter: PetFilter?) {
        sessionPetFilter.value = filter
        resultCount = sessionPetFilter.value?.countFilterResults(petList.value) ?: 0
    }

    fun onSubmit(dialog: DialogFragment) {
        petFilter.value = sessionPetFilter.value?.copy()
        loadItems()
        dialog.dismiss()
    }

    fun onDismissed() {
        sessionPetFilter.value = petFilter.value?.copy() ?: PetFilter()
    }
}
