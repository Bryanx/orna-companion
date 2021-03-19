package nl.bryanderidder.ornaguide.save.persistence

import androidx.annotation.WorkerThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import nl.bryanderidder.ornaguide.save.model.Save

/**
 * Main repository to fetch data from api and store in local db.
 * @author Bryan de Ridder
 */
class SaveRepository(
    private val dao: SaveDao,
) {
    @WorkerThread
    fun fetchSaveList() = flow<List<Save>> {
        val results = dao.getSaveList()
        emit(results)
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    suspend fun insertSave(save: Save) =
        dao.insertSave(save)

    @WorkerThread
    suspend fun isSaveExists(save: Save) =
        dao.isSaveExists(save.id, save.type)

    @WorkerThread
    suspend fun deleteSave(save: Save) =
        dao.deleteSave(save)
}