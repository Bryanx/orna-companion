package nl.bryanderidder.ornaguide.item.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import nl.bryanderidder.ornaguide.item.model.ItemAssess


@Dao
interface ItemAssessDao {

    @Query("SELECT * FROM ItemAssess WHERE itemId = :itemId ORDER BY creationDate desc")
    suspend fun getItemAssessList(itemId: Int): List<ItemAssess>

    @Query("SELECT * FROM ItemAssess WHERE id = :id")
    suspend fun getItemAssess(id: Int): ItemAssess

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItemAssess(ItemAssess: ItemAssess)
}
