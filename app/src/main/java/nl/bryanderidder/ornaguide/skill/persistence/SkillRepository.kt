package nl.bryanderidder.ornaguide.skill.persistence

import androidx.annotation.WorkerThread
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import nl.bryanderidder.ornaguide.shared.network.OrnaClient
import nl.bryanderidder.ornaguide.shared.util.NetworkUtil
import nl.bryanderidder.ornaguide.skill.model.Skill
import timber.log.Timber

/**
 * Description
 * @author Bryan de Ridder
 */
class SkillRepository(
    private val client: OrnaClient,
    private val dao: SkillDao,
) {
    @WorkerThread
    fun getSkillListFromDb(
        requestBody: SkillRequestBody = SkillRequestBody(),
        onStart: () -> Unit = {},
        onComplete: () -> Unit = {},
        onError: (String?) -> Unit,
    ) = flow<List<Skill>> {
        val skillList = dao.getSkillList()
        if (!skillList.isNullOrEmpty())
            emit(skillList)
        else
            syncDbWithNetwork(requestBody, onError = onError)
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun syncDbWithNetwork(
        requestBody: SkillRequestBody = SkillRequestBody(),
        onStart: () -> Unit = {},
        onComplete: () -> Unit = {},
        onError: (String?) -> Unit,
    ) = flow<List<Skill>> {
        client.fetchSkillList(requestBody)
            .suspendOnSuccess {
                dao.insertSkillList(response.body() ?: listOf())
                emit(response.body() ?: listOf())
            }
            .onError {
                onError(message())
                Timber.e(message())
            }
            .onException {
                NetworkUtil.handleExceptionWithNetworkMessage(onError, exception)
            }
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(Dispatchers.IO)

    fun fetchSkill(
        id: Int,
        onError: (String?) -> Unit
    ) = flow<Skill> {
        val dbResult = getSkillFromDb(id)
        emit(dbResult)
        client.fetchSkillList(SkillRequestBody(id))
            .suspendOnSuccess {
                // the network object has changed replace it in the db.
                val networkResult = response.body()?.firstOrNull()
                if (networkResult != null && dbResult != networkResult) {
                    dao.insertSkill(networkResult)
                    emit(networkResult)
                }
            }
            .onError {
                onError(message())
                Timber.e(message())
            }
            .onException {
                NetworkUtil.handleException(onError, exception)
            }
    }

    @WorkerThread
    suspend fun getSkillFromDb(id: Int): Skill = dao.getSkill(id)

    @WorkerThread
    fun search(query: String) = flow<List<Skill>> {
        val results = dao.search("*$query*")
        emit(results)
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun fetchAllPossibleTiers() = flow<List<Int>> {
        val results = dao.getAllPossibleTiers()
        emit(results)
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun fetchAllPossibleTypes() = flow<List<String>> {
        val results = dao.getAllPossibleTypes()
        emit(results)
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun fetchAllPossibleElements() = flow<List<String>> {
        val results = dao.getAllPossibleElements()
        emit(results)
    }.flowOn(Dispatchers.IO)
}