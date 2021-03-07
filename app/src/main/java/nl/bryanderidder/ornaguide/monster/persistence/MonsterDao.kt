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

    @Query("SELECT * FROM Monster ORDER BY tier LIMIT 100")
    suspend fun getMonsterList(): List<Monster>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMonster(Monster: Monster)

    @Query("SELECT * FROM Monster WHERE id = :id")
    suspend fun getMonster(id: Int): Monster
}
