package pe.edu.upc.flexiarrendermobile.factories

import pe.edu.upc.flexiarrendermobile.network.ApiClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {

    companion object {
        private var retrofit: Retrofit? = null
        fun getRetrofit(): Retrofit {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(ApiClient.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit as Retrofit
        }
    }
}