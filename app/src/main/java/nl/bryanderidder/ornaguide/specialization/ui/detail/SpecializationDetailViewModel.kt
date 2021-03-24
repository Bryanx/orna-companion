package nl.bryanderidder.ornaguide.specialization.ui.detail

import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import nl.bryanderidder.ornaguide.shared.ui.menu.search.SearchResult
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil
import nl.bryanderidder.ornaguide.specialization.model.Specialization
import nl.bryanderidder.ornaguide.specialization.persistence.SpecializationRepository

class SpecializationDetailViewModel(
    private val repository: SpecializationRepository,
    private val sharedPrefsUtil: SharedPrefsUtil
) : BindingViewModel() {

    val specialization: MutableLiveData<Specialization> = MutableLiveData()

    @get:Bindable
    var toastMessage: String? by bindingProperty(null)
        private set

    fun loadSpecialization() = viewModelScope.launch {
        repository.fetchSpecialization(
            id = sharedPrefsUtil.getSpecializationId(),
            onError = { toastMessage = it })
            .collect {
                specialization.postValue(it)
                sharedPrefsUtil.addToSearchHistory(SearchResult.ofSpecialization(it))
            }
    }
}