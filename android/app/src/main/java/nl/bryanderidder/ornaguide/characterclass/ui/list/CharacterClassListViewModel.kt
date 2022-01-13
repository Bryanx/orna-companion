package nl.bryanderidder.ornaguide.characterclass.ui.list

import androidx.databinding.Bindable
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import nl.bryanderidder.ornaguide.characterclass.model.CharacterClass
import nl.bryanderidder.ornaguide.characterclass.persistence.CharacterClassRepository
import nl.bryanderidder.ornaguide.characterclass.ui.list.filter.CharacterClassFilter
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil
import nl.bryanderidder.ornaguide.shared.util.asLiveDataIO

class CharacterClassListViewModel(
    private val repository: CharacterClassRepository,
    private val sharedPrefs: SharedPrefsUtil
) : BindingViewModel() {

    @get:Bindable
    var toastMessage: String? by bindingProperty(null)
        private set

    @get:Bindable
    var isLoading: Boolean by bindingProperty(false)
        private set

    @get:Bindable
    var resultCount: Int by bindingProperty(0)
        private set

    val allPossibleTiers: LiveData<List<Int>> by lazy {
        repository.fetchAllPossibleTiers().asLiveDataIO(viewModelScope)
    }

    val allPossiblePreferredWeapons: LiveData<List<String>> by lazy {
        repository.fetchAllPossiblePreferredWeapons().asLiveDataIO(viewModelScope)
    }

    val allPossibleCostTypes: List<String> = listOf("Orns", "Money")

    val characterClassList: MutableLiveData<List<CharacterClass>> = MutableLiveData()

    var sessionCharacterClassFilter: MutableLiveData<CharacterClassFilter> =
        MutableLiveData(sharedPrefs.getCharacterClassFilter())
    var characterClassFilter: MutableLiveData<CharacterClassFilter> =
        MutableLiveData(sharedPrefs.getCharacterClassFilter())

    init {
        loadItems()
    }

    private fun loadItems() = viewModelScope.launch {
        isLoading = true
        delay(200L)
        repository.getCharacterClassListFromDb(
            onComplete = { isLoading = false },
            onError = { toastMessage = it }
        ).collect {
            val newList = characterClassFilter.value?.applyFilter(it)
            characterClassList.postValue(newList)
            resultCount = sessionCharacterClassFilter.value?.countFilterResults(newList) ?: 0
        }
    }

    fun updateSelectedTiers(tiers: List<String>) =
        updateFilter(sessionCharacterClassFilter.value?.copy(tiers = tiers.map(String::toInt).toList()))

    fun updateSelectedCostTypes(costTypes: List<String>) =
        updateFilter(sessionCharacterClassFilter.value?.copy(costTypes = costTypes))

    fun updateSelectedPreferredWeapons(preferredWeapons: List<String>) =
        updateFilter(sessionCharacterClassFilter.value?.copy(preferredWeapons = preferredWeapons))

    fun onClearFilters() = updateFilter(CharacterClassFilter())

    fun updateFilter(filter: CharacterClassFilter?) {
        sessionCharacterClassFilter.value = filter
        resultCount = sessionCharacterClassFilter.value?.countFilterResults(characterClassList.value) ?: 0
    }

    fun onSubmit(dialog: DialogFragment) {
        characterClassFilter.value = sessionCharacterClassFilter.value?.copy()
        characterClassFilter.value?.let(sharedPrefs::setCharacterClassFilter)
        loadItems()
        dialog.dismiss()
    }

    fun onDismissed() {
        sessionCharacterClassFilter.value = characterClassFilter.value?.copy() ?: CharacterClassFilter()
    }
}