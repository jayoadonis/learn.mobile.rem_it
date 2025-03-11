package learn.mobile.rem_it.midterm.activity_i

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import learn.mobile.rem_it.R
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class MapsIActivity : AppCompatActivity() {

    private lateinit var map: MapView
    private val REQUEST_PERMISSIONS_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //REM: Important: set the user agent for osmdroid to avoid getting blocked
        Configuration.getInstance().load(applicationContext, getSharedPreferences("osmdroid", MODE_PRIVATE))
        Configuration.getInstance().userAgentValue = packageName

        setContentView(R.layout.midterm__activity_i__activity_maps_i)

        //REM: Request necessary permissions at runtime
        requestPermissionsIfNecessary(
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )

        //REM: Initialize the MapView and enable multi-touch controls
        map = findViewById(R.id.map_i)
        map.setMultiTouchControls(true)

        //REM: Set default zoom and center location (Davao City, Philippines)
        val davaoCity = GeoPoint(7.207573, 125.395874)
        map.controller.setZoom(15.0)
        map.controller.setCenter(davaoCity)

        //REM: Add a marker to the map
        val marker = Marker(map).apply {
            position = davaoCity
            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            title = "Davao City"
        }
        map.overlays.add(marker)
    }

    //REM: Helper function to request necessary permissions
    private fun requestPermissionsIfNecessary(permissions: Array<String>) {
        val permissionsToRequest = permissions.filter {
            ActivityCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }
        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toTypedArray(),
                REQUEST_PERMISSIONS_REQUEST_CODE
            )
        }
    }

    override fun onResume() {
        super.onResume()
        map.onResume() //REM: needed for osmdroid
    }

    override fun onPause() {
        super.onPause()
        map.onPause() //REM: needed for osmdroid
    }
}
