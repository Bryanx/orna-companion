package nl.bryanderidder.ornaguide.shared.network

import com.skydoves.sandwich.ApiResponse
import nl.bryanderidder.ornaguide.characterclass.model.CharacterClass
import nl.bryanderidder.ornaguide.skill.model.Skill
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

//    @POST("monster")
//    suspend fun fetchMonsterList(
//        @Body body: MonsterRequestBody
//    ): ApiResponse<List<Monster>>
//
//    @POST("specialization")
//    suspend fun fetchSpecializationList(
//        @Body body: SpecializationRequestBody
//    ): ApiResponse<List<Specialization>>
//
//    @POST("item")
//    suspend fun fetchItemList(
//        @Body body: ItemRequestBody
//    ): ApiResponse<List<Item>>
//
//    @POST("pet")
//    suspend fun fetchPetList(
//        @Body body: PetRequestBody
//    ): ApiResponse<List<Pet>>
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