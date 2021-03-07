package nl.bryanderidder.ornaguide.achievement.ui.detail

import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import nl.bryanderidder.ornaguide.achievement.model.Achievement
import nl.bryanderidder.ornaguide.achievement.persistence.AchievementRepository
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil

class AchievementDetailViewModel(
    private val repository: AchievementRepository,
    private val sharedPrefsUtil: SharedPrefsUtil
) : BindingViewModel() {

    val achievement: MutableLiveData<Achievement> = MutableLiveData()

    @get:Bindable
    var toastMessage: String? by bindingProperty(null)
        private set

    fun loadAchievement() = viewModelScope.launch {
        repository.fetchAchievement(
            id = sharedPrefsUtil.getAchievementId(),
            onError = { toastMessage = it })
            .collect {
                achievement.postValue(it)
            }
    }
}