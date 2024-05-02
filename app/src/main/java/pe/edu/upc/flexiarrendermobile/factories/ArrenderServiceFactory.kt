package pe.edu.upc.flexiarrendermobile.factories

import pe.edu.upc.flexiarrendermobile.model.remote.ArrenderService

class ArrenderServiceFactory {

    companion object {
        private var arrenderService: ArrenderService? = null
        fun getArrenderService(token:String): ArrenderService {
            if (arrenderService == null) {
                arrenderService = RetrofitFactory.getRetrofit(token).create(ArrenderService::class.java)
            }
            return arrenderService as ArrenderService
        }
    }
}