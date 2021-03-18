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

    @Query("SELECT * FROM Monster ORDER BY tier")
    suspend fun getMonsterList(): List<Monster>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMonster(Monster: Monster)

    @Query("SELECT * FROM Monster WHERE id = :id")
    suspend fun getMonster(id: Int): Monster

    @Query("SELECT DISTINCT tier FROM Monster ORDER BY tier")
    fun getAllPossibleTiers(): List<Int>

    @Query("SELECT DISTINCT spawns FROM Monster")
    fun getAllPossibleSpawns(): List<String>

    @Query(
        """
      SELECT *
      FROM Monster
      JOIN MonsterFTS ON Monster.name = MonsterFTS.name
      WHERE MonsterFTS MATCH :query
    """
    )
    suspend fun search(query: String): List<Monster>
}
