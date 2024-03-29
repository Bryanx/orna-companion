package nl.bryanderidder.ornaguide.shared.ui.menu.search

import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import nl.bryanderidder.ornaguide.achievement.persistence.AchievementRepository
import nl.bryanderidder.ornaguide.characterclass.persistence.CharacterClassRepository
import nl.bryanderidder.ornaguide.item.persistence.ItemRepository
import nl.bryanderidder.ornaguide.monster.persistence.MonsterRepository
import nl.bryanderidder.ornaguide.npc.persistence.NpcRepository
import nl.bryanderidder.ornaguide.pet.persistence.PetRepository
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil
import nl.bryanderidder.ornaguide.skill.persistence.SkillRepository
import nl.bryanderidder.ornaguide.specialization.persistence.SpecializationRepository
import timber.log.Timber

class SearchViewModel(
    private val characterClassRepo: CharacterClassRepository,
    private val skillRepo: SkillRepository,
    private val specializationRepo: SpecializationRepository,
    private val petRepo: PetRepository,
    private val itemRepo: ItemRepository,
    private val monsterRepo: MonsterRepository,
    private val npcRepo: NpcRepository,
    private val achievementRepo: AchievementRepository,
    private val sharedPrefsUtil: SharedPrefsUtil
) : BindingViewModel() {

    @get:Bindable
    var toastMessage: String? by bindingProperty(null)
        private set

    @get:Bindable
    var isLoading: Boolean by bindingProperty(false)
        private set

    var searchQuery = MutableStateFlow("")

    var liveDataQuery: MutableLiveData<String> = MutableLiveData("")

    var searchResults: MutableLiveData<List<SearchResult>> = MutableLiveData(listOf())

    init {
        viewModelScope.launch {
            searchQuery.collectLatest { query ->
                when {
                    query.isEmpty() -> loadSearchHistory()
                    else -> loadSearchResults(query)
                }
            }
        }
    }

    fun setQuery(query: String) {
        val replace = query.replace(("[^a-z|^A-Z| |']").toRegex(), "")
        searchQuery.value = replace
        liveDataQuery.value = query
    }

    private fun loadSearchHistory() =
        searchResults.postValue(sharedPrefsUtil.getSearchHistory())

    private suspend fun loadSearchResults(query: String) {
        Timber.i("loadSearchResults(${query})")
        combine(
            characterClassRepo.search(query).map { it.map(SearchResult::ofCharacterClass).toList() },
            skillRepo.search(query).map { it.map(SearchResult::ofSkill).toList() },
            specializationRepo.search(query).map { it.map(SearchResult::ofSpecialization).toList() },
            petRepo.search(query).map { it.map(SearchResult::ofPet).toList() },
            itemRepo.search(query).map { it.map(SearchResult::ofItem).toList() },
            monsterRepo.search(query).map { it.map(SearchResult::ofMonster).toList() },
            npcRepo.search(query).map { it.map(SearchResult::ofNpc).toList() },
            achievementRepo.search(query).map { it.map(SearchResult::ofAchievement).toList() },
        ) { results ->
            results.asList().flatten().sortedWith(compareBy({ !it.name.startsWith(query) }, { !it.name.endsWith(query) }, { it.name.length }))
        }.flowOn(Dispatchers.Default)
            .collect(searchResults::postValue)
    }
}