package nl.bryanderidder.ornaguide.pet.ui.detail

import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import nl.bryanderidder.ornaguide.pet.model.Pet
import nl.bryanderidder.ornaguide.pet.persistence.PetRepository
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil

class PetDetailViewModel(
    private val repository: PetRepository,
    private val sharedPrefsUtil: SharedPrefsUtil
) : BindingViewModel() {

    val pet: MutableLiveData<Pet> = MutableLiveData()

    @get:Bindable
    var toastMessage: String? by bindingProperty(null)
        private set

    fun loadPet() = viewModelScope.launch {
        repository.fetchPet(
            id = sharedPrefsUtil.getPetId(),
            onError = { toastMessage = it })
            .collect {
                pet.postValue(it)
            }
    }
}