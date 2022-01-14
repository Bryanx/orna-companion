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
    var resultMessage: String by bindingProperty("Syncing")
        private set

    @get:Bindable
    var resultSubMessage: String by bindingProperty("This may take a few minutes...")
        private set

    @get:Bindable
    var resultSuccess: Boolean by bindingProperty(false)
        private set

    @get:Bindable
    var isLoading: Boolean by bindingProperty(true)
        private set

    @get:Bindable
    var characterClassMessage: String by bindingProperty("Fetching classes")
        private set

    @get:Bindable
    var skillMessage: String by bindingProperty("Fetching skills")
        private set

    @get:Bindable
    var specializationMessage: String by bindingProperty("Fetching specializations")
        private set

    @get:Bindable
    var petMessage: String by bindingProperty("Fetching pets")
        private set

    @get:Bindable
    var itemMessage: String by bindingProperty("Fetching items")
        private set

    @get:Bindable
    var monsterMessage: String by bindingProperty("Fetching monsters")
        private set

    @get:Bindable
    var npcMessage: String by bindingProperty("Fetching NPCs")
        private set

    @get:Bindable
    var achievementMessage: String by bindingProperty("Fetching achievements")
        private set

    init {
        viewModelScope.launch {
            combine(
                characterClassRepo.syncDbWithNetwork(
                    onComplete = { characterClassMessage = "Classes successfully synced."},
                    onError = { characterClassMessage = "Classes failed to sync." }
                ) as Flow<List<Any>>,
                skillRepo.syncDbWithNetwork(
                    onComplete = { skillMessage = "Skills successfully synced."},
                    onError = { skillMessage = "Skills failed to sync." }
                ) as Flow<List<Any>>,
                specializationRepo.syncDbWithNetwork(
                    onComplete = { specializationMessage = "Specializations successfully synced."},
                    onError = { specializationMessage = "Specializations failed to sync." }
                ) as Flow<List<Any>>,
                petRepo.syncDbWithNetwork(
                    onComplete = { petMessage = "Pets successfully synced."},
                    onError = { petMessage = "Pets failed to sync." }
                ) as Flow<List<Any>>,
                itemRepo.syncDbWithNetwork(
                    onComplete = { itemMessage = "Items successfully synced."},
                    onError = { itemMessage = "Items failed to sync." }
                ) as Flow<List<Any>>,
                monsterRepo.syncDbWithNetwork(
                    onComplete = { monsterMessage = "Monsters successfully synced."},
                    onError = { monsterMessage = "Monsters failed to sync." }
                ) as Flow<List<Any>>,
                npcRepo.syncDbWithNetwork(
                    onComplete = { npcMessage = "NPCs successfully synced."},
                    onError = { npcMessage = "NPCs failed to sync." }
                ) as Flow<List<Any>>,
                achievementRepo.syncDbWithNetwork(
                    onComplete = { achievementMessage = "Achievements successfully synced."},
                    onError = { achievementMessage = "Achievements failed to sync." }
                ) as Flow<List<Any>>
            ) {
                return@combine when {
                    it.all(List<Any>::isEmpty) -> SyncResult("Sync failed", "Please try again later.", false)
                    else -> SyncResult("Sync successful, ${it.count(List<Any>::isEmpty)} failed",
                        "To prevent overloading the server syncing is blocked for the next 60 minutes.", true)
                }
            }.collect {
                sharedPrefs.clearAllFilters()
                isLoading = false
                resultMessage = it.message
                resultSubMessage = it.subMessage
                resultSuccess = it.status
            }
        }
    }

    data class SyncResult(val message: String, val subMessage: String, val status: Boolean)
}
