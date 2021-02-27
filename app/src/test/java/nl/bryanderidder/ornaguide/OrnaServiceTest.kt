package nl.bryanderidder.ornaguide

import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import nl.bryanderidder.ornaguide.shared.network.CharacterClassRequestBody
import nl.bryanderidder.ornaguide.shared.network.OrnaService
import nl.bryanderidder.ornaguide.shared.network.SkillRequestBody
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class OrnaServiceTest : ApiAbstract<OrnaService>() {

    private lateinit var service: OrnaService

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @Before
    fun initService() {
        service = createService(OrnaService::class.java)
    }

    @Throws(IOException::class)
    @Test
    fun fetchCharacterClassListNetworkTest() = runBlocking {
        enqueueResponse("/CharacterClassResponse.json")
        val response = service.fetchCharacterClassList(CharacterClassRequestBody(tier = 5))
        val responseBody = requireNotNull((response as ApiResponse.Success).data)
        mockWebServer.takeRequest()
        assertThat(responseBody.count(), `is`(3))
    }

    @Throws(IOException::class)
    @Test
    fun fetchSkillsNetworkTest() = runBlocking {
        enqueueResponse("/SkillsResponse.json")
        val response = service.fetchSkillList(SkillRequestBody(tier = 5))
        val responseBody = requireNotNull((response as ApiResponse.Success).data)
        mockWebServer.takeRequest()
        assertThat(responseBody.count(), `is`(56))
    }
}