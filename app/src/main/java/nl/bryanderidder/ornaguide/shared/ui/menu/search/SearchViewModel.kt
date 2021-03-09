package nl.bryanderidder.ornaguide.shared.ui.menu.search

import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import nl.bryanderidder.ornaguide.achievement.persistence.AchievementRepository
import nl.bryanderidder.ornaguide.characterclass.persistence.CharacterClassRepository
import nl.bryanderidder.ornaguide.item.persistence.ItemRepository
import nl.bryanderidder.ornaguide.monster.persistence.MonsterRepository
import nl.bryanderidder.ornaguide.npc.persistence.NpcRepository
import nl.bryanderidder.ornaguide.pet.persistence.PetRepository
import nl.bryanderidder.ornaguide.shared.util.flowForkJoin
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
) : BindingViewModel() {

    @get:Bindable
    var toastMessage: String? by bindingProperty(null)
        private set

    @get:Bindable
    var isLoading: Boolean by bindingProperty(false)
        private set

    var searchQuery = MutableStateFlow("")

    var searchResults: MutableLiveData<List<SearchResult>> = MutableLiveData(listOf())

    private var searchJob: Job? = null

    init {
        viewModelScope.launch {
            searchQuery.collectLatest { query ->
                searchJob?.cancel()
                searchJob = flowForkJoin(
                    characterClassRepo.search(query).map { it.map(SearchResult::ofCharacterClass).toList() },
                    skillRepo.search(query).map { it.map(SearchResult::ofSkill).toList() },
                    specializationRepo.search(query).map { it.map(SearchResult::ofSpecialization).toList() },
                    petRepo.search(query).map { it.map(SearchResult::ofPet).toList() },
                    itemRepo.search(query).map { it.map(SearchResult::ofItem).toList() },
                    monsterRepo.search(query).map { it.map(SearchResult::ofMonster).toList() },
                    npcRepo.search(query).map { it.map(SearchResult::ofNpc).toList() },
                    achievementRepo.search(query).map { it.map(SearchResult::ofAchievement).toList() },
                ) {
                    searchResults.postValue(it)
                }
            }
        }
    }

    fun setQuery(query: String) {
        val replace = query.replace(Regex("[^a-z| ]"), "")
        searchQuery.value = replace
    }
}