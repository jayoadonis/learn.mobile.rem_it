package learn.mobile.rem_it

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import learn.mobile.rem_it.utils.SessionManager

import learn.mobile.rem_it.databinding.ActivityMainBinding;
import learn.mobile.rem_it.models.User

class MainActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager;
    private lateinit var activityMainBind: ActivityMainBinding;

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);

        this.activityMainBind = ActivityMainBinding.inflate(super.getLayoutInflater());
//        super.setContentView(R.layout.activity_main);
        super.setContentView(this.activityMainBind.root);

        this.sessionManager = SessionManager(this);

        if( this.sessionManager.isLoggedIn() ) {
            this.navigateToDashboard();
            super.finish();
        }

        val txtInputEditPassword: TextInputEditText
            = super.findViewById<TextInputEditText>(R.id.txt_input_edit_password);

        val btnLogIn: Button
            = this.activityMainBind.btnSignIn;
        val lblSignUp: TextView = this.activityMainBind.lblSignUp;

        lblSignUp.setOnClickListener {
            this.navigateToSignUp();
//            super.finish();
        }

        btnLogIn.setOnClickListener {
            val email = this.activityMainBind.txtInputEditEmail.text.toString();
            val password = this.activityMainBind.txtInputEditPassword.text.toString();
            if( email.equals("admin@email.io") && password.equals("123") ) {
                Toast.makeText(it?.context, "Log In Successfully", Toast.LENGTH_SHORT).show()
                println(String.format("%s", "Got it... But not best practice."))

                this.sessionManager.saveUserSession(
                    User(null, email, true)
                );

                this.navigateToDashboard();
                super.finish();
            }
            else {
                println(String.format("%s", "Better luck next time..."))
                Toast.makeText(it?.context, "Unauthorized access is prohibited!", Toast.LENGTH_SHORT).show()
            }
        }

//        btnLogIn.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(v: View?) {
//                Toast.makeText(v?.context, "Login button clicked!", Toast.LENGTH_SHORT).show()
//            }
//        });
    }

    private fun navigateToDashboard(): Unit {
        val intentActivityDashboard = Intent(this, DashboardActivity::class.java);
        super.startActivity( intentActivityDashboard );
    }

    private fun navigateToSignUp(): Unit {
        super.startActivity( Intent( this, SignUpActivity::class.java) );
    }
}