package nl.bryanderidder.ornaguide.shared.network

import com.skydoves.sandwich.ApiResponse
import nl.bryanderidder.ornaguide.characterclass.model.CharacterClass
import nl.bryanderidder.ornaguide.characterclass.persistence.CharacterClassRequestBody
import nl.bryanderidder.ornaguide.item.model.Item
import nl.bryanderidder.ornaguide.item.persistence.ItemRequestBody
import nl.bryanderidder.ornaguide.pet.model.Pet
import nl.bryanderidder.ornaguide.pet.persistence.PetRequestBody
import nl.bryanderidder.ornaguide.skill.model.Skill
import nl.bryanderidder.ornaguide.skill.persistence.SkillRequestBody
import nl.bryanderidder.ornaguide.specialization.model.Specialization
import nl.bryanderidder.ornaguide.specialization.persistence.SpecializationRequestBody
import retrofit2.http.Body
import retrofit2.http.POST


/**
 * Main service for fetching data from orna guide api
 * @author Bryan de Ridder
 */
interface OrnaService {

    @POST("class")
    suspend fun fetchCharacterClassList(
        @Body body: CharacterClassRequestBody
    ): ApiResponse<List<CharacterClass>>

    @POST("skill")
    suspend fun fetchSkillList(
        @Body body: SkillRequestBody
    ): ApiResponse<List<Skill>>

    @POST("specialization")
    suspend fun fetchSpecializationList(
        @Body body: SpecializationRequestBody
    ): ApiResponse<List<Specialization>>

    @POST("pet")
    suspend fun fetchPetList(
        @Body body: PetRequestBody
    ): ApiResponse<List<Pet>>

    @POST("item")
    suspend fun fetchItemList(
        @Body body: ItemRequestBody
    ): ApiResponse<List<Item>>

//    @POST("monster")
//    suspend fun fetchMonsterList(
//        @Body body: MonsterRequestBody
//    ): ApiResponse<List<Monster>>
//
//    @POST("quest")
//    suspend fun fetchQuestList(
//        @Body body: QuestRequestBody
//    ): ApiResponse<List<Quest>>
//
//    @POST("achievement")
//    suspend fun fetchAchievementList(
//        @Body body: AchievementRequestBody
//    ): ApiResponse<List<Achievement>>
//
//    @POST("npc")
//    suspend fun fetchNpcList(
//        @Body body: NpcRequestBody
//    ): ApiResponse<List<Npc>>

}