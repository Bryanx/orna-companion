package nl.bryanderidder.ornaguide.specialization.ui.list

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
import kotlinx.coroutines.launch
import nl.bryanderidder.ornaguide.specialization.model.Specialization
import nl.bryanderidder.ornaguide.specialization.persistence.SpecializationRepository
import nl.bryanderidder.ornaguide.specialization.ui.list.filter.SpecializationFilter

class SpecializationListViewModel(
    private val repository: SpecializationRepository
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

    val specializationList: MutableLiveData<List<Specialization>> = MutableLiveData()

    private var sessionSpecializationFilter: SpecializationFilter = SpecializationFilter()
    var specializationFilter: MutableLiveData<SpecializationFilter> = MutableLiveData(SpecializationFilter())

    init {
        loadItems()
    }

    private fun loadItems() = viewModelScope.launch {
        repository.getSpecializationListFromDb(
            onStart = { isLoading = true },
            onComplete = { isLoading = false },
            onError = { toastMessage = it }
        ).collect {
            specializationList.postValue(specializationFilter.value?.applyFilter(it))
        }
    }

    fun updateSelectedTiers(tiers: List<String>) {
        sessionSpecializationFilter.tiers = tiers.map(String::toInt).toList()
    }

    fun onSubmit(dialog: DialogFragment) {
        specializationFilter.value = sessionSpecializationFilter.copy()
        loadItems()
        dialog.dismiss()
    }

    fun onDismissed() {
        sessionSpecializationFilter = specializationFilter.value?.copy() ?: SpecializationFilter()
    }
}
