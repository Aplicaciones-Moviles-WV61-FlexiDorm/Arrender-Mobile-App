package pe.edu.upc.flexiarrendermobile.factories

import pe.edu.upc.flexiarrendermobile.model.remote.ArrenderService

class ArrenderServiceFactory {

    companion object {
        private var arrenderService: ArrenderService? = null
        fun getArrenderService(): ArrenderService {
            if (arrenderService == null) {
                arrenderService = RetrofitFactory.getRetrofit().create(ArrenderService::class.java)
            }
            return arrenderService as ArrenderService
        }
    }
}