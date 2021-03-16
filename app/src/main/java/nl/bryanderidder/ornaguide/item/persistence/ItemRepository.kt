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
        requestBody: ItemRequestBody = ItemRequestBody(),
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

    fun fetchItem(
        id: Int,
        onError: (String?) -> Unit
    ) = flow<Item> {
        val dbResult = dao.getItem(id)
        emit(dbResult)
        client.fetchItemList(ItemRequestBody(id))
            .suspendOnSuccess {
                // the network object has changed replace it in the db.
                val networkResult = response.body()?.firstOrNull()
                if (networkResult != null && dbResult != networkResult) {
                    dao.insertItem(networkResult)
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
    fun search(query: String) = flow<List<Item>> {
        val results = dao.search("*$query*")
        emit(results)
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun fetchAllPossibleTiers() = flow<List<Int>> {
        val results = dao.getAllPossibleTiers()
        emit(results)
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun fetchAllPossibleTypes() = flow<List<String>> {
        val results = dao.getAllPossibleTypes()
        emit(results)
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun fetchAllPossibleElements() = flow<List<String>> {
        val results = dao.getAllPossibleElements()
        emit(results)
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun fetchAllPossibleEquippedBy() = flow<List<Item.IdNamePair>> {
        val results = dao.getAllPossibleEquippedBy().flatMap { it.equippedBy }.distinct().toList()
        emit(results)
    }.flowOn(Dispatchers.IO)
}