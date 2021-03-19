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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import nl.bryanderidder.ornaguide.monster.model.Monster
import nl.bryanderidder.ornaguide.monster.persistence.MonsterRepository
import nl.bryanderidder.ornaguide.monster.ui.list.filter.MonsterFilter
import nl.bryanderidder.ornaguide.shared.database.OrnaTypeConverters
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil

class MonsterListViewModel(
    repository: MonsterRepository,
    typeConverter: OrnaTypeConverters,
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
    
    val allPossibleTypes: LiveData<List<String>> by lazy {
        repository.fetchAllPossibleTypes()
            .asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)
    }
    
    val allPossibleSpawns: LiveData<List<String>> by lazy {
        repository.fetchAllPossibleSpawns()
            .map { it.flatMap(typeConverter::toStringList).distinct() }
            .asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)
    }

    private var sessionItems = listOf<Monster>()
    val monsterList: MutableLiveData<List<Monster>> = MutableLiveData()

    private var sessionMonsterFilter: MonsterFilter =
        MonsterFilter(tiers = listOf(sharedPrefs.getDefaultTier()))
    var monsterFilter: MutableLiveData<MonsterFilter> =
        MutableLiveData(MonsterFilter(tiers = listOf(sharedPrefs.getDefaultTier())))

    init {
        viewModelScope.launch {
            repository.fetchMonsterList(
                onStart = { isLoading = true },
                onComplete = { isLoading = false },
                onError = { toastMessage = it }
            ).collect {
                sessionItems = it
                loadItems()
            }
        }
    }
    
    private fun loadItems() = viewModelScope.launch {
        val filteredMonsters = monsterFilter.value?.applyFilter(sessionItems)
        monsterList.postValue(filteredMonsters)
    }
    
    fun updateSelectedTiers(tiers: List<String>) {
        sessionMonsterFilter.tiers = tiers.map(String::toInt).toList()
    }
    
    fun updateSelectedTypes(types: List<String>) {
        sessionMonsterFilter.types = types
    }
    
    fun updateSelectedSpawns(spawns: List<String>) {
        sessionMonsterFilter.spawns = spawns
    }

    fun onSubmit(dialog: DialogFragment) {
        monsterFilter.value = sessionMonsterFilter.copy()
        loadItems()
        dialog.dismiss()
    }

    fun onDismissed() {
        sessionMonsterFilter = monsterFilter.value?.copy() ?: MonsterFilter()
    }
}
