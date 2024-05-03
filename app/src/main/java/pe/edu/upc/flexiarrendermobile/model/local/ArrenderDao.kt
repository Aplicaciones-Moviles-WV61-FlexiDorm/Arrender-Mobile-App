package pe.edu.upc.flexiarrendermobile.model.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface ArrenderDao {

    @Insert
    fun insert(arrenderEntity: ArrenderEntity)


        @Query("DELETE FROM arrender WHERE id = :heroId")
        fun deleteById(heroId: String)

    @Query("DELETE FROM arrender")
    fun deleteAllArrendersLocal()

    //Traer un solo usuario
    @Query("SELECT * FROM arrender")
    fun getArrender(): List<ArrenderEntity>

    //Traer un solo usuario
    @Query("SELECT * FROM arrender WHERE id = :id")
    fun getArrenderById(id: String): ArrenderEntity?


    //Traer token de usuario
    @Query("SELECT token FROM arrender WHERE id = :id")
    fun getToken(id: String): String


}