package pe.edu.upc.flexiarrendermobile.model.remote

import pe.edu.upc.flexiarrendermobile.model.data.ApiResponseRoom
import pe.edu.upc.flexiarrendermobile.model.data.ApiResponseRoomList
import pe.edu.upc.flexiarrendermobile.model.data.RegisterRoomRequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface RoomService {

    @POST("room/registerRoom")
    fun registerRoom(
        @Header("Authorization") token:String,
        @Body requestRegisterRoomBody:RegisterRoomRequestBody

    ): Call<ApiResponseRoom>

    @GET("room/getRoomsByArrenderId/{arrenderId}")
    fun getRoomsByArrenderId(
        @Header("Authorization") token:String,
        @Path("arrenderId") arrenderId:Long
    ): Call<ApiResponseRoomList>


}