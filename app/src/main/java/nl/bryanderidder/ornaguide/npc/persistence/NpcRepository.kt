package nl.bryanderidder.ornaguide.npc.persistence

import androidx.annotation.WorkerThread
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import nl.bryanderidder.ornaguide.npc.model.Npc
import nl.bryanderidder.ornaguide.shared.network.OrnaClient
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
    fun fetchNpcList(
        requestBody: NpcRequestBody = NpcRequestBody(),
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit,
    ) = flow<List<Npc>> {
        val npcList = dao.getNpcList()
        if (!npcList.isNullOrEmpty()) {
            emit(npcList)
            return@flow
        }
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
                onError(message())
                Timber.e(message())
            }
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(Dispatchers.IO)

    fun fetchNpc(
        id: Int,
        onError: (String?) -> Unit
    ) = flow<Npc> {
        val dbResult = dao.getNpc(id)
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
                onError(message())
                Timber.e(message())
            }
    }
}