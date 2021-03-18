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

class PetListViewModel(repository: PetRepository) : BindingViewModel() {

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

    private var sessionItems = listOf<Pet>()
    val petList: MutableLiveData<List<Pet>> = MutableLiveData()

    private var sessionPetFilter: PetFilter = PetFilter(tiers = listOf(1))
    var petFilter: MutableLiveData<PetFilter> = MutableLiveData(PetFilter(tiers = listOf(1)))

    init {
        viewModelScope.launch {
            repository.fetchPetList(
                onStart = { isLoading = true },
                onComplete = { isLoading = false },
                onError = { toastMessage = it }
            ).collect {
                sessionItems = it
                loadItems()
            }
        }
    }

    private fun loadItems() = viewModelScope.launch {
        val filteredPets = petFilter.value?.applyFilter(sessionItems)
        petList.postValue(filteredPets)
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
