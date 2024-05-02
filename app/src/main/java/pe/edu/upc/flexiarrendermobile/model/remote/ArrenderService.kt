package pe.edu.upc.flexiarrendermobile.model.remote

import pe.edu.upc.flexiarrendermobile.model.data.ApiResponse
import pe.edu.upc.flexiarrendermobile.model.data.RequestSignInArrenderBody
import pe.edu.upc.flexiarrendermobile.model.data.RequestSignUpArrenderBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ArrenderService {

    @POST("auth/signUp/arrender")
    fun signUpArrender(
        @Body  requestSignUpArrenderBody: RequestSignUpArrenderBody
    ): Call<ApiResponse>

    @POST("auth/signIn")
    fun signInArrender(
        @Body  requestSignInArrenderBody: RequestSignInArrenderBody
    ): Call<ApiResponse>
}