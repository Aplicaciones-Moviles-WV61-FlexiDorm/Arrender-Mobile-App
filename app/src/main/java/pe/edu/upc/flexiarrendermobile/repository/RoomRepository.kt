package pe.edu.upc.flexiarrendermobile.repository

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.compose.runtime.MutableState
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toFile
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import pe.edu.upc.flexiarrendermobile.model.data.ApiResponse
import pe.edu.upc.flexiarrendermobile.model.data.ApiResponseRoom
import pe.edu.upc.flexiarrendermobile.model.data.ApiResponseRoomList
import pe.edu.upc.flexiarrendermobile.model.data.RegisterRoomRequestBody
import pe.edu.upc.flexiarrendermobile.model.remote.RoomService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream

class RoomRepository(
    private val roomService: RoomService,
    private val token: String
) {


    fun registerRoom(
        requestRegisterRoomBody: RegisterRoomRequestBody,
        selectedImageUri: MutableState<Uri?>,
        context: Context,
        callback: (ApiResponseRoom?, Int?, String?) -> Unit
    ) {
        println( "El token el el room repository es: $token")

        println( "La imagen seleccionada es: $selectedImageUri")

        val gson = Gson()
        val jsonRequest = gson.toJson(requestRegisterRoomBody)
        val requestBody = jsonRequest.toRequestBody("application/json".toMediaTypeOrNull())

        val imagePart = selectedImageUri.value?.let { uri ->
            val inputStream = context.contentResolver.openInputStream(uri)
            inputStream?.use { input ->
                val imageBytes = input.readBytes()
                val requestBody = imageBytes.toRequestBody("image/*".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("image", "image.jpg", requestBody)
            }
        }


        println( "El image part es: ${imagePart?.body?.contentType()}")

        println( "El request body es: $requestBody")




            val call = roomService.registerRoom("Bearer $token", requestBody, imagePart)

            call.enqueue(object : Callback<ApiResponseRoom> {
                override fun onResponse(
                    call: Call<ApiResponseRoom>,
                    response: Response<ApiResponseRoom>
                ) {
                    if (response.isSuccessful) {

                        val apiResponseRoom = response.body()
                        val statusCode = response.code()
                        println( "El api response room es: $apiResponseRoom")
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

    fun getRoomsById(
        arrenderId:Long,
        callback: (ApiResponseRoomList?, Int?, String?) -> Unit
    ) {
        val call=roomService.getRoomsByArrenderId("Bearer $token",arrenderId)

        call.enqueue(object : Callback<ApiResponseRoomList> {
            override fun onResponse(call: Call<ApiResponseRoomList>, response: Response<ApiResponseRoomList>) {
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

            override fun onFailure(call: Call<ApiResponseRoomList>, t: Throwable) {
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