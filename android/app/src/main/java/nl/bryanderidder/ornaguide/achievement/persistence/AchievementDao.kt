package nl.bryanderidder.ornaguide.achievement.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import nl.bryanderidder.ornaguide.achievement.model.Achievement

@Dao
interface AchievementDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAchievementList(AchievementList: List<Achievement>)

    @Query("SELECT * FROM Achievement ORDER BY tier")
    suspend fun getAchievementList(): List<Achievement>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAchievement(Achievement: Achievement)

    @Query("SELECT * FROM Achievement WHERE id = :id")
    suspend fun getAchievement(id: Int): Achievement?

    @Query("SELECT DISTINCT tier FROM Achievement ORDER BY tier")
    fun getAllPossibleTiers(): List<Int>

    @Query(
        """
      SELECT *
      FROM Achievement
      JOIN AchievementFTS ON Achievement.name = AchievementFTS.name
      WHERE AchievementFTS MATCH :query
    """
    )
    suspend fun search(query: String): List<Achievement>
}
