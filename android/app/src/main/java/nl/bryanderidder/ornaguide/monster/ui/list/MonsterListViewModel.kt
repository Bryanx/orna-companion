package nl.bryanderidder.ornaguide.monster.ui.list

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
import nl.bryanderidder.ornaguide.monster.model.Monster
import nl.bryanderidder.ornaguide.monster.persistence.MonsterRepository
import nl.bryanderidder.ornaguide.monster.ui.list.filter.MonsterFilter
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil
import nl.bryanderidder.ornaguide.shared.util.asLiveDataIO

class MonsterListViewModel(
    private val repository: MonsterRepository,
    sharedPrefs: SharedPrefsUtil
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
    
    val allPossibleTypes: LiveData<List<String>> by lazy {
        repository.fetchAllPossibleTypes().asLiveDataIO(viewModelScope)
    }
    
    val allPossibleSpawns: LiveData<List<String>> by lazy {
        repository.fetchAllPossibleSpawns().asLiveDataIO(viewModelScope)
    }

    val allPossibleWeakTos: LiveData<List<String>> by lazy {
        repository.fetchAllPossibleWeakTos().asLiveDataIO(viewModelScope)
    }

    val allPossibleResistantTos: LiveData<List<String>> by lazy {
        repository.fetchAllPossibleResistantTos().asLiveDataIO(viewModelScope)
    }

    val allPossibleImmuneTos: LiveData<List<String>> by lazy {
        repository.fetchAllPossibleImmuneTos().asLiveDataIO(viewModelScope)
    }

    val allPossibleImmuneToStatuses: LiveData<List<String>> by lazy {
        repository.fetchAllPossibleImmuneToStatuses().asLiveDataIO(viewModelScope)
    }

    val allPossibleVulnerableToStatuses: LiveData<List<String>> by lazy {
        repository.fetchAllPossibleVulnerableToStatuses().asLiveDataIO(viewModelScope)
    }

    val monsterList: MutableLiveData<List<Monster>> = MutableLiveData()

    var sessionMonsterFilter: MutableLiveData<MonsterFilter> =
        MutableLiveData(MonsterFilter(tiers = listOf(sharedPrefs.getDefaultTier())))
    var monsterFilter: MutableLiveData<MonsterFilter> =
        MutableLiveData(MonsterFilter(tiers = listOf(sharedPrefs.getDefaultTier())))

    init {
        loadItems()
    }

    private fun loadItems() = viewModelScope.launch {
        isLoading = true
        delay(200L)
        repository.getMonsterListFromDb(
            onComplete = { isLoading = false },
            onError = { toastMessage = it }
        ).collect {
            val newList = monsterFilter.value?.applyFilter(it)
            monsterList.postValue(newList)
            resultCount = sessionMonsterFilter.value?.countFilterResults(newList) ?: 0
        }
    }
    
    fun updateSelectedTiers(tiers: List<String>) =
        updateFilter(sessionMonsterFilter.value?.copy(tiers = tiers.map(String::toInt).toList()))
    
    fun updateSelectedTypes(types: List<String>) =
        updateFilter(sessionMonsterFilter.value?.copy(types = types))
    
    fun updateSelectedSpawns(spawns: List<String>) =
        updateFilter(sessionMonsterFilter.value?.copy(spawns = spawns))

    fun updateSelectedWeakTos(weakTos: List<String>) =
        updateFilter(sessionMonsterFilter.value?.copy(weakTos = weakTos))

    fun updateSelectedResistantTos(resistantTos: List<String>) =
        updateFilter(sessionMonsterFilter.value?.copy(resistantTos = resistantTos))

    fun updateSelectedImmuneTos(immuneTos: List<String>) =
        updateFilter(sessionMonsterFilter.value?.copy(immuneTos = immuneTos))

    fun updateSelectedImmuneToStatuses(immuneToStatuses: List<String>) =
        updateFilter(sessionMonsterFilter.value?.copy(immuneToStatuses = immuneToStatuses))

    fun updateSelectedVulnerableToStatuses(vulnerableToStatuses: List<String>) =
        updateFilter(sessionMonsterFilter.value?.copy(vulnerableToStatuses = vulnerableToStatuses))

    fun onClearFilters() = updateFilter(MonsterFilter())

    fun updateFilter(filter: MonsterFilter?) {
        sessionMonsterFilter.value = filter
        resultCount = sessionMonsterFilter.value?.countFilterResults(monsterList.value) ?: 0
    }

    fun onSubmit(dialog: DialogFragment) {
        monsterFilter.value = sessionMonsterFilter.value?.copy()
        loadItems()
        dialog.dismiss()
    }

    fun onDismissed() {
        sessionMonsterFilter.value = monsterFilter.value?.copy() ?: MonsterFilter()
    }
}
