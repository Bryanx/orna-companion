package nl.bryanderidder.ornaguide.characterclass.ui.detail

import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import nl.bryanderidder.ornaguide.characterclass.model.CharacterClass
import nl.bryanderidder.ornaguide.characterclass.persistence.CharacterClassRepository
import nl.bryanderidder.ornaguide.shared.ui.menu.search.SearchResult
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil

class CharacterClassDetailViewModel(
    private val repository: CharacterClassRepository,
    private val sharedPrefsUtil: SharedPrefsUtil
) : BindingViewModel() {

    val characterClass: MutableLiveData<CharacterClass> = MutableLiveData()

    @get:Bindable
    var toastMessage: String? by bindingProperty(null)
        private set

    fun loadCharacterClass() = viewModelScope.launch {
        repository.fetchCharacterClass(
            id = sharedPrefsUtil.getCharacterClassId(),
            onError = { toastMessage = it })
            .collect {
                characterClass.postValue(it)
                sharedPrefsUtil.addToSearchHistory(SearchResult.ofCharacterClass(it))
            }
    }
}