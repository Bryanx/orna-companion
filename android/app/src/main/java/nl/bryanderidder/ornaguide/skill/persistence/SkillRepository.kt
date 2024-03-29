package nl.bryanderidder.ornaguide.skill.persistence

import androidx.annotation.WorkerThread
import com.skydoves.sandwich.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import nl.bryanderidder.ornaguide.shared.database.OrnaTypeConverters
import nl.bryanderidder.ornaguide.shared.network.OrnaClient
import nl.bryanderidder.ornaguide.shared.util.NetworkUtil
import nl.bryanderidder.ornaguide.skill.model.Skill
import timber.log.Timber

/**
 * Main skill repository
 * @author Bryan de Ridder
 */
class SkillRepository(
    private val client: OrnaClient,
    private val dao: SkillDao,
    private val converters: OrnaTypeConverters,
) {
    @WorkerThread
    fun getSkillListFromDb(
        requestBody: SkillRequestBody = SkillRequestBody(),
        onStart: () -> Unit = {},
        onComplete: () -> Unit = {},
        onError: (String?) -> Unit,
    ) = flow {
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
                onComplete()
            }
            .suspendOnError {
                onError(message())
                Timber.w(message())
                emit(listOf())
            }
            .onException {
                NetworkUtil.handleExceptionWithNetworkMessage(onError, exception)
            }
    }.onStart { onStart() }.flowOn(Dispatchers.IO)

    fun fetchSkill(
        id: Int,
        onError: (String?) -> Unit
    ) = flow {
        val dbResult = getSkillFromDb(id)
        if (dbResult != null)
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
    suspend fun getSkillFromDb(id: Int): Skill? = dao.getSkill(id)

    @WorkerThread
    fun search(query: String) = flow {
        val results = dao.search("*$query*")
        emit(results)
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun fetchAllPossibleTiers() = flow {
        val results = dao.getAllPossibleTiers()
        emit(results)
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun fetchAllPossibleTypes() = flow {
        val results = dao.getAllPossibleTypes()
        emit(results)
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun fetchAllPossibleElements() = flow {
        val results = dao.getAllPossibleElements()
            .filter(String::isNotEmpty)
        emit(results)
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun fetchAllPossibleCures() = flow {
        val results = dao.getAllPossibleCures()
            .flatMap { converters.toStringList(it) }
            .distinct()
            .sorted()
        emit(results)
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun fetchAllPossibleGives() = flow {
        val results = dao.getAllPossibleGives()
            .flatMap { converters.toStringList(it) }
            .distinct()
            .sorted()
        emit(results)
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun fetchAllPossibleCauses() = flow {
        val results = dao.getAllPossibleCauses()
            .flatMap { converters.toStringList(it) }
            .distinct()
            .sorted()
        emit(results)
    }.flowOn(Dispatchers.IO)
}