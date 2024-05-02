package pe.edu.upc.flexiarrendermobile.factories

import pe.edu.upc.flexiarrendermobile.model.remote.ArrenderService
import pe.edu.upc.flexiarrendermobile.model.remote.RoomService

class RoomServiceFactory {

    companion object {

        private var roomService: RoomService? = null
        fun getRoomServiceFactory(token:String): RoomService {
            if (roomService == null) {

                roomService = RetrofitFactory.getRetrofit(token).create(RoomService::class.java)

            }
            return roomService as RoomService
        }
    }
}