package nl.bryanderidder.ornaguide.specialization.ui

import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty
import kotlinx.coroutines.Dispatchers
import nl.bryanderidder.ornaguide.specialization.model.Specialization
import nl.bryanderidder.ornaguide.specialization.persistence.SpecializationRepository

class SpecializationListViewModel(repository: SpecializationRepository) : BindingViewModel() {

    var specializationList: LiveData<List<Specialization>>

    @get:Bindable
    var toastMessage: String? by bindingProperty(null)
        private set

    @get:Bindable
    var isLoading: Boolean by bindingProperty(false)
        private set

    init {
        specializationList = repository.fetchSpecializationList(
            onStart = { isLoading = true },
            onComplete = { isLoading = false },
            onError = { toastMessage = it }
        ).asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)
    }
}
