package pe.edu.upc.flexiarrendermobile.persistence

import androidx.room.Database
import pe.edu.upc.flexiarrendermobile.model.local.ArrenderEntity

@Database(entities=[ArrenderEntity::class], version=1)//Cada vez que cambie la estrcutura de las entidadades se debe cambiar la version
abstract class AppDatabase {
}