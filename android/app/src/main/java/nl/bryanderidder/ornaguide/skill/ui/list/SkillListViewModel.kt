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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil
import nl.bryanderidder.ornaguide.skill.model.Skill
import nl.bryanderidder.ornaguide.skill.persistence.SkillRepository
import nl.bryanderidder.ornaguide.skill.ui.list.filter.SkillFilter

class SkillListViewModel(
    private val repository: SkillRepository,
    sharedPrefs: SharedPrefsUtil
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

    val allPossibleTypes: LiveData<List<String>> by lazy {
        repository.fetchAllPossibleTypes()
            .asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)
    }

    val allPossibleElements: LiveData<List<String>> by lazy {
        repository.fetchAllPossibleElements()
            .map { it.filter(String::isNotEmpty).toList() }
            .asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)
    }

    val skillList: MutableLiveData<List<Skill>> = MutableLiveData()

    var sessionSkillFilter: MutableLiveData<SkillFilter> =
        MutableLiveData(SkillFilter(tiers = listOf(sharedPrefs.getDefaultTier())))
    var skillFilter: MutableLiveData<SkillFilter> =
        MutableLiveData(SkillFilter(tiers = listOf(sharedPrefs.getDefaultTier())))

    init {
        loadItems()
    }

    private fun loadItems() = viewModelScope.launch {
        isLoading = true
        delay(200L)
        repository.getSkillListFromDb(
            onComplete = { isLoading = false },
            onError = { toastMessage = it }
        ).collect {
            val newList = skillFilter.value?.applyFilter(it)
            skillList.postValue(newList)
            resultCount = sessionSkillFilter.value?.countFilterResults(newList) ?: 0
        }
    }

    fun updateSelectedTiers(tiers: List<String>) {
        sessionSkillFilter.value = sessionSkillFilter.value?.copy(tiers = tiers.map(String::toInt).toList())
        resultCount = sessionSkillFilter.value?.countFilterResults(skillList.value) ?: 0
    }

    fun updateSelectedTypes(types: List<String>) {
        sessionSkillFilter.value = sessionSkillFilter.value?.copy(types = types)
        resultCount = sessionSkillFilter.value?.countFilterResults(skillList.value) ?: 0
    }

    fun updateSelectedElements(elements: List<String>) {
        sessionSkillFilter.value = sessionSkillFilter.value?.copy(elements = elements)
        resultCount = sessionSkillFilter.value?.countFilterResults(skillList.value) ?: 0
    }

    fun onSubmit(dialog: DialogFragment) {
        skillFilter.value = sessionSkillFilter.value?.copy()
        loadItems()
        dialog.dismiss()
    }

    fun onDismissed() {
        sessionSkillFilter.value = skillFilter.value?.copy() ?: SkillFilter()
    }
}
