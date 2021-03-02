package nl.bryanderidder.ornaguide.pet.ui

import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty
import kotlinx.coroutines.Dispatchers
import nl.bryanderidder.ornaguide.pet.model.Pet
import nl.bryanderidder.ornaguide.pet.persistence.PetRepository

class PetListViewModel(repository: PetRepository) : BindingViewModel() {

    var petList: LiveData<List<Pet>>

    @get:Bindable
    var toastMessage: String? by bindingProperty(null)
        private set

    @get:Bindable
    var isLoading: Boolean by bindingProperty(false)
        private set

    init {
        petList = repository.fetchPetList(
            onStart = { isLoading = true },
            onComplete = { isLoading = false },
            onError = { toastMessage = it }
        ).asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)
    }
}
