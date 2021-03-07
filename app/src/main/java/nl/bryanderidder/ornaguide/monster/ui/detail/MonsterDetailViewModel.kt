package nl.bryanderidder.ornaguide.monster.ui.detail

import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import nl.bryanderidder.ornaguide.monster.model.Monster
import nl.bryanderidder.ornaguide.monster.persistence.MonsterRepository
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil

class MonsterDetailViewModel(
    private val repository: MonsterRepository,
    private val sharedPrefsUtil: SharedPrefsUtil
) : BindingViewModel() {

    val monster: MutableLiveData<Monster> = MutableLiveData()

    @get:Bindable
    var toastMessage: String? by bindingProperty(null)
        private set

    fun loadMonster() = viewModelScope.launch {
        repository.fetchMonster(
            id = sharedPrefsUtil.getMonsterId(),
            onError = { toastMessage = it })
            .collect {
                monster.postValue(it)
            }
    }
}