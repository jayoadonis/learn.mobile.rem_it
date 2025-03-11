package learn.mobile.rem_it.midterm.activity_i

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import learn.mobile.rem_it.R

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.midterm__activity_i__activity_maps)

        //REM: Obtain the SupportMapFragment and request notification when the map is ready.
        val mapFragment = this.supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.mMap = googleMap

        //REM: Specify a location (for example, Davao City, Philippines)
        val davaoCity = LatLng(7.207573, 125.395874)

        //REM: Add a marker at Davao City
        this.mMap.addMarker(MarkerOptions().position(davaoCity).title("Marker in Davao City"))

        //REM: Configure the camera position:
        //REM: Change tilt to 0f for a flat (2D) view, or > 0 for a more 3D perspective.
        val cameraPosition = CameraPosition.Builder()
            .target(davaoCity)   //REM: Sets the center of the map to Davao City.
            .zoom(15f)           //REM: Sets the zoom level (adjust as needed).
            .tilt(45f)           //REM: 45° tilt for a 3D perspective; use 0f for a 2D view.
            .build()

        //REM: Animate the camera to the chosen position.
        this.mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }
}
