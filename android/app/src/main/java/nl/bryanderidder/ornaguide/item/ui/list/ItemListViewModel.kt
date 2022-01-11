package nl.bryanderidder.ornaguide.item.ui.list

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
import nl.bryanderidder.ornaguide.item.model.Item
import nl.bryanderidder.ornaguide.item.persistence.ItemRepository
import nl.bryanderidder.ornaguide.item.ui.list.filter.ItemFilter
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil
import nl.bryanderidder.ornaguide.shared.util.asLiveDataIO

class ItemListViewModel(
    private val repository: ItemRepository,
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

    val allPossibleTypes: LiveData<List<String>> by lazy {
        repository.fetchAllPossibleTypes().asLiveDataIO(viewModelScope)
    }

    val allPossibleElements: LiveData<List<String>> by lazy {
        repository.fetchAllPossibleElements().asLiveDataIO(viewModelScope)
    }

    val allPossibleEquippedBy: LiveData<List<String>> by lazy {
        repository.fetchAllPossibleEquippedBy().asLiveDataIO(viewModelScope)
    }

    val allPossibleStats: List<String> = Item.STATS

    val allPossibleGives: LiveData<List<String>> by lazy {
        repository.fetchAllPossibleGives().asLiveDataIO(viewModelScope)
    }

    val allPossibleCauses: LiveData<List<String>> by lazy {
        repository.fetchAllPossibleCauses().asLiveDataIO(viewModelScope)
    }

    val allPossibleImmunities: LiveData<List<String>> by lazy {
        repository.fetchAllPossibleImmunities().asLiveDataIO(viewModelScope)
    }
    val allPossibleCures: LiveData<List<String>> by lazy {
        repository.fetchAllPossibleCures().asLiveDataIO(viewModelScope)
    }

    val itemList: MutableLiveData<List<Item>> = MutableLiveData()

    var sessionItemFilter: MutableLiveData<ItemFilter> =
        MutableLiveData(sharedPrefs.getItemFilter())
    var itemFilter: MutableLiveData<ItemFilter> =
        MutableLiveData(sharedPrefs.getItemFilter())

    init {
        loadItems()
    }

    private fun loadItems() = viewModelScope.launch {
        isLoading = true
        delay(200L)
        repository.getItemListFromDb(
            onComplete = { isLoading = false },
            onError = { toastMessage = it }
        ).collect {
            val newList = itemFilter.value?.applyFilter(it)
            itemList.postValue(newList)
            resultCount = sessionItemFilter.value?.countFilterResults(newList) ?: 0
        }
    }

    fun updateSelectedTiers(tiers: List<String>) =
        updateFilter(sessionItemFilter.value?.copy(tiers = tiers.map(String::toInt).toList()))

    fun updateSelectedType(types: List<String>) =
        updateFilter(sessionItemFilter.value?.copy(types = types))

    fun updateSelectedElement(elements: List<String>) =
        updateFilter(sessionItemFilter.value?.copy(elements = elements))

    fun updateSelectedEquippedByList(equippedByList: List<String>) =
        updateFilter(sessionItemFilter.value?.copy(equippedByList = equippedByList))

    fun updateSelectedStats(stats: List<String>) =
        updateFilter(sessionItemFilter.value?.copy(stats = stats))

    fun updateSelectedGives(gives: List<String>) =
        updateFilter(sessionItemFilter.value?.copy(gives = gives))

    fun updateSelectedCauses(causes: List<String>) =
        updateFilter(sessionItemFilter.value?.copy(causes = causes))

    fun updateSelectedImmunities(immunities: List<String>) =
        updateFilter(sessionItemFilter.value?.copy(immunities = immunities))

    fun updateSelectedCures(cures: List<String>) =
        updateFilter(sessionItemFilter.value?.copy(cures = cures))

    fun onClearFilters() = updateFilter(ItemFilter())

    fun updateFilter(filter: ItemFilter?) {
        sessionItemFilter.value = filter
        resultCount = sessionItemFilter.value?.countFilterResults(itemList.value) ?: 0
    }

    fun onSubmit(dialog: DialogFragment) {
        itemFilter.value = sessionItemFilter.value?.copy()
        itemFilter.value?.let(sharedPrefs::setItemFilter)
        loadItems()
        dialog.dismiss()
    }

    fun onDismissed() {
        sessionItemFilter.value = itemFilter.value?.copy() ?: ItemFilter()
    }
}
