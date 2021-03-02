package nl.bryanderidder.ornaguide.pet.persistence

import androidx.annotation.WorkerThread
import com.skydoves.sandwich.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import nl.bryanderidder.ornaguide.pet.model.Pet
import nl.bryanderidder.ornaguide.shared.network.OrnaClient
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
    fun fetchPetList(
        requestBody: PetRequestBody = PetRequestBody(),
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit,
    ) = flow<List<Pet>> {
        val petList = dao.getPetList()
        if (!petList.isNullOrEmpty()) {
            emit(petList)
            return@flow
        }
        client.fetchPetList(requestBody)
            .suspendOnSuccess {
                dao.insertPetList(response.body() ?: listOf())
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
}