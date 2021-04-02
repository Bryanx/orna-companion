package nl.bryanderidder.ornaguide.save.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.BindingViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import nl.bryanderidder.ornaguide.achievement.model.Achievement
import nl.bryanderidder.ornaguide.achievement.persistence.AchievementRepository
import nl.bryanderidder.ornaguide.characterclass.model.CharacterClass
import nl.bryanderidder.ornaguide.characterclass.persistence.CharacterClassRepository
import nl.bryanderidder.ornaguide.item.model.Item
import nl.bryanderidder.ornaguide.item.persistence.ItemRepository
import nl.bryanderidder.ornaguide.monster.model.Monster
import nl.bryanderidder.ornaguide.monster.persistence.MonsterRepository
import nl.bryanderidder.ornaguide.npc.model.Npc
import nl.bryanderidder.ornaguide.npc.persistence.NpcRepository
import nl.bryanderidder.ornaguide.pet.model.Pet
import nl.bryanderidder.ornaguide.pet.persistence.PetRepository
import nl.bryanderidder.ornaguide.save.model.Save
import nl.bryanderidder.ornaguide.save.persistence.SaveRepository
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil
import nl.bryanderidder.ornaguide.skill.model.Skill
import nl.bryanderidder.ornaguide.skill.persistence.SkillRepository
import nl.bryanderidder.ornaguide.specialization.model.Specialization
import nl.bryanderidder.ornaguide.specialization.persistence.SpecializationRepository

class SaveListViewModel(
    private val sharedPrefsUtil: SharedPrefsUtil,
    private val saveRepo: SaveRepository,
    private val characterClassRepo: CharacterClassRepository,
    private val skillRepo: SkillRepository,
    private val specializationRepo: SpecializationRepository,
    private val petRepo: PetRepository,
    private val itemRepo: ItemRepository,
    private val monsterRepo: MonsterRepository,
    private val npcRepo: NpcRepository,
    private val achievementRepo: AchievementRepository,
) : BindingViewModel() {

    var type = ""
    fun setType(tag: String?): SaveListViewModel {
        type = tag  ?: ""
        isAlreadySaved()
        return this
    }

    var isSaved: MutableLiveData<Boolean> = MutableLiveData(false)

    val saveList: LiveData<List<Save>> by lazy {
        saveRepo.fetchSaveList()
            .asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)
    }

    fun addSave() = viewModelScope.launch {
        getSaveFromSharedPrefs()?.let {
            if (isSaved.value == false) {
                saveRepo.insertSave(it)
            } else {
                saveRepo.deleteSave(it)
            }
            isSaved.value?.let { saved -> isSaved.postValue(!saved) }
        }
    }

    private fun isAlreadySaved() = viewModelScope.launch {
        getSaveFromSharedPrefs()?.let { isSaved.postValue(saveRepo.isSaveExists(it)) }
    }

    private suspend fun getSaveFromSharedPrefs(): Save? {
        return when (type) {
            Item.NAME -> Save.ofItem(itemRepo.getItemFromDb(sharedPrefsUtil.getItemId()))
            Monster.NAME -> Save.ofMonster(monsterRepo.getMonsterFromDb(sharedPrefsUtil.getMonsterId()))
            Skill.NAME -> Save.ofSkill(skillRepo.getSkillFromDb(sharedPrefsUtil.getSkillId()))
            CharacterClass.NAME -> Save.ofCharacterClass(characterClassRepo.getCharacterClassFromDb(sharedPrefsUtil.getCharacterClassId()))
            Pet.NAME -> Save.ofPet(petRepo.getPetFromDb(sharedPrefsUtil.getPetId()))
            Specialization.NAME -> Save.ofSpecialization(specializationRepo.getSpecializationFromDb(sharedPrefsUtil.getSpecializationId()))
            Npc.NAME -> Save.ofNpc(npcRepo.getNpcFromDb(sharedPrefsUtil.getNpcId()))
            Achievement.NAME -> Save.ofAchievement(achievementRepo.getAchievementFromDb(sharedPrefsUtil.getAchievementId()))
            else -> null
        }
    }
}