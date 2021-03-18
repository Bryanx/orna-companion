package nl.bryanderidder.ornaguide.skill.ui.list

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
import nl.bryanderidder.ornaguide.skill.model.Skill
import nl.bryanderidder.ornaguide.skill.persistence.SkillRepository
import nl.bryanderidder.ornaguide.skill.ui.list.filter.SkillFilter

class SkillListViewModel(repository: SkillRepository) : BindingViewModel() {

    @get:Bindable
    var toastMessage: String? by bindingProperty(null)
        private set

    @get:Bindable
    var isLoading: Boolean by bindingProperty(false)
        private set

    val allPossibleTiers: LiveData<List<Int>> by lazy {
        repository.fetchAllPossibleTiers()
            .map { it.sorted() }
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

    private var sessionItems = listOf<Skill>()
    val skillList: MutableLiveData<List<Skill>> = MutableLiveData()

    private var sessionSkillFilter: SkillFilter = SkillFilter(tiers = listOf(1))
    var skillFilter: MutableLiveData<SkillFilter> = MutableLiveData(SkillFilter(tiers = listOf(1)))

    init {
        viewModelScope.launch {
            repository.fetchSkillList(
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
        val filteredSkills = skillFilter.value?.applyFilter(sessionItems)
        skillList.postValue(filteredSkills)
    }

    fun updateSelectedTiers(tiers: List<String>) {
        sessionSkillFilter.tiers = tiers.map(String::toInt).toList()
    }

    fun updateSelectedTypes(types: List<String>) {
        sessionSkillFilter.types = types
    }

    fun updateSelectedElements(elements: List<String>) {
        sessionSkillFilter.elements = elements
    }

    fun onSubmit(dialog: DialogFragment) {
        skillFilter.value = sessionSkillFilter.copy()
        loadItems()
        dialog.dismiss()
    }

    fun onDismissed() {
        sessionSkillFilter = skillFilter.value?.copy() ?: SkillFilter()
    }
}
