package nl.bryanderidder.ornaguide.characterclass.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import nl.bryanderidder.ornaguide.characterclass.model.CharacterClass

@Dao
interface CharacterClassDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacterClassList(CharacterClassList: List<CharacterClass>)

    @Query("SELECT * FROM CharacterClass ORDER BY tier")
    suspend fun getCharacterClassList(): List<CharacterClass>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacterClass(CharacterClass: CharacterClass)

    @Query("SELECT * FROM CharacterClass WHERE id = :id")
    suspend fun getCharacterClass(id: Int): CharacterClass?

    @Query("SELECT DISTINCT tier FROM CharacterClass ORDER BY tier")
    suspend fun getAllPossibleTiers(): List<Int>

    @Query("SELECT DISTINCT preferredWeapons FROM CharacterClass")
    suspend fun getAllPossiblePreferredWeapons(): List<String>

    @Query(
        """
      SELECT *
      FROM CharacterClass
      JOIN CharacterClassFTS ON CharacterClass.name = CharacterClassFTS.name
      WHERE CharacterClassFTS MATCH :query
    """
    )
    suspend fun search(query: String): List<CharacterClass>
}
