package pe.edu.upc.flexiarrendermobile.repository

import pe.edu.upc.flexiarrendermobile.model.data.ApiResponseReservationList
import pe.edu.upc.flexiarrendermobile.model.remote.ReservationService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class ReservationRepository(
    private val reservationService: ReservationService,
    private val token: String
) {
    fun getReservationsByArrenderId(
        arrenderId: Long,
        callback: (ApiResponseReservationList?, Int?, String?) -> Unit
    ) {
        val call = reservationService.getReservationsByArrenderId("Bearer $token", arrenderId.toString())

        call.enqueue(object : Callback<ApiResponseReservationList> {
            override fun onResponse(
                call: Call<ApiResponseReservationList>,
                response: Response<ApiResponseReservationList>
            ) {
                if (response.isSuccessful) {
                    val apiResponseReservationList = response.body()
                    val statusCode = response.code()
                    callback(apiResponseReservationList, statusCode, null)
                } else {
                    // La respuesta no es exitosa, obtén el código de estado y el cuerpo del error
                    val errorCode = response.code()
                    val errorBody = response.errorBody()?.string()

                    callback(null, errorCode, errorBody)
                }
            }

            override fun onFailure(call: Call<ApiResponseReservationList>, t: Throwable) {
                var errorCode =
                    -1 // Valor por defecto en caso de que no se pueda obtener el código de estado HTTP

                // Verificar si la excepción está relacionada con una solicitud HTTP
                if (t is HttpException) {
                    // Si es una excepción relacionada con HTTP, obtén el código de estado HTTP
                    errorCode = t.code()
                }

                // Llamar al callback con el código de estado HTTP y el mensaje de error
                callback(null, errorCode, t.message)
            }

        })
    }

}



