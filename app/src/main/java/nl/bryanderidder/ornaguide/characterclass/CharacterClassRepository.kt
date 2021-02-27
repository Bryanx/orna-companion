package nl.bryanderidder.ornaguide.characterclass

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
import nl.bryanderidder.ornaguide.shared.network.CharacterClassRequestBody
import nl.bryanderidder.ornaguide.shared.network.OrnaClient
import timber.log.Timber

/**
 * Main repository to fetch data from api and store in local db.
 * @author Bryan de Ridder
 */
class CharacterClassRepository(private val ornaClient: OrnaClient) {

    @WorkerThread
    fun fetchCharacterClassList(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ) = flow<List<CharacterClass>> {
        ornaClient.fetchCharacterClassList(CharacterClassRequestBody(tier = 1))
            .suspendOnSuccess {
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

    @WorkerThread
    fun fetchCharacterClass(
        id: Int,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ) = flow {
        ornaClient.fetchCharacterClassList(CharacterClassRequestBody(id))
            .suspendOnSuccess {
                emit(response.body()?.first() ?: CharacterClass())
            }
            .onError {
                onError(message())
                Timber.e(message())
            }
            .onException {
                onError(message())
                Timber.e(message())
            }
    }.onCompletion { onComplete() }.flowOn(Dispatchers.IO)
}