package nl.bryanderidder.ornaguide.characterclass.ui.list

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
import nl.bryanderidder.ornaguide.characterclass.model.CharacterClass
import nl.bryanderidder.ornaguide.characterclass.persistence.CharacterClassRepository
import nl.bryanderidder.ornaguide.characterclass.ui.list.filter.CharacterClassFilter
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil

class CharacterClassListViewModel(
    private val repository: CharacterClassRepository,
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

    val allPossibleCostTypes: LiveData<List<String>> = MutableLiveData(listOf("Orns", "Money"))

    val characterClassList: MutableLiveData<List<CharacterClass>> = MutableLiveData()

    private var sessionCharacterClassFilter: CharacterClassFilter =
        CharacterClassFilter(tiers = listOf(sharedPrefs.getDefaultTier()))
    var characterClassFilter: MutableLiveData<CharacterClassFilter> =
        MutableLiveData(CharacterClassFilter(tiers = listOf(sharedPrefs.getDefaultTier())))

    init {
        loadItems()
    }

    private fun loadItems() = viewModelScope.launch {
        repository.getCharacterClassListFromDb(
            onStart = { isLoading = true },
            onComplete = { isLoading = false },
            onError = { toastMessage = it }
        ).collect {
            characterClassList.postValue(characterClassFilter.value?.applyFilter(it))
        }
    }

    fun updateSelectedTiers(tiers: List<String>) {
        sessionCharacterClassFilter.tiers = tiers.map(String::toInt).toList()
    }

    fun updateSelectedCostTypes(costTypes: List<String>) {
        sessionCharacterClassFilter.costTypes = costTypes
    }

    fun onSubmit(dialog: DialogFragment) {
        characterClassFilter.value = sessionCharacterClassFilter.copy()
        loadItems()
        dialog.dismiss()
    }

    fun onDismissed() {
        sessionCharacterClassFilter = characterClassFilter.value?.copy() ?: CharacterClassFilter()
    }
}