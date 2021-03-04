package nl.bryanderidder.ornaguide.characterclass.ui

import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import nl.bryanderidder.ornaguide.characterclass.model.CharacterClass
import nl.bryanderidder.ornaguide.characterclass.persistence.CharacterClassRepository
import nl.bryanderidder.ornaguide.characterclass.persistence.CharacterClassRequestBody
import nl.bryanderidder.ornaguide.shared.SessionViewModel

class CharacterClassListViewModel(
    repository: CharacterClassRepository,
    sessionVM: SessionViewModel
) : BindingViewModel() {

    private val characterClassLiveData: LiveData<CharacterClass>

    @get:Bindable
    var toastMessage: String? by bindingProperty(null)
        private set

    @get:Bindable
    var isLoading: Boolean by bindingProperty(false)
        private set

    init {
        val id = sessionVM.characterClass.value?.id ?: 1
        characterClassLiveData = repository.fetchCharacterClassList(
            requestBody = CharacterClassRequestBody(id),
            onStart = { isLoading = true },
            onComplete = { isLoading = false },
            onError = { toastMessage = it }
        ).map { it.first() }
            .asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)
    }
}