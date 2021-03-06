package nl.bryanderidder.ornaguide.skill.persistence

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
import nl.bryanderidder.ornaguide.shared.network.OrnaClient
import nl.bryanderidder.ornaguide.skill.model.Skill
import timber.log.Timber

/**
 * Description
 * @author Bryan de Ridder
 */
class SkillRepository(
    private val client: OrnaClient,
    private val dao: SkillDao,
) {

    @WorkerThread
    fun fetchSkillList(
        requestBody: SkillRequestBody = SkillRequestBody(),
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit,
    ) = flow<List<Skill>> {
        val skillList = dao.getSkillList()
        if (!skillList.isNullOrEmpty()) {
            emit(skillList)
            return@flow
        }
        client.fetchSkillList(requestBody)
            .suspendOnSuccess {
                dao.insertSkillList(response.body() ?: listOf())
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

    fun fetchSkill(
        id: Int,
        onError: (String?) -> Unit
    ) = flow<Skill> {
        val dbResult = dao.getSkill(id)
        emit(dbResult)
        client.fetchSkillList(SkillRequestBody(id))
            .suspendOnSuccess {
                // the network object has changed replace it in the db.
                val networkResult = response.body()?.firstOrNull()
                if (networkResult != null && dbResult != networkResult) {
                    dao.insertSkill(networkResult)
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