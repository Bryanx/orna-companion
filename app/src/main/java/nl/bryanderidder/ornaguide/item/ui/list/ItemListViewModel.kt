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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import nl.bryanderidder.ornaguide.item.model.Item
import nl.bryanderidder.ornaguide.item.persistence.ItemRepository
import nl.bryanderidder.ornaguide.item.ui.list.filter.ItemFilter
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil

class ItemListViewModel(
    repository: ItemRepository,
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

    private var sessionItems = listOf<Item>()
    val itemList: MutableLiveData<List<Item>> = MutableLiveData()

    private var sessionItemFilter: ItemFilter =
        ItemFilter(tiers = listOf(sharedPrefs.getDefaultTier()))
    var itemFilter: MutableLiveData<ItemFilter> =
        MutableLiveData(ItemFilter(tiers = listOf(sharedPrefs.getDefaultTier())))

    init {
        viewModelScope.launch {
            repository.getItemListFromDb(
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
        val filteredItems = itemFilter.value?.applyFilter(sessionItems)
        itemList.postValue(filteredItems)
    }

    fun updateSelectedTiers(tiers: List<String>) {
        sessionItemFilter.tiers = tiers.map(String::toInt).toList()
    }

    fun updateSelectedType(types: List<String>) {
        sessionItemFilter.types = types
    }

    fun updateSelectedElement(elements: List<String>) {
        sessionItemFilter.elements = elements
    }

    fun updateSelectedEquippedByList(equippedByList: List<String>) {
        sessionItemFilter.equippedByList = equippedByList
    }

    fun onSubmit(dialog: DialogFragment) {
        itemFilter.value = sessionItemFilter.copy()
        loadItems()
        dialog.dismiss()
    }

    fun onDismissed() {
        sessionItemFilter = itemFilter.value?.copy() ?: ItemFilter()
    }
}
