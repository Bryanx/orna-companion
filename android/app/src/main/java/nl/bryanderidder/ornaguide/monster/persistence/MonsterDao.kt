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
    suspend fun getMonster(id: Int): Monster?

    @Query("SELECT DISTINCT tier FROM Monster ORDER BY tier")
    suspend fun getAllPossibleTiers(): List<Int>

    @Query("SELECT DISTINCT spawns FROM Monster")
    suspend fun getAllPossibleSpawns(): List<String>

    @Query("SELECT DISTINCT weakTo FROM Monster")
    suspend fun getAllPossibleWeakTos(): List<String>

    @Query("SELECT DISTINCT resistantTo FROM Monster")
    suspend fun getAllPossibleResistantTos(): List<String>

    @Query("SELECT DISTINCT immuneTo FROM Monster")
    suspend fun getAllPossibleImmuneTos(): List<String>

    @Query("SELECT DISTINCT immuneToStatus FROM Monster")
    suspend fun getAllPossibleImmuneToStatuses(): List<String>

    @Query("SELECT DISTINCT vulnerableToStatus FROM Monster")
    suspend fun getAllPossibleVulnerableToStatuses(): List<String>

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
