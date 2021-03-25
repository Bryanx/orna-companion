package nl.bryanderidder.ornaguide.monster.persistence

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
import nl.bryanderidder.ornaguide.monster.model.Monster
import nl.bryanderidder.ornaguide.shared.network.OrnaClient
import nl.bryanderidder.ornaguide.shared.util.NetworkUtil
import timber.log.Timber

/**
 * Main repository to fetch data from api and store in local db.
 * @author Bryan de Ridder
 */
class MonsterRepository(
    private val client: OrnaClient,
    private val dao: MonsterDao,
) {
    @WorkerThread
    fun getMonsterListFromDb(
        requestBody: MonsterRequestBody = MonsterRequestBody(),
        onStart: () -> Unit = {},
        onComplete: () -> Unit = {},
        onError: (String?) -> Unit,
    ) = flow<List<Monster>> {
        val monsterList = dao.getMonsterList()
        if (!monsterList.isNullOrEmpty())
            emit(monsterList)
        else
            syncDbWithNetwork(requestBody, onError = onError)
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun syncDbWithNetwork(
        requestBody: MonsterRequestBody = MonsterRequestBody(),
        onStart: () -> Unit = {},
        onComplete: () -> Unit = {},
        onError: (String?) -> Unit,
    ) = flow<List<Monster>> {
        client.fetchMonsterList(requestBody)
            .suspendOnSuccess {
                dao.insertMonsterList(response.body() ?: listOf())
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

    fun fetchMonster(
        id: Int,
        onError: (String?) -> Unit
    ) = flow<Monster> {
        val dbResult = getMonsterFromDb(id)
        emit(dbResult)
        client.fetchMonsterList(MonsterRequestBody(id))
            .suspendOnSuccess {
                // the network object has changed replace it in the db.
                val networkResult = response.body()?.firstOrNull()
                if (networkResult != null && dbResult != networkResult) {
                    dao.insertMonster(networkResult)
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
    suspend fun getMonsterFromDb(id: Int): Monster = dao.getMonster(id)

    @WorkerThread
    fun search(query: String) = flow<List<Monster>> {
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
        emit(listOf("Boss", "Normal"))
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun fetchAllPossibleSpawns() = flow<List<String>> {
        val results = dao.getAllPossibleSpawns()
        emit(results)
    }.flowOn(Dispatchers.IO)
}