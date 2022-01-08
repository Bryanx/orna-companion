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
import kotlinx.coroutines.delay
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

    @get:Bindable
    var resultCount: Int by bindingProperty(0)
        private set

    val allPossibleTiers: LiveData<List<Int>> by lazy {
        repository.fetchAllPossibleTiers()
            .asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)
    }

    val allPossibleBoosts: LiveData<List<String>> by lazy {
        repository.fetchAllPossibleBoosts()
            .asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)
    }

    val specializationList: MutableLiveData<List<Specialization>> = MutableLiveData()

    var sessionSpecializationFilter: MutableLiveData<SpecializationFilter> = MutableLiveData(SpecializationFilter())
    var specializationFilter: MutableLiveData<SpecializationFilter> = MutableLiveData(SpecializationFilter())

    init {
        loadItems()
    }

    private fun loadItems() = viewModelScope.launch {
        isLoading = true
        delay(200L)
        repository.getSpecializationListFromDb(
            onComplete = { isLoading = false },
            onError = { toastMessage = it }
        ).collect {
            val newList = specializationFilter.value?.applyFilter(it)
            specializationList.postValue(newList)
            resultCount = sessionSpecializationFilter.value?.countFilterResults(newList) ?: 0
        }
    }

    fun updateSelectedTiers(tiers: List<String>) {
        sessionSpecializationFilter.value = sessionSpecializationFilter.value?.copy(tiers = tiers.map(String::toInt).toList())
        resultCount = sessionSpecializationFilter.value?.countFilterResults(specializationList.value) ?: 0
    }

    fun updateSelectedBoosts(boosts: List<String>) {
        sessionSpecializationFilter.value = sessionSpecializationFilter.value?.copy(boosts = boosts)
        resultCount = sessionSpecializationFilter.value?.countFilterResults(specializationList.value) ?: 0
    }

    fun onClearFilters() {
        sessionSpecializationFilter.value = SpecializationFilter()
        resultCount = sessionSpecializationFilter.value?.countFilterResults(specializationList.value) ?: 0
    }

    fun onSubmit(dialog: DialogFragment) {
        specializationFilter.value = sessionSpecializationFilter.value?.copy()
        loadItems()
        dialog.dismiss()
    }

    fun onDismissed() {
        sessionSpecializationFilter.value = specializationFilter.value?.copy() ?: SpecializationFilter()
    }
}
