package learn.mobile.rem_it

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import learn.mobile.rem_it.databinding.ActivityDashboardBinding;
import learn.mobile.rem_it.models.UserDataAccessObject
import learn.mobile.rem_it.utils.SessionManager

class DashboardActivity : AppCompatActivity() {

    private lateinit var activityDashboardBind: ActivityDashboardBinding;
    private lateinit var sessionManager: SessionManager;

    private lateinit var userDAO: UserDataAccessObject;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.userDAO = UserDataAccessObject(this);
        this.activityDashboardBind = ActivityDashboardBinding.inflate(super.getLayoutInflater());

        super.setContentView(this.activityDashboardBind.root);
//        setContentView(R.layout.activity_dashboard)

        this.sessionManager = SessionManager(this)

        val lblDashboard = this.activityDashboardBind.lblDashboardTitle.text.toString();

        this.activityDashboardBind.lblDashboardTitle.text = lblDashboard.plus( ": [${this.sessionManager.getUserName()}]")

        this.activityDashboardBind.btnDashboardLogOut.setOnClickListener{
            this.sessionManager.clearSession();
            super.startActivity( Intent(this, MainActivity::class.java) );
            super.finish();
        }
    }
}