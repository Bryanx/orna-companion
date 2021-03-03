package nl.bryanderidder.ornaguide.item.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import nl.bryanderidder.ornaguide.item.model.Item

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItemList(ItemList: List<Item>)

    @Query("SELECT * FROM Item WHERE tier = 10 ORDER BY tier LIMIT 100")
    suspend fun getItemList(): List<Item>
}
