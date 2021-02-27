package nl.bryanderidder.ornaguide.shared.network

import com.skydoves.sandwich.ApiResponse
import nl.bryanderidder.ornaguide.characterclass.CharacterClass


/**
 * Orna client to fetch data from orna service.
 * @author Bryan de Ridder
 */
class OrnaClient(
    private val ornaService: OrnaService
) {
    suspend fun fetchCharacterClassList(
        requestBody: CharacterClassRequestBody
    ): ApiResponse<List<CharacterClass>> =
        ornaService.fetchCharacterClassList(requestBody)
}