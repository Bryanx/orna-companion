package nl.bryanderidder.ornaguide.shared.network

import com.skydoves.sandwich.ApiResponse
import nl.bryanderidder.ornaguide.achievement.model.Achievement
import nl.bryanderidder.ornaguide.achievement.persistence.AchievementRequestBody
import nl.bryanderidder.ornaguide.characterclass.model.CharacterClass
import nl.bryanderidder.ornaguide.characterclass.persistence.CharacterClassRequestBody
import nl.bryanderidder.ornaguide.item.model.Item
import nl.bryanderidder.ornaguide.item.model.ItemAssess
import nl.bryanderidder.ornaguide.item.persistence.ItemAssessRequestBody
import nl.bryanderidder.ornaguide.item.persistence.ItemRequestBody
import nl.bryanderidder.ornaguide.monster.model.Monster
import nl.bryanderidder.ornaguide.monster.persistence.MonsterRequestBody
import nl.bryanderidder.ornaguide.npc.model.Npc
import nl.bryanderidder.ornaguide.npc.persistence.NpcRequestBody
import nl.bryanderidder.ornaguide.pet.model.Pet
import nl.bryanderidder.ornaguide.pet.persistence.PetRequestBody
import nl.bryanderidder.ornaguide.skill.model.Skill
import nl.bryanderidder.ornaguide.skill.persistence.SkillRequestBody
import nl.bryanderidder.ornaguide.specialization.model.Specialization
import nl.bryanderidder.ornaguide.specialization.persistence.SpecializationRequestBody


/**
 * Orna client to fetch data from orna service.
 * @author Bryan de Ridder
 */
class OrnaClient(
    private val service: OrnaService
) {
    suspend fun fetchCharacterClassList(
        requestBody: CharacterClassRequestBody
    ): ApiResponse<List<CharacterClass>> =
        service.fetchCharacterClassList(requestBody)

    suspend fun fetchSkillList(
        requestBody: SkillRequestBody
    ): ApiResponse<List<Skill>> =
        service.fetchSkillList(requestBody)

    suspend fun fetchSpecializationList(
        requestBody: SpecializationRequestBody
    ): ApiResponse<List<Specialization>> =
        service.fetchSpecializationList(requestBody)

    suspend fun fetchPetList(
        requestBody: PetRequestBody
    ): ApiResponse<List<Pet>> =
        service.fetchPetList(requestBody)
    
    suspend fun fetchItemList(
        requestBody: ItemRequestBody
    ): ApiResponse<List<Item>> =
        service.fetchItemList(requestBody)

    suspend fun fetchMonsterList(
        requestBody: MonsterRequestBody
    ): ApiResponse<List<Monster>> =
        service.fetchMonsterList(requestBody)

    suspend fun fetchNpcList(
        requestBody: NpcRequestBody
    ): ApiResponse<List<Npc>> =
        service.fetchNpcList(requestBody)

    suspend fun fetchAchievementList(
        requestBody: AchievementRequestBody
    ): ApiResponse<List<Achievement>> =
        service.fetchAchievementList(requestBody)

    suspend fun assessItem(
        requestBody: ItemAssessRequestBody
    ): ApiResponse<ItemAssess> =
        service.assessItem(requestBody)

}