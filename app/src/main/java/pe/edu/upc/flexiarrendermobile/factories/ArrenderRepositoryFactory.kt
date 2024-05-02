package pe.edu.upc.flexiarrendermobile.factories

import pe.edu.upc.flexiarrendermobile.repository.ArrenderRepository

class ArrenderRepositoryFactory {

    companion object {
        private var arrenderRepository: ArrenderRepository? = null

        fun getArrenderRepository(token:String): ArrenderRepository {
            if (arrenderRepository == null) {
                arrenderRepository = ArrenderRepository(
                    arrenderService = ArrenderServiceFactory.getArrenderService(token),
                    arrenderDao = ArrenderDaoFactory.getArrenderDao()
                )
            }
            return arrenderRepository as ArrenderRepository
        }
    }
}