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

    val allPossibleTiers: LiveData<List<Int>> by lazy {
        repository.fetchAllPossibleTiers()
            .asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)
    }

    val petList: MutableLiveData<List<Pet>> = MutableLiveData()

    private var sessionPetFilter: PetFilter =
        PetFilter(tiers = listOf(sharedPrefs.getDefaultTier()))
    var petFilter: MutableLiveData<PetFilter> =
        MutableLiveData(PetFilter(tiers = listOf(sharedPrefs.getDefaultTier())))

    init {
        loadItems()
    }

    private fun loadItems() = viewModelScope.launch {
        repository.getPetListFromDb(
            onStart = { isLoading = true },
            onComplete = { isLoading = false },
            onError = { toastMessage = it }
        ).collect {
            petList.postValue(petFilter.value?.applyFilter(it))
        }
    }

    fun updateSelectedTiers(tiers: List<String>) {
        sessionPetFilter.tiers = tiers.map(String::toInt).toList()
    }

    fun onSubmit(dialog: DialogFragment) {
        petFilter.value = sessionPetFilter.copy()
        loadItems()
        dialog.dismiss()
    }

    fun onDismissed() {
        sessionPetFilter = petFilter.value?.copy() ?: PetFilter()
    }
}
