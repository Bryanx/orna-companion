package nl.bryanderidder.ornaguide.achievement.ui.list

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
import nl.bryanderidder.ornaguide.achievement.model.Achievement
import nl.bryanderidder.ornaguide.achievement.persistence.AchievementRepository
import nl.bryanderidder.ornaguide.achievement.ui.list.filter.AchievementFilter
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil

class AchievementListViewModel(
    private val repository: AchievementRepository,
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

    val achievementList: MutableLiveData<List<Achievement>> = MutableLiveData()

    var sessionAchievementFilter: MutableLiveData<AchievementFilter> = MutableLiveData(
        AchievementFilter(tiers = listOf(sharedPrefs.getDefaultTier())))
    var achievementFilter: MutableLiveData<AchievementFilter> = MutableLiveData(
        AchievementFilter(tiers = listOf(sharedPrefs.getDefaultTier())))

    init {
        loadItems()
    }

    private fun loadItems() = viewModelScope.launch {
        isLoading = true
        delay(200L)
        repository.getAchievementListFromDb(
            onComplete = { isLoading = false },
            onError = { toastMessage = it }
        ).collect {
            val newList = achievementFilter.value?.applyFilter(it)
            achievementList.postValue(newList)
            resultCount = sessionAchievementFilter.value?.countFilterResults(newList) ?: 0
        }
    }

    fun updateSelectedTiers(tiers: List<String>) {
        sessionAchievementFilter.value = sessionAchievementFilter.value?.copy(tiers = tiers.map(String::toInt).toList())
        resultCount = sessionAchievementFilter.value?.countFilterResults(achievementList.value) ?: 0
    }

    fun onClearFilters() {
        sessionAchievementFilter.value = AchievementFilter()
        resultCount = sessionAchievementFilter.value?.countFilterResults(achievementList.value) ?: 0
    }

    fun onSubmit(dialog: DialogFragment) {
        achievementFilter.value = sessionAchievementFilter.value?.copy()
        loadItems()
        dialog.dismiss()
    }

    fun onDismissed() {
        sessionAchievementFilter.value = achievementFilter.value?.copy() ?: AchievementFilter()
    }
}
