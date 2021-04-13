package nl.bryanderidder.ornaguide.skill.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import nl.bryanderidder.ornaguide.skill.model.Skill

@Dao
interface SkillDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSkillList(CharacterClassList: List<Skill>)

    @Query("SELECT * FROM Skill ORDER BY tier")
    suspend fun getSkillList(): List<Skill>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSkill(Skill: Skill)

    @Query("SELECT * FROM Skill WHERE id = :id")
    suspend fun getSkill(id: Int): Skill?

    @Query("SELECT DISTINCT tier FROM Skill ORDER BY tier")
    fun getAllPossibleTiers(): List<Int>

    @Query("SELECT DISTINCT type FROM Skill")
    fun getAllPossibleTypes(): List<String>

    @Query("SELECT DISTINCT element FROM Skill")
    fun getAllPossibleElements(): List<String>

    @Query(
        """
      SELECT *
      FROM Skill
      JOIN SkillFTS ON Skill.name = SkillFTS.name
      WHERE SkillFTS MATCH :query
    """
    )
    suspend fun search(query: String): List<Skill>
}
