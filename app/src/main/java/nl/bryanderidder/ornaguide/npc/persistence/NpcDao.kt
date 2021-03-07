package nl.bryanderidder.ornaguide.npc.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import nl.bryanderidder.ornaguide.npc.model.Npc

@Dao
interface NpcDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNpcList(NpcList: List<Npc>)

    @Query("SELECT * FROM Npc ORDER BY tier LIMIT 100")
    suspend fun getNpcList(): List<Npc>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNpc(Npc: Npc)

    @Query("SELECT * FROM Npc WHERE id = :id")
    suspend fun getNpc(id: Int): Npc
}
