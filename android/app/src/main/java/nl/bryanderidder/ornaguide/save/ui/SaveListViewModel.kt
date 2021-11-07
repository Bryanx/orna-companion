package nl.bryanderidder.ornaguide.save.ui

import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.BindingViewModel
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

    fun loadItemsAndFilters() {
        loadItems()
        loadFilters()
    }

    private fun loadItems() = viewModelScope.launch {
        delay(200L)
        saveRepo.fetchSaveList().collect {
            saveList.postValue(saveFilter.value?.applyFilter(it))
        }
    }

    private fun loadFilters() = viewModelScope.launch {
        saveRepo.fetchAllPossibleTiers().collect(allPossibleTiers::postValue)
        saveRepo.fetchAllPossibleTypes().collect(allPossibleTypes::postValue)
    }

    fun updateSelectedTiers(tiers: List<String>) {
        sessionSaveFilter.tiers = tiers.map(String::toInt).toList()
    }

    fun updateSelectedTypes(types: List<String>) {
        sessionSaveFilter.types = types
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