package nl.bryanderidder.ornaguide.item.persistence

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
import nl.bryanderidder.ornaguide.shared.database.OrnaTypeConverters
import nl.bryanderidder.ornaguide.shared.network.OrnaClient
import nl.bryanderidder.ornaguide.shared.util.NetworkUtil
import timber.log.Timber

/**
 * Repository to fetch data from api
 * @author Bryan de Ridder
 */
class ItemAssessRepository(
    private val client: OrnaClient,
    private val dao: ItemAssessDao,
    private val converters: OrnaTypeConverters,
) {

    @WorkerThread
    fun assessItem(
        body: ItemAssessRequestBody,
        onError: (String?) -> Unit,
    ) = flow {
        client.assessItem(body)
            .suspendOnSuccess {
                val result = response.body()
                if (result != null) {
                    if (result.quality != "0") {
                        result.requestBody = converters.fromItemAssessRequestBody(body)
                        dao.insertItemAssess(result)
                    }
                    emit(result)
                } else
                    onError("no result")
            }
            .onError {
                onError(message())
                Timber.e(message())
            }
            .onException {
                NetworkUtil.handleExceptionWithNetworkMessage(onError, exception)
            }
    }

    @WorkerThread
    fun getItemAssessList(
        itemId: Int,
        onStart: () -> Unit = {},
        onComplete: () -> Unit = {},
        onError: (String?) -> Unit,
    ) = flow {
        emit(dao.getItemAssessList(itemId))
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(Dispatchers.IO)
}