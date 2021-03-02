package nl.bryanderidder.ornaguide.shared.network

import com.skydoves.sandwich.ApiResponse
import nl.bryanderidder.ornaguide.characterclass.model.CharacterClass
import nl.bryanderidder.ornaguide.skill.model.Skill
import nl.bryanderidder.ornaguide.specialization.model.Specialization


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
}