package learn.mobile.rem_it

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


import learn.mobile.rem_it.databinding.ActivitySignUpBinding;

class SignUpActivity : AppCompatActivity() {

    private lateinit var signUpActivityBind: ActivitySignUpBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.signUpActivityBind = ActivitySignUpBinding.inflate(
            super.getLayoutInflater()
        );

        super.setContentView( this.signUpActivityBind.root );

        this.signUpActivityBind.lblSignIn.setOnClickListener {
            super.startActivity( Intent(this, MainActivity::class.java) );
            super.finish();
        }
    }
}