package nl.bryanderidder.ornaguide.characterclass.persistence

import androidx.annotation.WorkerThread
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import nl.bryanderidder.ornaguide.characterclass.model.CharacterClass
import nl.bryanderidder.ornaguide.shared.network.OrnaClient
import timber.log.Timber

/**
 * CharacterClass repository to fetch data from api and store in local db.
 * @author Bryan de Ridder
 */
class CharacterClassRepository(
    private val client: OrnaClient,
    private val dao: CharacterClassDao,
) {

    @WorkerThread
    fun fetchCharacterClassList(
        requestBody: CharacterClassRequestBody = CharacterClassRequestBody(),
        onStart: () -> Unit = {},
        onComplete: () -> Unit = {},
        onError: (String?) -> Unit,
    ) = flow<List<CharacterClass>> {
        val characterClassList = dao.getCharacterClassList()
        if (!characterClassList.isNullOrEmpty()) {
            emit(characterClassList)
            return@flow
        }
        client.fetchCharacterClassList(requestBody)
            .suspendOnSuccess {
                dao.insertCharacterClassList(response.body() ?: listOf())
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

    fun fetchCharacterClass(
        id: Int,
        onError: (String?) -> Unit
    ) = flow<CharacterClass> {
        val dbResult = dao.getCharacterClass(id)
        emit(dbResult)
        client.fetchCharacterClassList(CharacterClassRequestBody(id))
            .suspendOnSuccess {
                // the network object has changed replace it in the db.
                val networkResult = response.body()?.firstOrNull()
                if (networkResult != null && dbResult != networkResult) {
                    dao.insertCharacterClass(networkResult)
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