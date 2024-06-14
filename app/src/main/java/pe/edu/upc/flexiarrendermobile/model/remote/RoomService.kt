package pe.edu.upc.flexiarrendermobile.model.remote

import okhttp3.MultipartBody
import okhttp3.RequestBody
import pe.edu.upc.flexiarrendermobile.model.data.ApiResponseRoom
import pe.edu.upc.flexiarrendermobile.model.data.ApiResponseRoomList
import pe.edu.upc.flexiarrendermobile.model.data.RegisterRoomRequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface RoomService {

    @Multipart
    @POST("room/registerRoom")
    fun registerRoom(
        @Header("Authorization") token:String,
        @Part("request") request: RequestBody,
        @Part image: MultipartBody.Part?

    ): Call<ApiResponseRoom>

    @GET("room/getRoomsByArrenderId/{arrenderId}")
    fun getRoomsByArrenderId(
        @Header("Authorization") token:String,
        @Path("arrenderId") arrenderId:Long
    ): Call<ApiResponseRoomList>


}