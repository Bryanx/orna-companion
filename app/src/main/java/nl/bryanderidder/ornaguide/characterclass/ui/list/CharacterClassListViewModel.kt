package nl.bryanderidder.ornaguide.characterclass.ui.list

import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty
import kotlinx.coroutines.Dispatchers
import nl.bryanderidder.ornaguide.characterclass.model.CharacterClass
import nl.bryanderidder.ornaguide.characterclass.persistence.CharacterClassRepository

class CharacterClassListViewModel(
    repository: CharacterClassRepository
) : BindingViewModel() {

    val characterClassList: LiveData<List<CharacterClass>>

    @get:Bindable
    var toastMessage: String? by bindingProperty(null)
        private set

    @get:Bindable
    var loading: Boolean by bindingProperty(false)
        private set

    init {
        characterClassList = repository.fetchCharacterClassList(
            onStart = { loading = true },
            onComplete = { loading = false },
            onError = { toastMessage = it }
        ).asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)
    }
}