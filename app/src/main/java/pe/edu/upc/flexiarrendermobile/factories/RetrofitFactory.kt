package pe.edu.upc.flexiarrendermobile.factories

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import pe.edu.upc.flexiarrendermobile.network.ApiClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitFactory {

    companion object {
        private var retrofit: Retrofit? = null
        fun getRetrofit(token:String): Retrofit {
            println( "Token en retrofit: $token")
            if (retrofit == null) {
                val client = OkHttpClient.Builder()
                    //.addInterceptor(AuthInterceptor(token))
                    .connectTimeout(30, TimeUnit.SECONDS) // Tiempo de espera para establecer la conexión
                    .readTimeout(30, TimeUnit.SECONDS) // Tiempo de espera para leer datos del servidor
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .build()
                //Este client no se adjunta tovavia a retrofit

                retrofit = Retrofit.Builder()
                    .baseUrl(ApiClient.BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit as Retrofit
        }
    }
}

class AuthInterceptor(private val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(request)
    }
}




