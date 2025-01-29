package learn.mobile.rem_it

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import learn.mobile.rem_it.databinding.ActivityDashboardBinding;
import learn.mobile.rem_it.utils.SessionManager

class DashboardActivity : AppCompatActivity() {

    private lateinit var activityDashboardBind: ActivityDashboardBinding;
    private lateinit var sessionManager: SessionManager;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.activityDashboardBind = ActivityDashboardBinding.inflate(super.getLayoutInflater());
        super.setContentView(this.activityDashboardBind.root);
//        setContentView(R.layout.activity_dashboard)

        this.sessionManager = SessionManager(this)

        this.activityDashboardBind.btnDashboardLogOut.setOnClickListener{
            this.sessionManager.clearSession();
            super.startActivity( Intent(this, MainActivity::class.java) );
            super.finish();
        }
    }
}