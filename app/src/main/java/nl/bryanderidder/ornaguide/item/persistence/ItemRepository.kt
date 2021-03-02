package nl.bryanderidder.ornaguide.item.persistence

import androidx.annotation.WorkerThread
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import nl.bryanderidder.ornaguide.item.model.Item
import nl.bryanderidder.ornaguide.shared.network.OrnaClient
import timber.log.Timber

/**
 * Main repository to fetch data from api and store in local db.
 * @author Bryan de Ridder
 */
class ItemRepository(
    private val client: OrnaClient,
    private val dao: ItemDao,
) {

    @WorkerThread
    fun fetchItemList(
        requestBody: ItemRequestBody = ItemRequestBody(tier = 1),
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit,
    ) = flow<List<Item>> {
        val itemList = dao.getItemList()
        if (!itemList.isNullOrEmpty()) {
            emit(itemList)
            return@flow
        }
        client.fetchItemList(requestBody)
            .suspendOnSuccess {
                dao.insertItemList(response.body() ?: listOf())
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