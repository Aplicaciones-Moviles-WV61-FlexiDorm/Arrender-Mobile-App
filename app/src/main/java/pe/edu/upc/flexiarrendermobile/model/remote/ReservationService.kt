package pe.edu.upc.flexiarrendermobile.model.remote

import pe.edu.upc.flexiarrendermobile.model.data.ApiResponseReservationList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ReservationService {

    @GET("rental/getRentalsByArrenderId/{arrenderId}")
    fun getReservationsByArrenderId(
        @Header("Authorization") token:String,
        @Path("arrenderId") arrenderId:String
    ): Call<ApiResponseReservationList>
}