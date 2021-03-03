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

    @Query("SELECT * FROM Achievement")
    suspend fun getAchievementList(): List<Achievement>
}
