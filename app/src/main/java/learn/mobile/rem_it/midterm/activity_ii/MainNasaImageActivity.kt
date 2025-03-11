package learn.mobile.rem_it.midterm.activity_ii;

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import learn.mobile.rem_it.R
import learn.mobile.rem_it.midterm.activity_ii.models.ApodResponse
import learn.mobile.rem_it.midterm.activity_ii.models.NasaApiService
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainNasaImageActivity : AppCompatActivity() {
    private lateinit var nasaImage: ImageView
    private lateinit var titleText: TextView
    private lateinit var descText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.midterm__activity_ii__activity_main_nasa_image)

        nasaImage = findViewById(R.id.nasaImage)
        titleText = findViewById(R.id.titleText)
        descText = findViewById(R.id.descText)

        fetchApodData()
    }

    private fun fetchApodData() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.nasa.gov/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(NasaApiService::class.java)
        val apiKey = "Mp1rIK4UCLgBV5bV1dEmdLmfKkco445fLyQufAC6"
        val call = apiService.getApod(apiKey)

        call.enqueue(object : Callback<ApodResponse> {
            override fun onResponse(call: Call<ApodResponse>, response: Response<ApodResponse>) {
                if (response.isSuccessful) {
                    val apod = response.body()
                    apod?.let {
                        titleText.text = it.title
                        descText.text = it.explanation
                        if (it.media_type == "image") {
                            Picasso.get().load(it.url).into(nasaImage)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ApodResponse>, t: Throwable) {
                titleText.text = "Failed to load data."
            }
        })
    }
}
