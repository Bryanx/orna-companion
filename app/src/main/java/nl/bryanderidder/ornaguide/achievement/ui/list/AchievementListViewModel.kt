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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import nl.bryanderidder.ornaguide.achievement.model.Achievement
import nl.bryanderidder.ornaguide.achievement.persistence.AchievementRepository
import nl.bryanderidder.ornaguide.achievement.ui.list.filter.AchievementFilter

class AchievementListViewModel(repository: AchievementRepository) : BindingViewModel() {

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

    private var sessionItems = listOf<Achievement>()
    val achievementList: MutableLiveData<List<Achievement>> = MutableLiveData()

    private var sessionAchievementFilter: AchievementFilter = AchievementFilter(tiers = listOf(1))
    var achievementFilter: MutableLiveData<AchievementFilter> = MutableLiveData(
        AchievementFilter(tiers = listOf(1)))

    init {
        viewModelScope.launch {
            repository.fetchAchievementList(
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
        val filteredAchievements = achievementFilter.value?.applyFilter(sessionItems)
        achievementList.postValue(filteredAchievements)
    }

    fun updateSelectedTiers(tiers: List<String>) {
        sessionAchievementFilter.tiers = tiers.map(String::toInt).toList()
    }

    fun onSubmit(dialog: DialogFragment) {
        achievementFilter.value = sessionAchievementFilter.copy()
        loadItems()
        dialog.dismiss()
    }

    fun onDismissed() {
        sessionAchievementFilter = achievementFilter.value?.copy() ?: AchievementFilter()
    }
}
