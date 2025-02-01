package learn.mobile.rem_it

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.content.res.AppCompatResources
import com.google.android.material.textfield.TextInputLayout


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

//        val passwordInputLayout = this.signUpActivityBind.txtInputLayoutPassword;
//        val confirmPasswordInputLayout = this.signUpActivityBind.txtInputLayoutConfirmPassword;

//        passwordInputLayout.setEndIconOnClickListener {
//            val isVisible = passwordInputLayout.endIconDrawable?.constantState?.equals(
//                AppCompatResources.getDrawable(
//                    this.applicationContext,
//                    com.google.android.material.R.drawable.ic_call_answer
//                )?.constantState
//            ) ?: false
//
//            if (isVisible) {
//                confirmPasswordInputLayout.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
//            } else {
//                confirmPasswordInputLayout.endIconMode = TextInputLayout.END_ICON_NONE
//            }
//        }

    }
}