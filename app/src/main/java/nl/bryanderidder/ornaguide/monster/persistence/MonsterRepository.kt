package nl.bryanderidder.ornaguide.monster.persistence

import androidx.annotation.WorkerThread
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import nl.bryanderidder.ornaguide.monster.model.Monster
import nl.bryanderidder.ornaguide.shared.network.OrnaClient
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
    fun fetchMonsterList(
        requestBody: MonsterRequestBody = MonsterRequestBody(),
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit,
    ) = flow<List<Monster>> {
        val monsterList = dao.getMonsterList()
        if (!monsterList.isNullOrEmpty()) {
            emit(monsterList)
            return@flow
        }
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
                onError(message())
                Timber.e(message())
            }
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(Dispatchers.IO)

    fun fetchMonster(
        id: Int,
        onError: (String?) -> Unit
    ) = flow<Monster> {
        val dbResult = dao.getMonster(id)
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
                onError(message())
                Timber.e(message())
            }
    }

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