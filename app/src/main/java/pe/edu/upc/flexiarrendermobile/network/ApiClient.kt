package pe.edu.upc.flexiarrendermobile.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    //Define the base URL
    const val BASE_URL="https://flexidormsapi-wfjf.onrender.com/api/v1/"

    //Create a function to get the Retrofit instance
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


}