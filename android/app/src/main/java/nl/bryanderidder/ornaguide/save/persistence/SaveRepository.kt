package nl.bryanderidder.ornaguide.save.persistence

import androidx.annotation.WorkerThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import nl.bryanderidder.ornaguide.save.model.Save

/**
 * Main repository to fetch data from api and store in local db.
 * @author Bryan de Ridder
 */
class SaveRepository(
    private val dao: SaveDao,
) {
    @WorkerThread
    fun fetchSaveList(
        onComplete: () -> Unit = {}
    ) = flow {
        val results = dao.getSaveList()
        emit(results)
    }.flowOn(Dispatchers.IO).onCompletion { onComplete() }

    @WorkerThread
    suspend fun insertSave(save: Save) =
        dao.insertSave(save)

    @WorkerThread
    suspend fun isSaveExists(save: Save) =
        dao.isSaveExists(save.id, save.type)

    @WorkerThread
    suspend fun deleteSave(save: Save) =
        dao.deleteSave(save)

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
}