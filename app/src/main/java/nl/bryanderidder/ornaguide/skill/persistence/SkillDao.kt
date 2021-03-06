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

    @Query("SELECT * FROM Skill WHERE tier = 10 ORDER BY tier LIMIT 100")
    suspend fun getSkillList(): List<Skill>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSkill(Skill: Skill)

    @Query("SELECT * FROM Skill WHERE id = :id")
    suspend fun getSkill(id: Int): Skill
}
