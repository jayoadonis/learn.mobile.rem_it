package learn.mobile.rem_it.midterm.activity_ii.models

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApiService {
    @GET("planetary/apod")
    fun getApod(
        @Query("api_key") apiKey: String
    ): Call<ApodResponse>
}
