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

    @Query("SELECT * FROM Pet ORDER BY tier LIMIT 100")
    suspend fun getPetList(): List<Pet>
}
