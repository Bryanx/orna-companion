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
import nl.bryanderidder.ornaguide.item.model.Item
import nl.bryanderidder.ornaguide.shared.database.OrnaTypeConverters
import nl.bryanderidder.ornaguide.shared.network.OrnaClient
import nl.bryanderidder.ornaguide.shared.util.NetworkUtil.handleException
import nl.bryanderidder.ornaguide.shared.util.NetworkUtil.handleExceptionWithNetworkMessage
import timber.log.Timber

/**
 * Main repository to fetch data from api and store in local db.
 * @author Bryan de Ridder
 */
class ItemRepository(
    private val client: OrnaClient,
    private val dao: ItemDao,
    private val converters: OrnaTypeConverters,
) {

    @WorkerThread
    fun getItemListFromDb(
        requestBody: ItemRequestBody = ItemRequestBody(),
        onStart: () -> Unit = {},
        onComplete: () -> Unit = {},
        onError: (String?) -> Unit,
    ) = flow {
        val itemList = dao.getItemList()
        if (!itemList.isNullOrEmpty())
            emit(itemList)
        else
            syncDbWithNetwork(requestBody, onError = onError)
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun syncDbWithNetwork(
        requestBody: ItemRequestBody = ItemRequestBody(),
        onStart: () -> Unit = {},
        onComplete: () -> Unit = {},
        onError: (String?) -> Unit,
    ) = flow<List<Item>> {
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
                handleExceptionWithNetworkMessage(onError, exception)
            }
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(Dispatchers.IO)

    fun fetchItem(
        id: Int,
        onError: (String?) -> Unit
    ) = flow {
        val dbResult = getItemFromDb(id)
        if (dbResult != null)
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
                handleException(onError, exception)
            }
    }

    @WorkerThread
    suspend fun getItemFromDb(id: Int): Item? = dao.getItem(id)

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
    fun fetchAllPossibleTypes() = flow {
        val results = dao.getAllPossibleTypes()
        emit(results)
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun fetchAllPossibleElements() = flow {
        val results = dao.getAllPossibleElements()
        emit(results)
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun fetchAllPossibleEquippedBy() = flow {
        val results = dao.getAllPossibleEquippedBy().flatMap { it.equippedBy }.distinct().toList()
        emit(results)
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun fetchAllPossibleGives() = flow {
        val results = dao.getAllPossibleGives()
            .flatMap { converters.toStringList(it) }
            .distinct()
        emit(results)
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun fetchAllPossibleImmunities() = flow {
        val results = dao.getAllPossibleImmunities()
            .flatMap { converters.toStringList(it) }
            .distinct()
        emit(results)
    }.flowOn(Dispatchers.IO)
}