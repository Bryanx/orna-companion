package nl.bryanderidder.ornaguide.pet.ui.list

import androidx.databinding.Bindable
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import nl.bryanderidder.ornaguide.pet.model.Pet
import nl.bryanderidder.ornaguide.pet.persistence.PetRepository
import nl.bryanderidder.ornaguide.pet.ui.list.filter.PetFilter
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil

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
        repository.fetchAllPossibleTiers()
            .asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)
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

    fun updateSelectedTiers(tiers: List<String>) {
        sessionPetFilter.value = sessionPetFilter.value?.copy(tiers = tiers.map(String::toInt).toList())
        resultCount = sessionPetFilter.value?.countFilterResults(petList.value) ?: 0
    }

    fun updateSelectedStats(stats: List<String>) {
        sessionPetFilter.value = sessionPetFilter.value?.copy(stats = stats)
        resultCount = sessionPetFilter.value?.countFilterResults(petList.value) ?: 0
    }

    fun onClearFilters() {
        sessionPetFilter.value = PetFilter()
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
