package nl.bryanderidder.ornaguide.shared.ui.menu.search

import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty
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
        val replace = query.replace(Regex("[^a-z| ]"), "")
        searchQuery.value = replace
    }

    private fun loadSearchHistory() =
        searchResults.postValue(sharedPrefsUtil.getSearchHistory())

    private suspend fun loadSearchResults(query: String) {
        combine(
            characterClassRepo.search(query).map { it.map(SearchResult::ofCharacterClass).toList() },
            skillRepo.search(query).map { it.map(SearchResult::ofSkill).toList() },
            specializationRepo.search(query).map { it.map(SearchResult::ofSpecialization).toList() },
            petRepo.search(query).map { it.map(SearchResult::ofPet).toList() },
            itemRepo.search(query).map { it.map(SearchResult::ofItem).toList() },
            monsterRepo.search(query).map { it.map(SearchResult::ofMonster).toList() },
            npcRepo.search(query).map { it.map(SearchResult::ofNpc).toList() },
            achievementRepo.search(query).map { it.map(SearchResult::ofAchievement).toList() },
        ) {
            it.asList().flatten()
        }.collect(searchResults::postValue)
    }
}