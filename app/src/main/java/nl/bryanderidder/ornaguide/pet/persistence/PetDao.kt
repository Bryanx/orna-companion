package nl.bryanderidder.ornaguide.pet.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import nl.bryanderidder.ornaguide.pet.model.Pet

@Dao
interface PetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPetList(PetList: List<Pet>)

    @Query("SELECT * FROM Pet ORDER BY tier")
    suspend fun getPetList(): List<Pet>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPet(Pet: Pet)

    @Query("SELECT * FROM Pet WHERE id = :id")
    suspend fun getPet(id: Int): Pet

    @Query("SELECT DISTINCT tier FROM Pet ORDER BY tier")
    suspend fun getAllPossibleTiers(): List<Int>
    @Query(
        """
      SELECT *
      FROM Pet
      JOIN PetFTS ON Pet.name = PetFTS.name
      WHERE PetFTS MATCH :query
    """
    )
    suspend fun search(query: String): List<Pet>
}
