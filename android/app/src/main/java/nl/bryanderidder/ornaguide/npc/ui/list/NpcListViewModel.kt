package nl.bryanderidder.ornaguide.npc.ui.list

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
import nl.bryanderidder.ornaguide.npc.model.Npc
import nl.bryanderidder.ornaguide.npc.persistence.NpcRepository
import nl.bryanderidder.ornaguide.npc.ui.list.filter.NpcFilter
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil
import nl.bryanderidder.ornaguide.shared.util.asLiveDataIO

class NpcListViewModel(
    private val repository: NpcRepository,
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

    val npcList: MutableLiveData<List<Npc>> = MutableLiveData()

    var sessionNpcFilter: MutableLiveData<NpcFilter> =
        MutableLiveData(sharedPrefs.getNpcFilter())
    var npcFilter: MutableLiveData<NpcFilter> =
        MutableLiveData(sharedPrefs.getNpcFilter())

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
            resultCount = sessionNpcFilter.value?.countFilterResults(newList) ?: 0
        }
    }

    fun updateSelectedTiers(tiers: List<String>) =
        updateFilter(sessionNpcFilter.value?.copy(tiers = tiers.map(String::toInt).toList()))

    fun onClearFilters() = updateFilter(NpcFilter())

    fun updateFilter(filter: NpcFilter?) {
        sessionNpcFilter.value = filter
        resultCount = sessionNpcFilter.value?.countFilterResults(npcList.value) ?: 0
    }

    fun onSubmit(dialog: DialogFragment) {
        npcFilter.value = sessionNpcFilter.value?.copy()
        npcFilter.value?.let(sharedPrefs::setNpcFilter)
        loadItems()
        dialog.dismiss()
    }

    fun onDismissed() {
        sessionNpcFilter.value = npcFilter.value?.copy() ?: NpcFilter()
    }
}
