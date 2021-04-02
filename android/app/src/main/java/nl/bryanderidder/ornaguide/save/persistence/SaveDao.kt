package nl.bryanderidder.ornaguide.save.persistence

import androidx.room.*
import nl.bryanderidder.ornaguide.save.model.Save

@Dao
interface SaveDao {

    @Query("SELECT * FROM Save")
    suspend fun getSaveList(): List<Save>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSave(Save: Save)

    @Delete
    suspend fun deleteSave(Save: Save)

    @Query("SELECT EXISTS(SELECT * FROM Save WHERE id = :id AND type = :type)")
    suspend fun isSaveExists(id: Int, type: String): Boolean
}
