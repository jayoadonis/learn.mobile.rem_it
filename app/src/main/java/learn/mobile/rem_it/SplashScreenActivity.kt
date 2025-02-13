package learn.mobile.rem_it

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import learn.mobile.rem_it.utils.SessionManager

class SplashScreenActivity : AppCompatActivity() {

//    private lateinit var sessionManager: SessionManager;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        super.setContentView(R.layout.activity_splash_screen);

//        this.sessionManager = SessionManager(this);

        Handler(Looper.getMainLooper()).postDelayed({
            super.startActivity(Intent(this, MainActivity::class.java))
            super.finish()
        }, 2000)

    }
}