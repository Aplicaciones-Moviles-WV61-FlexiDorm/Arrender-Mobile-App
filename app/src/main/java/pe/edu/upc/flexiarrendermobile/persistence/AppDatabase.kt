package pe.edu.upc.flexiarrendermobile.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pe.edu.upc.flexiarrendermobile.model.local.ArrenderDao
import pe.edu.upc.flexiarrendermobile.model.local.ArrenderEntity

@Database(entities=[ArrenderEntity::class], version=1)//Cada vez que cambie la estrcutura de las entidadades se debe cambiar la version
abstract class AppDatabase:RoomDatabase() {

    abstract fun arrenderDao(): ArrenderDao


}