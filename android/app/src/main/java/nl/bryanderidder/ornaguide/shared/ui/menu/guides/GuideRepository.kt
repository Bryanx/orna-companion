package nl.bryanderidder.ornaguide.shared.ui.menu.guides

import androidx.annotation.WorkerThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import nl.bryanderidder.ornaguide.shared.network.OrnaClient
import nl.bryanderidder.ornaguide.shared.util.NOTION_API
import nl.bryanderidder.ornaguide.shared.util.NOTION_GUIDE_DATABASE_ID
import nl.bryanderidder.ornaguide.shared.util.NetworkUtil
import notion.api.v1.NotionClient
import notion.api.v1.model.pages.Page
import timber.log.Timber

/**
 * Main repository to fetch data from notion api.
 * @author Bryan de Ridder
 */
class GuideRepository(
    private val client: OrnaClient,
) {
    @WorkerThread
    fun getGuideListFromDb(
        onStart: () -> Unit = {},
        onComplete: () -> Unit = {},
        onError: (String?) -> Unit,
    ) = flow {
        val results: List<Page> = NotionClient(NOTION_API).queryDatabase(NOTION_GUIDE_DATABASE_ID).results
        val guides = results.map { it.properties?.get("Name")?.title?.firstOrNull()?.plainText ?: "" }.filter { it.isNotEmpty() }
        Timber.d(results.toString())
        if (!guides.isNullOrEmpty())
            emit(guides)
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(Dispatchers.IO)

//    fun fetchGuide(
//        id: Int,
//        onError: (String?) -> Unit
//    ) = flow {
//        val dbResult = getGuideFromDb(id)
//        if (dbResult != null)
//            emit(dbResult)
//        client.fetchGuideList(GuideRequestBody(id))
//            .suspendOnSuccess {
//                // the network object has changed replace it in the db.
//                val networkResult = response.body()?.firstOrNull()
//                if (networkResult != null && dbResult != networkResult) {
//                    dao.insertGuide(networkResult)
//                    emit(networkResult)
//                }
//            }
//            .onError {
//                onError(message())
//                Timber.e(message())
//            }
//            .onException {
//                NetworkUtil.handleException(onError, exception)
//            }
//    }
}