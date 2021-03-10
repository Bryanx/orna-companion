package nl.bryanderidder.ornaguide.item.ui.list.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.BindingViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import nl.bryanderidder.ornaguide.item.persistence.ItemRepository

class ItemListFilterViewModel(repository: ItemRepository) : BindingViewModel() {

    val allPossibleTiers: LiveData<List<String>> = repository.fetchAllPossibleTiers()
        .map { it.map(Int::toString).toList() }
        .asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)

    val allPossibleTypes: LiveData<List<String>> = repository.fetchAllPossibleTypes()
        .asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)

    val allPossibleElements: LiveData<List<String>> = repository.fetchAllPossibleElements()
        .map { it.filter(String::isNotEmpty).toList() }
        .asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)

    val allPossibleEquippedBy: LiveData<List<String>> = repository.fetchAllPossibleEquippedBy()
        .map { it.filter(String::isNotEmpty).toList() }
        .asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)

}
