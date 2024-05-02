package pe.edu.upc.flexiarrendermobile.model.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface ArrenderDao {

    @Insert
    fun insert(arrenderEntity: ArrenderEntity)

    @Delete
    fun delete(arrenderEntity: ArrenderEntity)

    //Traer un solo usuario
    @Query("SELECT * FROM arrender")
    fun getArrender(): List<ArrenderEntity>


}