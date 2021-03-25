package nl.bryanderidder.ornaguide.item.persistence

import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.flow.flow
import nl.bryanderidder.ornaguide.item.model.ItemAssess
import nl.bryanderidder.ornaguide.shared.network.OrnaClient
import nl.bryanderidder.ornaguide.shared.util.NetworkUtil
import timber.log.Timber

/**
 * Repository to fetch data from api
 * @author Bryan de Ridder
 */
class ItemAssessRepository(private val client: OrnaClient) {

    fun assessItem(
        body: ItemAssessRequestBody,
        onError: (String?) -> Unit,
    ) = flow {
        client.assessItem(body)
            .suspendOnSuccess {
                val result = response.body()
                Timber.d("result: $result")
                if (result != null)
                    emit(result)
                else
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
}