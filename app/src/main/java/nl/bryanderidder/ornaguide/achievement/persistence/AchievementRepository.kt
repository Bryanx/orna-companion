package nl.bryanderidder.ornaguide.achievement.persistence

import androidx.annotation.WorkerThread
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import nl.bryanderidder.ornaguide.achievement.model.Achievement
import nl.bryanderidder.ornaguide.shared.network.OrnaClient
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
    fun fetchAchievementList(
        requestBody: AchievementRequestBody = AchievementRequestBody(),
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit,
    ) = flow<List<Achievement>> {
        val achievementList = dao.getAchievementList()
        if (!achievementList.isNullOrEmpty()) {
            emit(achievementList)
            return@flow
        }
        client.fetchAchievementList(requestBody)
            .suspendOnSuccess {
                dao.insertAchievementList(response.body() ?: listOf())
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