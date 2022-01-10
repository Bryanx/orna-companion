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
import nl.bryanderidder.ornaguide.shared.util.asLiveDataIO
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
        repository.fetchAllPossibleTiers().asLiveDataIO(viewModelScope)
    }

    val allPossibleTypes: LiveData<List<String>> by lazy {
        repository.fetchAllPossibleTypes().asLiveDataIO(viewModelScope)
    }

    val allPossibleElements: LiveData<List<String>> by lazy {
        repository.fetchAllPossibleElements().asLiveDataIO(viewModelScope)
    }

    val allPossibleSources: List<String> = listOf("Drop", "Bought from Arcanist")

    val allPossibleCures: LiveData<List<String>> by lazy {
        repository.fetchAllPossibleCures().asLiveDataIO(viewModelScope)
    }

    val allPossibleGives: LiveData<List<String>> by lazy {
        repository.fetchAllPossibleGives().asLiveDataIO(viewModelScope)
    }

    val allPossibleCauses: LiveData<List<String>> by lazy {
        repository.fetchAllPossibleCauses().asLiveDataIO(viewModelScope)
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

    fun updateSelectedTiers(tiers: List<String>) =
        updateFilter(sessionSkillFilter.value?.copy(tiers = tiers.map(String::toInt).toList()))

    fun updateSelectedTypes(types: List<String>) =
        updateFilter(sessionSkillFilter.value?.copy(types = types))

    fun updateSelectedElements(elements: List<String>) =
        updateFilter(sessionSkillFilter.value?.copy(elements = elements))

    fun updateSelectedSources(sources: List<String>) =
        updateFilter(sessionSkillFilter.value?.copy(sources = sources))

    fun updateSelectedCures(cures: List<String>) =
        updateFilter(sessionSkillFilter.value?.copy(cures = cures))

    fun updateSelectedGives(gives: List<String>) =
        updateFilter(sessionSkillFilter.value?.copy(gives = gives))

    fun updateSelectedCauses(causes: List<String>) =
        updateFilter(sessionSkillFilter.value?.copy(causes = causes))

    fun onClearFilters() = updateFilter(SkillFilter())

    fun updateFilter(filter: SkillFilter?) {
        sessionSkillFilter.value = filter
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
