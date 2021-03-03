package nl.bryanderidder.ornaguide.specialization.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import nl.bryanderidder.ornaguide.specialization.model.Specialization

@Dao
interface SpecializationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpecializationList(SpecializationList: List<Specialization>)

    @Query("SELECT * FROM Specialization ORDER BY tier LIMIT 100")
    suspend fun getSpecializationList(): List<Specialization>
}
