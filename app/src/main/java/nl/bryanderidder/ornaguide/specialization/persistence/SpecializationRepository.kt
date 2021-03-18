package nl.bryanderidder.ornaguide.specialization.persistence

import androidx.annotation.WorkerThread
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import nl.bryanderidder.ornaguide.shared.network.OrnaClient
import nl.bryanderidder.ornaguide.specialization.model.Specialization
import timber.log.Timber

/**
 * Main repository to fetch data from api and store in local db.
 * @author Bryan de Ridder
 */
class SpecializationRepository(
    private val client: OrnaClient,
    private val dao: SpecializationDao,
) {

    @WorkerThread
    fun fetchSpecializationList(
        requestBody: SpecializationRequestBody = SpecializationRequestBody(),
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit,
    ) = flow<List<Specialization>> {
        val specializationList = dao.getSpecializationList()
        if (!specializationList.isNullOrEmpty()) {
            emit(specializationList)
            return@flow
        }
        client.fetchSpecializationList(requestBody)
            .suspendOnSuccess {
                dao.insertSpecializationList(response.body() ?: listOf())
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

    fun fetchSpecialization(
        id: Int,
        onError: (String?) -> Unit
    ) = flow<Specialization> {
        val dbResult = dao.getSpecialization(id)
        emit(dbResult)
        client.fetchSpecializationList(SpecializationRequestBody(id))
            .suspendOnSuccess {
                // the network object has changed replace it in the db.
                val networkResult = response.body()?.firstOrNull()
                if (networkResult != null && dbResult != networkResult) {
                    dao.insertSpecialization(networkResult)
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
    fun search(query: String) = flow<List<Specialization>> {
        val results = dao.search("*$query*")
        emit(results)
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun fetchAllPossibleTiers() = flow<List<Int>> {
        val results = dao.getAllPossibleTiers()
        emit(results)
    }.flowOn(Dispatchers.IO)
}