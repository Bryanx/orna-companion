package nl.bryanderidder.ornaguide.npc.ui.detail

import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import nl.bryanderidder.ornaguide.npc.model.Npc
import nl.bryanderidder.ornaguide.npc.persistence.NpcRepository
import nl.bryanderidder.ornaguide.shared.ui.menu.search.SearchResult
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil

class NpcDetailViewModel(
    private val repository: NpcRepository,
    private val sharedPrefsUtil: SharedPrefsUtil
) : BindingViewModel() {

    val npc: MutableLiveData<Npc> = MutableLiveData()

    @get:Bindable
    var toastMessage: String? by bindingProperty(null)
        private set

    fun loadNpc() = viewModelScope.launch {
        repository.fetchNpc(
            id = sharedPrefsUtil.getNpcId(),
            onError = { toastMessage = it })
            .collect {
                npc.postValue(it)
                sharedPrefsUtil.addToSearchHistory(SearchResult.ofNpc(it))
            }
    }
}