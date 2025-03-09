package learn.mobile.rem_it

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import learn.mobile.rem_it.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    private lateinit var activitySettingBinding: ActivitySettingBinding;

    private lateinit var btnBack: Button;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        this.activitySettingBinding = ActivitySettingBinding.inflate(
            super.getLayoutInflater()
        );
        super.setContentView(this.activitySettingBinding.root);

        this.btnBack = this.activitySettingBinding.btnSettingBack;

        this.btnBack.setOnClickListener{
//            super.startActivity(Intent(this, DashboardActivityI::class.java));
            super.finish();
        }
    }
}