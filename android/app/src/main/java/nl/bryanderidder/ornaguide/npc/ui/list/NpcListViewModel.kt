package nl.bryanderidder.ornaguide.npc.ui.list

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
import kotlinx.coroutines.launch
import nl.bryanderidder.ornaguide.npc.model.Npc
import nl.bryanderidder.ornaguide.npc.persistence.NpcRepository
import nl.bryanderidder.ornaguide.npc.ui.list.filter.NpcFilter
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil

class NpcListViewModel(
    private val repository: NpcRepository,
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

    val npcList: MutableLiveData<List<Npc>> = MutableLiveData()

    private var sessionNpcFilter: NpcFilter =
        NpcFilter(tiers = listOf(sharedPrefs.getDefaultTier()))
    var npcFilter: MutableLiveData<NpcFilter> = MutableLiveData(
        NpcFilter(tiers = listOf(sharedPrefs.getDefaultTier())))

    init {
        loadItems()
    }

    private fun loadItems() = viewModelScope.launch {
        isLoading = true
        delay(200L)
        repository.getNpcListFromDb(
            onComplete = { isLoading = false },
            onError = { toastMessage = it }
        ).collect {
            val newList = npcFilter.value?.applyFilter(it)
            npcList.postValue(newList)
            resultCount = sessionNpcFilter.countFilterResults(newList)
        }
    }

    fun updateSelectedTiers(tiers: List<String>) {
        sessionNpcFilter.tiers = tiers.map(String::toInt).toList()
        resultCount = sessionNpcFilter.countFilterResults(npcList.value)
    }

    fun onSubmit(dialog: DialogFragment) {
        npcFilter.value = sessionNpcFilter.copy()
        loadItems()
        dialog.dismiss()
    }

    fun onDismissed() {
        sessionNpcFilter = npcFilter.value?.copy() ?: NpcFilter()
    }
}
