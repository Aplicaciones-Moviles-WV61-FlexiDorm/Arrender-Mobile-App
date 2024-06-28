package pe.edu.upc.flexiarrendermobile.factories

import pe.edu.upc.flexiarrendermobile.model.remote.ReservationService

class ReservationServiceFactory {
    companion object{
        private var reservationService: ReservationService? = null

        fun getReservationServiceFactory(token:String): ReservationService {
            if (reservationService == null) {
                reservationService = RetrofitFactory.getRetrofit(token).create(ReservationService::class.java)
            }
            return reservationService as ReservationService
        }
    }
}