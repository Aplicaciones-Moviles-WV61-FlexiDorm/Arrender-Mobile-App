package pe.edu.upc.flexiarrendermobile.factories

import pe.edu.upc.flexiarrendermobile.repository.ArrenderRepository
import pe.edu.upc.flexiarrendermobile.repository.RoomRepository

class RoomRepositoryFactory {


    companion object {
        private var roomRepository: RoomRepository? = null

        fun getRoomRepositoryFactory(token:String): RoomRepository {
            if (roomRepository == null) {
                roomRepository = RoomRepository(
                    roomService = RoomServiceFactory.getRoomServiceFactory(token),
                    token=token
                )
            }
            return roomRepository as RoomRepository
        }


        }
    }


