package nl.bryanderidder.ornaguide.pet.persistence

import androidx.annotation.WorkerThread
import com.skydoves.sandwich.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import nl.bryanderidder.ornaguide.pet.model.Pet
import nl.bryanderidder.ornaguide.shared.network.OrnaClient
import nl.bryanderidder.ornaguide.shared.util.NetworkUtil
import timber.log.Timber

/**
 * Main repository to fetch data from api and store in local db.
 * @author Bryan de Ridder
 */
class PetRepository(
    private val client: OrnaClient,
    private val dao: PetDao,
) {
    @WorkerThread
    fun getPetListFromDb(
        requestBody: PetRequestBody = PetRequestBody(),
        onStart: () -> Unit = {},
        onComplete: () -> Unit = {},
        onError: (String?) -> Unit,
    ) = flow {
        val petList = dao.getPetList()
        if (!petList.isNullOrEmpty())
            emit(petList)
        else
            syncDbWithNetwork(requestBody, onError = onError)
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun syncDbWithNetwork(
        requestBody: PetRequestBody = PetRequestBody(),
        onStart: () -> Unit = {},
        onComplete: () -> Unit = {},
        onError: (String?) -> Unit,
    ) = flow<List<Pet>> {
        client.fetchPetList(requestBody)
            .suspendOnSuccess {
                dao.insertPetList(response.body() ?: listOf())
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

    fun fetchPet(
        id: Int,
        onError: (String?) -> Unit
    ) = flow {
        val dbResult = getPetFromDb(id)
        if (dbResult != null)
            emit(dbResult)
        client.fetchPetList(PetRequestBody(id))
            .suspendOnSuccess {
                // the network object has changed replace it in the db.
                val networkResult = response.body()?.firstOrNull()
                if (networkResult != null && dbResult != networkResult) {
                    dao.insertPet(networkResult)
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
    suspend fun getPetFromDb(id: Int): Pet? = dao.getPet(id)

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