package nl.bryanderidder.ornaguide.item.ui.detail

import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import nl.bryanderidder.ornaguide.item.model.Item
import nl.bryanderidder.ornaguide.item.persistence.ItemRepository
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil

class ItemDetailViewModel(
    private val repository: ItemRepository,
    private val sharedPrefsUtil: SharedPrefsUtil
) : BindingViewModel() {

    val item: MutableLiveData<Item> = MutableLiveData()

    @get:Bindable
    var toastMessage: String? by bindingProperty(null)
        private set

    fun loadItem(onComplete: (Item) -> Unit) = viewModelScope.launch {
        repository.fetchItem(
            id = sharedPrefsUtil.getItemId(),
            onError = { toastMessage = it })
            .collect {
                item.postValue(it)
                onComplete.invoke(it)
            }
    }
}