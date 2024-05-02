package pe.edu.upc.flexiarrendermobile.repository

import pe.edu.upc.flexiarrendermobile.model.data.ApiResponse
import pe.edu.upc.flexiarrendermobile.model.data.ApiResponseRoom
import pe.edu.upc.flexiarrendermobile.model.data.RegisterRoomRequestBody
import pe.edu.upc.flexiarrendermobile.model.remote.RoomService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class RoomRepository(
    private val roomService: RoomService,
    private val token: String
) {


    fun registerRoom(
        requestRegisterRoomBody: RegisterRoomRequestBody,
        callback: (ApiResponseRoom?, Int?, String?) -> Unit
    ) {
        println( "tokenen reposito: $token")
        val call=roomService.registerRoom("Bearer $token",requestRegisterRoomBody)

        call.enqueue(object : Callback<ApiResponseRoom> {
            override fun onResponse(call: Call<ApiResponseRoom>, response: Response<ApiResponseRoom>) {
                if (response.isSuccessful) {

                    val apiResponseRoom = response.body()
                    val statusCode= response.code()
                    callback(apiResponseRoom, statusCode, null)

                } else {
                    // La respuesta no es exitosa, obtén el código de estado y el cuerpo del error
                    val errorCode = response.code()
                    val errorBody = response.errorBody()?.string()

                    //aca debes ver que el correo y numero son repetidos
                    // Envia el código de estado y el cuerpo del error al callback
                    callback(null, errorCode, errorBody)
                }
            }

            override fun onFailure(call: Call<ApiResponseRoom>, t: Throwable) {
                var errorCode = -1 // Valor por defecto en caso de que no se pueda obtener el código de estado HTTP

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