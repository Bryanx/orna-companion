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

    @Query("SELECT * FROM CharacterClass")
    suspend fun getCharacterClassList(): List<CharacterClass>
}
