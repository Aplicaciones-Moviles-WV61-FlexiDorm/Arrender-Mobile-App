package pe.edu.upc.flexiarrendermobile.factories

import android.content.Context
import androidx.room.Room
import pe.edu.upc.flexiarrendermobile.persistence.AppDatabase

class AppDatabaseFactory {

    companion object{
        private var appDatabase: AppDatabase? = null
        fun getAppDatabase(context: Context): AppDatabase {
            if(appDatabase == null){
                appDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "flexiarrender_Database")
                    .allowMainThreadQueries().build()
            }
            return appDatabase as AppDatabase
        }
    }
}