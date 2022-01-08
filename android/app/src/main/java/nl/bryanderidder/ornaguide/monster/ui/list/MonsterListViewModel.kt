package nl.bryanderidder.ornaguide.monster.ui.list

import androidx.databinding.Bindable
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import nl.bryanderidder.ornaguide.monster.model.Monster
import nl.bryanderidder.ornaguide.monster.persistence.MonsterRepository
import nl.bryanderidder.ornaguide.monster.ui.list.filter.MonsterFilter
import nl.bryanderidder.ornaguide.shared.database.OrnaTypeConverters
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil

class MonsterListViewModel(
    private val repository: MonsterRepository,
    typeConverter: OrnaTypeConverters,
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
        repository.fetchAllPossibleTiers()
            .asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)
    }
    
    val allPossibleTypes: LiveData<List<String>> by lazy {
        repository.fetchAllPossibleTypes()
            .asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)
    }
    
    val allPossibleSpawns: LiveData<List<String>> by lazy {
        repository.fetchAllPossibleSpawns()
            .map { it.flatMap(typeConverter::toStringList).distinct() }
            .asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)
    }

    val allPossibleWeakTos: LiveData<List<String>> by lazy {
        repository.fetchAllPossibleWeakTos()
            .map { it.flatMap(typeConverter::toStringList).distinct() }
            .asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)
    }

    val allPossibleResistantTos: LiveData<List<String>> by lazy {
        repository.fetchAllPossibleResistantTos()
            .map { it.flatMap(typeConverter::toStringList).distinct() }
            .asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)
    }

    val allPossibleImmuneTos: LiveData<List<String>> by lazy {
        repository.fetchAllPossibleImmuneTos()
            .map { it.flatMap(typeConverter::toStringList).distinct() }
            .asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)
    }

    val allPossibleImmuneToStatuses: LiveData<List<String>> by lazy {
        repository.fetchAllPossibleImmuneToStatuses()
            .map { it.flatMap(typeConverter::toStringList).distinct() }
            .asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)
    }

    val allPossibleVulnerableToStatuses: LiveData<List<String>> by lazy {
        repository.fetchAllPossibleVulnerableToStatuses()
            .map { it.flatMap(typeConverter::toStringList).distinct() }
            .asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)
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
    
    fun updateSelectedTiers(tiers: List<String>) {
        sessionMonsterFilter.value = sessionMonsterFilter.value?.copy(tiers = tiers.map(String::toInt).toList())
        resultCount = sessionMonsterFilter.value?.countFilterResults(monsterList.value) ?: 0
    }
    
    fun updateSelectedTypes(types: List<String>) {
        sessionMonsterFilter.value = sessionMonsterFilter.value?.copy(types = types)
        resultCount = sessionMonsterFilter.value?.countFilterResults(monsterList.value) ?: 0
    }
    
    fun updateSelectedSpawns(spawns: List<String>) {
        sessionMonsterFilter.value = sessionMonsterFilter.value?.copy(spawns = spawns)
        resultCount = sessionMonsterFilter.value?.countFilterResults(monsterList.value) ?: 0
    }

    fun updateSelectedWeakTos(weakTos: List<String>) {
        sessionMonsterFilter.value = sessionMonsterFilter.value?.copy(weakTos = weakTos)
        resultCount = sessionMonsterFilter.value?.countFilterResults(monsterList.value) ?: 0
    }

    fun updateSelectedResistantTos(resistantTos: List<String>) {
        sessionMonsterFilter.value = sessionMonsterFilter.value?.copy(resistantTos = resistantTos)
        resultCount = sessionMonsterFilter.value?.countFilterResults(monsterList.value) ?: 0
    }

    fun updateSelectedImmuneTos(immuneTos: List<String>) {
        sessionMonsterFilter.value = sessionMonsterFilter.value?.copy(immuneTos = immuneTos)
        resultCount = sessionMonsterFilter.value?.countFilterResults(monsterList.value) ?: 0
    }

    fun updateSelectedImmuneToStatuses(immuneToStatuses: List<String>) {
        sessionMonsterFilter.value = sessionMonsterFilter.value?.copy(immuneToStatuses = immuneToStatuses)
        resultCount = sessionMonsterFilter.value?.countFilterResults(monsterList.value) ?: 0
    }

    fun updateSelectedVulnerableToStatuses(vulnerableToStatuses: List<String>) {
        sessionMonsterFilter.value = sessionMonsterFilter.value?.copy(vulnerableToStatuses = vulnerableToStatuses)
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
