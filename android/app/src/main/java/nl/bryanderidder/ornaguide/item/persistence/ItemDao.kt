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

    @Query("SELECT * FROM Item ORDER BY tier")
    suspend fun getItemList(): List<Item>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(Item: Item)

    @Query("SELECT * FROM Item WHERE id = :id")
    suspend fun getItem(id: Int): Item?

    @Query("SELECT DISTINCT tier FROM Item ORDER BY tier")
    suspend fun getAllPossibleTiers(): List<Int>

    @Query("SELECT DISTINCT type FROM Item ORDER BY type")
    suspend fun getAllPossibleTypes(): List<String>

    @Query("SELECT DISTINCT element FROM Item ORDER BY element")
    suspend fun getAllPossibleElements(): List<String>

    @Query("SELECT DISTINCT equippedBy FROM Item")
    suspend fun getAllPossibleEquippedBy(): List<String>

    @Query("SELECT DISTINCT category FROM Item ORDER BY category")
    suspend fun getAllPossibleCategories(): List<String>

    @Query("SELECT DISTINCT gives FROM Item")
    suspend fun getAllPossibleGives(): List<String>

    @Query("SELECT DISTINCT causes FROM Item")
    suspend fun getAllPossibleCauses(): List<String>

    @Query("SELECT DISTINCT immunities FROM Item")
    suspend fun getAllPossibleImmunities(): List<String>

    @Query("SELECT DISTINCT cures FROM Item")
    suspend fun getAllPossibleCures(): List<String>

    @Query(
        """
      SELECT *
      FROM Item
      JOIN ItemFTS ON Item.name = ItemFTS.name
      WHERE ItemFTS MATCH :query
    """
    )
    suspend fun search(query: String): List<Item>
}
