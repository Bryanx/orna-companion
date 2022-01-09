package nl.bryanderidder.ornaguide.item.ui.list

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
import nl.bryanderidder.ornaguide.item.model.Item
import nl.bryanderidder.ornaguide.item.persistence.ItemRepository
import nl.bryanderidder.ornaguide.item.ui.list.filter.ItemFilter
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil

class ItemListViewModel(
    private val repository: ItemRepository,
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

    val allPossibleElements: LiveData<List<String>> by lazy {
        repository.fetchAllPossibleElements()
            .map { it.filter(String::isNotEmpty).toList() }
            .asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)
    }

    val allPossibleEquippedBy: LiveData<List<String>> by lazy {
        repository.fetchAllPossibleEquippedBy()
            .map { it.map(Item.IdNamePair::name).toList() }
            .asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)
    }

    val allPossibleStats: List<String> = Item.STATS

    val allPossibleGives: LiveData<List<String>> by lazy {
        repository.fetchAllPossibleGives()
            .asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)
    }

    val allPossibleCauses: LiveData<List<String>> by lazy {
        repository.fetchAllPossibleCauses()
            .asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)
    }

    val allPossibleImmunities: LiveData<List<String>> by lazy {
        repository.fetchAllPossibleImmunities()
            .asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)
    }
    val allPossibleCures: LiveData<List<String>> by lazy {
        repository.fetchAllPossibleCures()
            .asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)
    }

    val itemList: MutableLiveData<List<Item>> = MutableLiveData()

    var sessionItemFilter: MutableLiveData<ItemFilter> =
        MutableLiveData(ItemFilter(tiers = listOf(sharedPrefs.getDefaultTier())))
    var itemFilter: MutableLiveData<ItemFilter> =
        MutableLiveData(ItemFilter(tiers = listOf(sharedPrefs.getDefaultTier())))

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

    fun updateSelectedTiers(tiers: List<String>) {
        sessionItemFilter.value = sessionItemFilter.value?.copy(tiers = tiers.map(String::toInt).toList())
        resultCount = sessionItemFilter.value?.countFilterResults(itemList.value) ?: 0
    }

    fun updateSelectedType(types: List<String>) {
        sessionItemFilter.value = sessionItemFilter.value?.copy(types = types)
        resultCount = sessionItemFilter.value?.countFilterResults(itemList.value) ?: 0
    }

    fun updateSelectedElement(elements: List<String>) {
        sessionItemFilter.value = sessionItemFilter.value?.copy(elements = elements)
        resultCount = sessionItemFilter.value?.countFilterResults(itemList.value) ?: 0
    }

    fun updateSelectedEquippedByList(equippedByList: List<String>) {
        sessionItemFilter.value = sessionItemFilter.value?.copy(equippedByList = equippedByList)
        resultCount = sessionItemFilter.value?.countFilterResults(itemList.value) ?: 0
    }

    fun updateSelectedStats(stats: List<String>) {
        sessionItemFilter.value = sessionItemFilter.value?.copy(stats = stats)
        resultCount = sessionItemFilter.value?.countFilterResults(itemList.value) ?: 0
    }

    fun updateSelectedGives(gives: List<String>) {
        sessionItemFilter.value = sessionItemFilter.value?.copy(gives = gives)
        resultCount = sessionItemFilter.value?.countFilterResults(itemList.value) ?: 0
    }

    fun updateSelectedCauses(causes: List<String>) {
        sessionItemFilter.value = sessionItemFilter.value?.copy(causes = causes)
        resultCount = sessionItemFilter.value?.countFilterResults(itemList.value) ?: 0
    }

    fun updateSelectedImmunities(immunities: List<String>) {
        sessionItemFilter.value = sessionItemFilter.value?.copy(immunities = immunities)
        resultCount = sessionItemFilter.value?.countFilterResults(itemList.value) ?: 0
    }

    fun updateSelectedCures(cures: List<String>) {
        sessionItemFilter.value = sessionItemFilter.value?.copy(cures = cures)
        resultCount = sessionItemFilter.value?.countFilterResults(itemList.value) ?: 0
    }

    fun onClearFilters() {
        sessionItemFilter.value = ItemFilter()
        resultCount = sessionItemFilter.value?.countFilterResults(itemList.value) ?: 0
    }

    fun onSubmit(dialog: DialogFragment) {
        itemFilter.value = sessionItemFilter.value?.copy()
        loadItems()
        dialog.dismiss()
    }

    fun onDismissed() {
        sessionItemFilter.value = itemFilter.value?.copy() ?: ItemFilter()
    }
}
