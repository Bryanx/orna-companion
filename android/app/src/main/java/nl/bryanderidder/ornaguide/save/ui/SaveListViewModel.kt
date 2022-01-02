package nl.bryanderidder.ornaguide.save.ui

import androidx.databinding.Bindable
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import nl.bryanderidder.ornaguide.save.model.Save
import nl.bryanderidder.ornaguide.save.persistence.SaveRepository
import nl.bryanderidder.ornaguide.save.ui.filter.SaveFilter

class SaveListViewModel(
    private val saveRepo: SaveRepository
) : BindingViewModel() {

    val saveList: MutableLiveData<List<Save>> = MutableLiveData()

    private var sessionSaveFilter: SaveFilter = SaveFilter()
    var saveFilter: MutableLiveData<SaveFilter> = MutableLiveData(SaveFilter())

    val allPossibleTiers: MutableLiveData<List<Int>> = MutableLiveData()

    val allPossibleTypes: MutableLiveData<List<String>> = MutableLiveData()

    @get:Bindable
    var resultCount: Int by bindingProperty(0)
        private set

    fun loadItemsAndFilters() {
        loadItems()
        loadFilters()
    }

    private fun loadItems() = viewModelScope.launch {
        delay(200L)
        saveRepo.fetchSaveList().collect {
            val newList = saveFilter.value?.applyFilter(it)
            saveList.postValue(newList)
            resultCount = sessionSaveFilter.countFilterResults(newList)
        }
    }

    private fun loadFilters() = viewModelScope.launch {
        saveRepo.fetchAllPossibleTiers().collect(allPossibleTiers::postValue)
        saveRepo.fetchAllPossibleTypes().collect(allPossibleTypes::postValue)
    }

    fun updateSelectedTiers(tiers: List<String>) {
        sessionSaveFilter.tiers = tiers.map(String::toInt).toList()
        resultCount = sessionSaveFilter.countFilterResults(saveList.value)

    }

    fun updateSelectedTypes(types: List<String>) {
        sessionSaveFilter.types = types
        resultCount = sessionSaveFilter.countFilterResults(saveList.value)
    }

    fun onSubmitFilter(dialog: DialogFragment) {
        saveFilter.value = sessionSaveFilter.copy()
        loadItems()
        dialog.dismiss()
    }

    fun onDismissed() {
        sessionSaveFilter = saveFilter.value?.copy() ?: SaveFilter()
    }
}