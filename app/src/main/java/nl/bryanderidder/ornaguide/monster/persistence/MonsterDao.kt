package nl.bryanderidder.ornaguide.monster.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import nl.bryanderidder.ornaguide.monster.model.Monster

@Dao
interface MonsterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMonsterList(MonsterList: List<Monster>)

    @Query("SELECT * FROM Monster")
    suspend fun getMonsterList(): List<Monster>
}
