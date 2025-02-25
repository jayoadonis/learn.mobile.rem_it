package learn.mobile.rem_it

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import learn.mobile.rem_it.databinding.ActivityDashboardBinding
import learn.mobile.rem_it.databinding.ActivityDashboardIBinding
import learn.mobile.rem_it.models.UserDataAccessObject
import learn.mobile.rem_it.utils.SessionManager

class DashboardActivityI: AppCompatActivity() {

    private lateinit var activityDashboardBind: ActivityDashboardIBinding;
    private lateinit var sessionManager: SessionManager;

    private lateinit var userDAO: UserDataAccessObject;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_dashboard_i)

        this.sessionManager = SessionManager(this);
        this.userDAO = UserDataAccessObject(this);
        this.activityDashboardBind = ActivityDashboardIBinding.inflate(super.getLayoutInflater());
        super.setContentView(this.activityDashboardBind.root);

        var txtTitle = this.activityDashboardBind.txtViewWelcome;
        var btnLogOut = this.activityDashboardBind.btnLogOut;
        var btnProfile = this.activityDashboardBind.btnProfile;
        var btnSetting  = this.activityDashboardBind.btnSetting;

        txtTitle.text = buildString {
            append(txtTitle.text.toString() + ", ")
            append(this@DashboardActivityI.sessionManager.getUserName())
        };


        btnLogOut.setOnClickListener {
            this.sessionManager.clearSession();
            super.startActivity(Intent(this, MainActivity::class.java));
            Toast.makeText(this, "Logging Out...", Toast.LENGTH_SHORT ).show()
            super.finish();
        }

        btnProfile.setOnClickListener {
            super.startActivity(Intent(this, ProfileActivity::class.java));
        }

        btnSetting.setOnClickListener {
            super.startActivity(Intent(this, SettingActivity::class.java));
        }

        this.activityDashboardBind.btnCalc.setOnClickListener {
            super.startActivity(
                Intent( this, SimpleCalcActivity::class.java )
            );
        }
    }

    public override fun onResume() {
        super.onResume()

        val USER = this.userDAO.getUserByEmail(this.sessionManager.getEmail());


        var txtTitle = this.activityDashboardBind.txtViewWelcome;

        "Welcome, ${USER?.userName?:"N/a"}".also { txtTitle.text = it }
    }
}