package nl.bryanderidder.ornaguide.specialization.persistence

import androidx.annotation.WorkerThread
import com.skydoves.sandwich.*
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import nl.bryanderidder.ornaguide.shared.database.OrnaTypeConverters
import nl.bryanderidder.ornaguide.shared.network.OrnaClient
import nl.bryanderidder.ornaguide.shared.util.NetworkUtil
import nl.bryanderidder.ornaguide.specialization.model.Specialization
import timber.log.Timber

/**
 * Main repository to fetch data from api and store in local db.
 * @author Bryan de Ridder
 */
class SpecializationRepository(
    private val client: OrnaClient,
    private val dao: SpecializationDao,
    private val converters: OrnaTypeConverters,
) {

    @WorkerThread
    fun getSpecializationListFromDb(
        requestBody: SpecializationRequestBody = SpecializationRequestBody(),
        onStart: () -> Unit = {},
        onComplete: () -> Unit = {},
        onError: (String?) -> Unit,
    ) = flow {
        val specializationList = dao.getSpecializationList()
        if (!specializationList.isNullOrEmpty())
            emit(specializationList)
        else
            syncDbWithNetwork(requestBody, onError = onError)
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun syncDbWithNetwork(
        requestBody: SpecializationRequestBody = SpecializationRequestBody(),
        onStart: () -> Unit = {},
        onComplete: () -> Unit = {},
        onError: (String?) -> Unit,
    ) = flow<List<Specialization>> {
        client.fetchSpecializationList(requestBody)
            .suspendOnSuccess {
                dao.insertSpecializationList(response.body() ?: listOf())
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

    fun fetchSpecialization(
        id: Int,
        onError: (String?) -> Unit
    ) = flow {
        val dbResult = getSpecializationFromDb(id)
        if (dbResult != null)
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
                NetworkUtil.handleException(onError, exception)
            }
    }

    @WorkerThread
    suspend fun getSpecializationFromDb(id: Int): Specialization? = dao.getSpecialization(id)

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
    fun fetchAllPossiblePreferredWeapons() = flow {
        val results = dao.getAllPossiblePreferredWeapons()
            .flatMap(converters::toStringList)
            .distinct()
            .sorted()
        emit(results)
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun fetchAllPossibleBoosts() = flow {
        val results = dao.getAllPossibleBoosts()
            .flatMap(converters::toBoost)
            .map { it.formattedName() }
            .distinct()
            .sorted()
        emit(results)
    }.flowOn(Dispatchers.IO)
}