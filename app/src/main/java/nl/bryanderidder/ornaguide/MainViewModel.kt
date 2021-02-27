package nl.bryanderidder.ornaguide

import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty
import kotlinx.coroutines.Dispatchers
import nl.bryanderidder.ornaguide.characterclass.CharacterClass
import nl.bryanderidder.ornaguide.characterclass.CharacterClassRepository

class MainViewModel(repository: CharacterClassRepository) : BindingViewModel() {

    val characterClassListLiveData: LiveData<List<CharacterClass>>

    @get:Bindable
    var toastMessage: String? by bindingProperty(null)
        private set

    @get:Bindable
    var isLoading: Boolean by bindingProperty(false)
        private set

    init {
        characterClassListLiveData = repository.fetchCharacterClassList(
            onStart = { isLoading = true },
            onComplete = { isLoading = false },
            onError = { toastMessage = it }
        ).asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)
    }
}
