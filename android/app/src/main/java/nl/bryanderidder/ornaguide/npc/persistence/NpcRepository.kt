package nl.bryanderidder.ornaguide.npc.persistence

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
import nl.bryanderidder.ornaguide.npc.model.Npc
import nl.bryanderidder.ornaguide.shared.network.OrnaClient
import nl.bryanderidder.ornaguide.shared.util.NetworkUtil
import timber.log.Timber

/**
 * Main repository to fetch data from api and store in local db.
 * @author Bryan de Ridder
 */
class NpcRepository(
    private val client: OrnaClient,
    private val dao: NpcDao,
) {
    @WorkerThread
    fun getNpcListFromDb(
        requestBody: NpcRequestBody = NpcRequestBody(),
        onStart: () -> Unit = {},
        onComplete: () -> Unit = {},
        onError: (String?) -> Unit,
    ) = flow {
        val npcList = dao.getNpcList()
        if (!npcList.isNullOrEmpty())
            emit(npcList)
        else
            syncDbWithNetwork(requestBody, onError = onError)
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun syncDbWithNetwork(
        requestBody: NpcRequestBody = NpcRequestBody(),
        onStart: () -> Unit = {},
        onComplete: () -> Unit = {},
        onError: (String?) -> Unit,
    ) = flow<List<Npc>> {
        client.fetchNpcList(requestBody)
            .suspendOnSuccess {
                dao.insertNpcList(response.body() ?: listOf())
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

    fun fetchNpc(
        id: Int,
        onError: (String?) -> Unit
    ) = flow {
        val dbResult = getNpcFromDb(id)
        emit(dbResult)
        client.fetchNpcList(NpcRequestBody(id))
            .suspendOnSuccess {
                // the network object has changed replace it in the db.
                val networkResult = response.body()?.firstOrNull()
                if (networkResult != null && dbResult != networkResult) {
                    dao.insertNpc(networkResult)
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
    suspend fun getNpcFromDb(id: Int): Npc = dao.getNpc(id)

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
}