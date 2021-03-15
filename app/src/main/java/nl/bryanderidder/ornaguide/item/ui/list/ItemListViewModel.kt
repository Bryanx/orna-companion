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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import nl.bryanderidder.ornaguide.item.model.Item
import nl.bryanderidder.ornaguide.item.persistence.ItemRepository
import nl.bryanderidder.ornaguide.item.ui.list.filter.ItemFilter
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil

class ItemListViewModel(
    repository: ItemRepository,
    private val sharedPrefsUtil: SharedPrefsUtil
) : BindingViewModel() {

    @get:Bindable
    var toastMessage: String? by bindingProperty(null)
        private set

    @get:Bindable
    var isLoading: Boolean by bindingProperty(false)
        private set

    private var applyFilter = MutableStateFlow(0)

    val itemList: MutableLiveData<List<Item>> = MutableLiveData()

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
            .map { it.filter(String::isNotEmpty).toList() }
            .asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)
    }

    var itemFilter: MutableLiveData<ItemFilter> = MutableLiveData(ItemFilter())
    var sessionItemFilter: MutableLiveData<ItemFilter> = MutableLiveData(ItemFilter())

    init {
        viewModelScope.launch {
            applyFilter.collectLatest {
                repository.fetchItemList(
                    onStart = { isLoading = true },
                    onComplete = { isLoading = false },
                    onError = { toastMessage = it }
                ).map {
                    val tiers = itemFilter.value?.tiers?.toList() ?: listOf()
                    val types = itemFilter.value?.types?.toList() ?: listOf()
                    val elements = itemFilter.value?.elements?.toList() ?: listOf()
                    it.filter { item -> tiers.isEmpty() || tiers.contains(item.tier) }
                        .filter { item -> types.isEmpty() || types.contains(item.type) }
                        .filter { item -> elements.isEmpty() || elements.contains(item.element) }
                }.collect(itemList::postValue)
            }
        }
    }

    fun updateSelectedTiers(tiers: List<String>) {
        sessionItemFilter.value?.tiers = tiers.map(String::toInt).toList()
    }

    fun updateSelectedType(types: List<String>) {
        sessionItemFilter.value?.types = types
    }
    fun updateSelectedElement(elements: List<String>) {
        sessionItemFilter.value?.elements = elements
    }

    fun onSubmit(dialog: DialogFragment) {
        itemFilter.postValue(sessionItemFilter.value)
        applyFilter.value = applyFilter.value + 1
        dialog.dismiss()
    }
}
