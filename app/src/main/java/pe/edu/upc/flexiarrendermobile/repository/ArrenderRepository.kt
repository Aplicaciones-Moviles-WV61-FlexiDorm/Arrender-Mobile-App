package pe.edu.upc.flexiarrendermobile.repository

import android.util.Log
import pe.edu.upc.flexiarrendermobile.model.data.ApiResponse
import pe.edu.upc.flexiarrendermobile.model.data.Arrender
import pe.edu.upc.flexiarrendermobile.model.data.RequestSignInArrenderBody
import pe.edu.upc.flexiarrendermobile.model.data.RequestSignUpArrenderBody
import pe.edu.upc.flexiarrendermobile.model.local.ArrenderDao
import pe.edu.upc.flexiarrendermobile.model.local.ArrenderEntity
import pe.edu.upc.flexiarrendermobile.model.remote.ArrenderService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class ArrenderRepository(
    private val arrenderService:ArrenderService,
    private val arrenderDao: ArrenderDao
) {

    fun signUpArrender(requestSignUpArrenderBody: RequestSignUpArrenderBody, callback: (ApiResponse?, Int?, String?) -> Unit) {

        println("El email es: " + requestSignUpArrenderBody.email)

        val call = arrenderService.signUpArrender(requestSignUpArrenderBody)

        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    // La respuesta es exitosa, maneja la ApiResponse normalmente
                    val apiResponse = response.body()
                    val statusCode= response.code()
                    callback(apiResponse, statusCode, null)
                } else {
                    // La respuesta no es exitosa, obtén el código de estado y el cuerpo del error
                    val errorCode = response.code()
                    val errorBody = response.errorBody()?.string()

                    //aca debes ver que el correo y numero son repetidos
                    // Envia el código de estado y el cuerpo del error al callback
                    callback(null, errorCode, errorBody)
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {

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

    fun signInArrender(requestSignInArrenderBody: RequestSignInArrenderBody,callback:(ApiResponse?, Int?, String?)->Unit) {
        //imprimir en consola el requestSignInArrender

        val call = arrenderService.signInArrender(requestSignInArrenderBody)

        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {

                if (response.isSuccessful) {
                    // La respuesta es exitosa, maneja la ApiResponse normalmente
                    val apiResponse = response.body()
                    val statusCode= response.code()
                    callback(apiResponse, statusCode, null)


                }else {
                    // La respuesta no es exitosa, obtén el código de estado y el cuerpo del error
                    val errorCode = response.code()
                    val errorBody = response.errorBody()?.string()

                    //aca debes ver que el correo y numero son repetidos
                    // Envia el código de estado y el cuerpo del error al callback
                    callback(null, errorCode, errorBody)
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {

                t.message?.let {
                    Log.e("ArrenderRepository", it)

                }
            }
        })
    }



    fun insertArrenderDataLocal(arrender: Arrender){
        arrenderDao.insert(
            ArrenderEntity(
                arrender.userId.toString(),
                arrender.username,
                arrender.firstname,
                arrender.lastname,
                arrender.phoneNumber,
                arrender.email,
                arrender.address,
                arrender.birthDate,
                arrender.profilePicture,
                arrender.gender,
                arrender.token,
                arrender.verifier
               )
        )
    }

    fun deleteArrenderDataLocal(id: String ){
        arrenderDao.deleteById(id)
    }

    fun deleteAllArrenderDataLocal(){
        arrenderDao.deleteAllArrendersLocal()
    }

    fun getArrenderDataLocal(id:String):ArrenderEntity?{
        return arrenderDao.getArrenderById(id)
    }

    fun getArrender():List<ArrenderEntity>{
        return arrenderDao.getArrender()
    }

    fun getToken(id:String):String{
        return arrenderDao.getToken(id)
    }
}