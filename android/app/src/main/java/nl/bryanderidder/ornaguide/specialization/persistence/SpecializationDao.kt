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

    @Query("SELECT * FROM Specialization ORDER BY tier")
    suspend fun getSpecializationList(): List<Specialization>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpecialization(Specialization: Specialization)

    @Query("SELECT * FROM Specialization WHERE id = :id")
    suspend fun getSpecialization(id: Int): Specialization?

    @Query("SELECT DISTINCT tier FROM Specialization ORDER BY tier")
    suspend fun getAllPossibleTiers(): List<Int>

    @Query(
        """
      SELECT *
      FROM Specialization
      JOIN SpecializationFTS ON Specialization.name = SpecializationFTS.name
      WHERE SpecializationFTS MATCH :query
    """
    )
    suspend fun search(query: String): List<Specialization>
}
