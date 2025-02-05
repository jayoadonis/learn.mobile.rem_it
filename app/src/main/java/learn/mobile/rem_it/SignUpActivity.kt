package learn.mobile.rem_it

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import com.google.android.material.textfield.TextInputLayout


import learn.mobile.rem_it.databinding.ActivitySignUpBinding;
import learn.mobile.rem_it.models.UserDataAccessObject

class SignUpActivity : AppCompatActivity() {

    private lateinit var signUpActivityBind: ActivitySignUpBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.signUpActivityBind = ActivitySignUpBinding.inflate(
            super.getLayoutInflater()
        );
        val userDAO = UserDataAccessObject(this);

        super.setContentView( this.signUpActivityBind.root );

        this.signUpActivityBind.lblSignIn.setOnClickListener {
            super.startActivity( Intent(this, MainActivity::class.java) );
            super.finish();
        }

        this.signUpActivityBind.btnSignUp.setOnClickListener {
            val txtEmail = this.signUpActivityBind.txtInputEditEmail.text.toString();
            val txtPassword = this.signUpActivityBind.txtInputEditPassword.text.toString();
            val txtConfirmPassword = this.signUpActivityBind.txtInputEditConfirmPassword.text.toString();
            val txtUserName = this.signUpActivityBind.txtInputEditUserName.text.toString();

            if( !txtPassword.equals(txtConfirmPassword) ) {
                Toast.makeText(this, "Both Inputted Passwords does not match!", Toast.LENGTH_LONG).show()
                return@setOnClickListener;
            }

            if( !txtEmail.isBlank() && !txtPassword.isBlank() ) {

                userDAO.insertUser( txtUserName, txtEmail, txtPassword );

//                this.signUpActivityBind.txtInputEditEmail.text?.clear();
//                this.signUpActivityBind.txtInputEditPassword.text?.clear();
//                this.signUpActivityBind.txtInputEditUserName.text?.clear();
//                this.signUpActivityBind.txtInputEditConfirmPassword.text?.clear();
                Toast.makeText(this, "Registration Succeeded!", Toast.LENGTH_SHORT).show()
                super.startActivity( Intent(this, MainActivity::class.java) );
                super.finish();
            }
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