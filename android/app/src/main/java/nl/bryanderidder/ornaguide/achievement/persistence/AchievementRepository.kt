package nl.bryanderidder.ornaguide.achievement.persistence

import androidx.annotation.WorkerThread
import com.skydoves.sandwich.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import nl.bryanderidder.ornaguide.achievement.model.Achievement
import nl.bryanderidder.ornaguide.shared.network.OrnaClient
import nl.bryanderidder.ornaguide.shared.util.NetworkUtil
import timber.log.Timber

/**
 * Main repository to fetch data from api and store in local db.
 * @author Bryan de Ridder
 */
class AchievementRepository(
    private val client: OrnaClient,
    private val dao: AchievementDao,
) {
    @WorkerThread
    fun getAchievementListFromDb(
        requestBody: AchievementRequestBody = AchievementRequestBody(),
        onStart: () -> Unit = {},
        onComplete: () -> Unit = {},
        onError: (String?) -> Unit,
    ) = flow {
        val achievementList = dao.getAchievementList()
        if (!achievementList.isNullOrEmpty())
            emit(achievementList)
        else
            syncDbWithNetwork(requestBody, onError = onError)
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun syncDbWithNetwork(
        requestBody: AchievementRequestBody = AchievementRequestBody(),
        onStart: () -> Unit = {},
        onComplete: () -> Unit = {},
        onError: (String?) -> Unit,
    ) = flow<List<Achievement>> {
        client.fetchAchievementList(requestBody)
            .suspendOnSuccess {
                dao.insertAchievementList(response.body() ?: listOf())
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

    fun fetchAchievement(
        id: Int,
        onError: (String?) -> Unit
    ) = flow {
        val dbResult = getAchievementFromDb(id)
        if (dbResult != null)
            emit(dbResult)
        client.fetchAchievementList(AchievementRequestBody(id))
            .suspendOnSuccess {
                // the network object has changed replace it in the db.
                val networkResult = response.body()?.firstOrNull()
                if (networkResult != null && dbResult != networkResult) {
                    dao.insertAchievement(networkResult)
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
    suspend fun getAchievementFromDb(id: Int): Achievement? = dao.getAchievement(id)

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