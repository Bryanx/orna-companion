package nl.bryanderidder.ornaguide.shared.ui.menu.sync

import androidx.databinding.Bindable
import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
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

class SyncViewModel(
    private val characterClassRepo: CharacterClassRepository,
    private val skillRepo: SkillRepository,
    private val specializationRepo: SpecializationRepository,
    private val petRepo: PetRepository,
    private val itemRepo: ItemRepository,
    private val monsterRepo: MonsterRepository,
    private val npcRepo: NpcRepository,
    private val achievementRepo: AchievementRepository,
    private val sharedPrefs: SharedPrefsUtil
) : BindingViewModel() {

    @get:Bindable
    var toastMessage: String? by bindingProperty(null)
        private set

    @get:Bindable
    var isLoading: Boolean by bindingProperty(false)
        private set

    @get:Bindable
    var isStarted: Boolean by bindingProperty(false)
        private set

    @get:Bindable
    var characterClassMessage: String by bindingProperty("")
        private set

    @get:Bindable
    var skillMessage: String by bindingProperty("")
        private set

    @get:Bindable
    var specializationMessage: String by bindingProperty("")
        private set

    @get:Bindable
    var petMessage: String by bindingProperty("")
        private set

    @get:Bindable
    var itemMessage: String by bindingProperty("")
        private set

    @get:Bindable
    var monsterMessage: String by bindingProperty("")
        private set

    @get:Bindable
    var npcMessage: String by bindingProperty("")
        private set

    @get:Bindable
    var achievementMessage: String by bindingProperty("")
        private set

    init {
        viewModelScope.launch {
            isLoading = true
            combine(
                characterClassRepo.syncDbWithNetwork(
                    onStart = { characterClassMessage = "Fetching classes" },
                    onComplete = { characterClassMessage = "Classes successfully synced."},
                    onError = { toastMessage = it }
                ) as Flow<Any>,
                skillRepo.syncDbWithNetwork(
                    onStart = { skillMessage = "Fetching skills" },
                    onComplete = { skillMessage = "Skills successfully synced."},
                    onError = { toastMessage = it }
                ) as Flow<Any>,
                specializationRepo.syncDbWithNetwork(
                    onStart = { specializationMessage = "Fetching specializations" },
                    onComplete = { specializationMessage = "Specializations successfully synced."},
                    onError = { toastMessage = it }
                ) as Flow<Any>,
                petRepo.syncDbWithNetwork(
                    onStart = { petMessage = "Fetching pets" },
                    onComplete = { petMessage = "Pets successfully synced."},
                    onError = { toastMessage = it }
                ) as Flow<Any>,
                itemRepo.syncDbWithNetwork(
                    onStart = { itemMessage = "Fetching items" },
                    onComplete = { itemMessage = "Items successfully synced."},
                    onError = { toastMessage = it }
                ) as Flow<Any>,
                monsterRepo.syncDbWithNetwork(
                    onStart = { monsterMessage = "Fetching monsters" },
                    onComplete = { monsterMessage = "Monsters successfully synced."},
                    onError = { toastMessage = it }
                ) as Flow<Any>,
                npcRepo.syncDbWithNetwork(
                    onStart = { npcMessage = "Fetching NPCs" },
                    onComplete = { npcMessage = "NPCs successfully synced."},
                    onError = { toastMessage = it }
                ) as Flow<Any>,
                achievementRepo.syncDbWithNetwork(
                    onStart = { achievementMessage = "Fetching achievements" },
                    onComplete = { achievementMessage = "Achievements successfully synced."},
                    onError = { toastMessage = it }
                ) as Flow<Any>
            ) { true }.collect {
                sharedPrefs.clearAllFilters()
                isLoading = false
            }
        }
    }
}
