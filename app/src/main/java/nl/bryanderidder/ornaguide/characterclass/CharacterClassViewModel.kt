package nl.bryanderidder.ornaguide.characterclass

import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty
import kotlinx.coroutines.Dispatchers
import nl.bryanderidder.ornaguide.shared.SessionViewModel

class CharacterClassViewModel(
    repository: CharacterClassRepository,
    sessionVM: SessionViewModel
) : BindingViewModel() {

    val characterClassLiveData: LiveData<CharacterClass>

    @get:Bindable
    var toastMessage: String? by bindingProperty(null)
        private set

    @get:Bindable
    var isLoading: Boolean by bindingProperty(false)
        private set

    init {
        characterClassLiveData = repository.fetchCharacterClass(
            id = sessionVM.characterClass.value?.id ?: 1,
            onComplete = { isLoading = false },
            onError = { toastMessage = it }
        ).asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)
    }
}
