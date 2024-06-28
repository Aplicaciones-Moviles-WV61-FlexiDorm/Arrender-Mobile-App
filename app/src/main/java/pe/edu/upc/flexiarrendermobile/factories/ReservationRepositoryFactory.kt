package pe.edu.upc.flexiarrendermobile.factories

import pe.edu.upc.flexiarrendermobile.repository.ReservationRepository

class ReservationRepositoryFactory {

    companion object{
        private var reservationRepository: ReservationRepository? = null

        fun getReservationRepositoryFactory(token:String): ReservationRepository {
            if(reservationRepository == null){
                reservationRepository = ReservationRepository(
                    reservationService = ReservationServiceFactory.getReservationServiceFactory(token),
                    token
                )
            }

            return reservationRepository as ReservationRepository
        }
    }
}