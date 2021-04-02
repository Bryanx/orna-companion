package nl.bryanderidder.ornaguide.skill.ui.detail

import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import nl.bryanderidder.ornaguide.shared.ui.menu.search.SearchResult
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil
import nl.bryanderidder.ornaguide.skill.model.Skill
import nl.bryanderidder.ornaguide.skill.persistence.SkillRepository

class SkillDetailViewModel(
    private val repository: SkillRepository,
    private val sharedPrefsUtil: SharedPrefsUtil
) : BindingViewModel() {

    val skill: MutableLiveData<Skill> = MutableLiveData()

    @get:Bindable
    var toastMessage: String? by bindingProperty(null)
        private set

    fun loadSkill() = viewModelScope.launch {
        repository.fetchSkill(
            id = sharedPrefsUtil.getSkillId(),
            onError = { toastMessage = it })
            .collect {
                skill.postValue(it)
                sharedPrefsUtil.addToSearchHistory(SearchResult.ofSkill(it))
            }
    }
}