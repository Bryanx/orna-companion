package nl.bryanderidder.ornaguide.shared.network

import com.skydoves.sandwich.ApiResponse
import nl.bryanderidder.ornaguide.characterclass.CharacterClass
import nl.bryanderidder.ornaguide.skill.Skill
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

}