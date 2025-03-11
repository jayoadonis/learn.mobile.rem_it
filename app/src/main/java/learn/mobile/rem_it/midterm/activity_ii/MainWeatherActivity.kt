package learn.mobile.rem_it.midterm.activity_ii;

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import learn.mobile.rem_it.R
import learn.mobile.rem_it.midterm.activity_ii.models.OpenWeatherMapService
import learn.mobile.rem_it.midterm.activity_ii.models.WeatherResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainWeatherActivity : AppCompatActivity() {

    private lateinit var textViewWeather: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.midterm__activity_ii__activity_main_weather)

        textViewWeather = findViewById(R.id.textViewWeather)

        //REM: Build the Retrofit instance
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        //REM: Create an instance of the API service
        val service = retrofit.create(OpenWeatherMapService::class.java)

        //REM: Coordinates for Davao City, Philippines
        val lat = 7.207573
        val lon = 125.395874
        val apiKey = "THE_API_KEY"

        //REM: Make the API call
        service.getWeather(lat, lon, apiKey).enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    val weatherResponse = response.body()
                    //REM: Retrieve temperature and description from the response
                    val temp = weatherResponse?.main?.temp ?: "N/A"
                    val description = weatherResponse?.weather?.get(0)?.description ?: "No description"
                    textViewWeather.text = "Temperature: $temp°C\nDescription: $description"
                } else {
                    textViewWeather.text = "Error: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                textViewWeather.text = "Failure: ${t.message}"
            }
        })
    }
}
