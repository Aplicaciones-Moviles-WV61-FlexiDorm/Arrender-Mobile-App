package pe.edu.upc.flexiarrendermobile.factories

import pe.edu.upc.flexiarrendermobile.MyApplication
import pe.edu.upc.flexiarrendermobile.model.local.ArrenderDao

class ArrenderDaoFactory {

    companion object{

        private var arrenderDao: ArrenderDao? = null
        fun getArrenderDao(): ArrenderDao {
            if(arrenderDao == null){
                arrenderDao = AppDatabaseFactory.getAppDatabase(MyApplication.getContext()).arrenderDao()
            }
            return arrenderDao as ArrenderDao
        }
    }
}