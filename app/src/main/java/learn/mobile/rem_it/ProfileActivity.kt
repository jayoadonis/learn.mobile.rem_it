package learn.mobile.rem_it

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.FocusFinder
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import learn.mobile.rem_it.databinding.ActivityProfileBinding
import learn.mobile.rem_it.models.UserDataAccessObject
import learn.mobile.rem_it.utils.SessionManager

class ProfileActivity : AppCompatActivity() {

    private lateinit var activityProfileBinding: ActivityProfileBinding
    private lateinit var userDAO: UserDataAccessObject;
    private lateinit var sessionManager: SessionManager;

    private lateinit var btnSave: Button;

    private lateinit var tiePassword: TextInputEditText;
    private lateinit var tieEmail: TextInputEditText;
    private lateinit var tieUserName: TextInputEditText;

    private lateinit var btnBack: Button;
    private lateinit var btnReset: Button;

    public companion object {
//        var canChange = false;
        private var isEmailValid = true;
        private var isUserNameValid = true;

//        public const val SAMPLE_CONST_VAL = 1;

        public fun isValidEmail(email: String?): Boolean {
            val emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"

            return email?.matches(emailPattern.toRegex())?:false;
        }
    }


    public override fun onResume(): Unit {
        super.onResume();
    }

    public override fun onDestroy(): Unit {
        super.onDestroy()
//        ProfileActivity.canChange = false;
//        ProfileActivity.isEmailValid = false;
//        ProfileActivity.isUserNameValid = false;
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("isEmailValid", ProfileActivity.isEmailValid)
        outState.putBoolean("isUserNameValid", ProfileActivity.isUserNameValid)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        ProfileActivity.isEmailValid = savedInstanceState.getBoolean("isEmailValid")
        ProfileActivity.isUserNameValid = savedInstanceState.getBoolean("isUserNameValid")
    }

    public override fun onCreate(saveInstanceState: Bundle?): Unit {
        super.onCreate(saveInstanceState)

        this.activityProfileBinding = ActivityProfileBinding.inflate( super.getLayoutInflater() );
        super.setContentView( this.activityProfileBinding.root );

        this.userDAO = UserDataAccessObject(this);
        this.sessionManager = SessionManager(this);

        val TV_USER_NAME: TextView = this.activityProfileBinding.txtViewUserName
        val TV_EMAIL: TextView = this.activityProfileBinding.txtViewEmail

        this.btnBack = this.activityProfileBinding.btnBack;

        this.btnSave = this.activityProfileBinding.btnSave

        this.btnReset = this.activityProfileBinding.btnReset;

        super.runOnUiThread {
            this.btnSave.isEnabled = false;
            this.btnReset.isEnabled = false;
        }

        this.tieUserName = this.activityProfileBinding.txtInputEditUserName
        this.tieEmail = this.activityProfileBinding.txtInputEditEmail
        this.tiePassword = this.activityProfileBinding.txtInputEditPassword

        val USER_NAME = this.sessionManager.getUserName()?:"N/a";
        val EMAIL = this.sessionManager.getEmail()?:"N/a";

        TV_USER_NAME.text = USER_NAME;
        TV_EMAIL.text = EMAIL;

        this.tieUserName.setText(USER_NAME);
        this.tieEmail.setText(EMAIL);
        this.tiePassword.setText("")

        val USER = this.userDAO.getUserByEmail(EMAIL)
        USER?.isLoggedIn = this.sessionManager.isLoggedIn();

        this.addTextWatcher( this.tieUserName );
        this.addTextWatcher( this.tieEmail );
        this.addTextWatcher( this.tiePassword );

        btnSave.setOnClickListener {

            if( USER != null && USER.isLoggedIn) {

                val NUM_OF_AFFECTED: Int = this@ProfileActivity.userDAO.updateUserById(
                    this@ProfileActivity.sessionManager.getId(),
                    this@ProfileActivity.tieUserName.text.toString().trim(),
                    this@ProfileActivity.tieEmail.text.toString().trim(),
                    this@ProfileActivity.tiePassword.text.toString().ifEmpty {
                        USER.password
                    }
                );

                if( NUM_OF_AFFECTED > 0 ) {

                    this@ProfileActivity.btnSave.post {
                        this@ProfileActivity.btnSave.isEnabled = false
                        this@ProfileActivity.btnReset.isEnabled = false
                    }

                    Toast.makeText(this@ProfileActivity, "Successfully updated", Toast.LENGTH_LONG)
                        .show();

                    //REM: [TODO, OPTIMIZED]
                    val NEW_USER = this@ProfileActivity.userDAO.getUserByEmail(
                        this@ProfileActivity.tieEmail.text.toString().trim()
                    );

                    NEW_USER?.isLoggedIn = this@ProfileActivity.sessionManager.isLoggedIn();

                    this@ProfileActivity.sessionManager.saveUserSession(NEW_USER);

                    TV_USER_NAME.text = this@ProfileActivity.sessionManager.getUserName();
                    TV_EMAIL.text = this@ProfileActivity.sessionManager.getEmail();

                    tieUserName.setText(sessionManager.getUserName())
                    tieEmail.setText(sessionManager.getEmail())
                    tiePassword.setText("")

                    return@setOnClickListener;
                }
            }
            Toast.makeText(this@ProfileActivity, "Error: Something went wrong.", Toast.LENGTH_LONG).show();
        }


        this.btnBack.setOnClickListener {
            super.startActivity( Intent(this, DashboardActivityI::class.java) );
        }

        this.btnReset.setOnClickListener {

            tieUserName.setText(sessionManager.getUserName())
            tieEmail.setText(sessionManager.getEmail())
            tiePassword.setText("")


            btnSave.isEnabled = false;
            btnReset.isEnabled = false;
        }

    }

    private fun addTextWatcher(editText: TextInputEditText ) {

        editText.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {

                val TXT_INPUT = s.toString().trim();

                when( editText ) {
                    tieUserName -> {
                        ProfileActivity.isUserNameValid = !TXT_INPUT.isBlank();
                    }
                    tieEmail -> {
                        ProfileActivity.isEmailValid = (!TXT_INPUT.isBlank()
                                && ProfileActivity.isValidEmail( TXT_INPUT ));
//                        this@ProfileActivity.btnReset.isEnabled = true;
//                        this@ProfileActivity.btnSave.isEnabled = ProfileActivity.isUserNameValid
//                                && ProfileActivity.isEmailValid;
                    }
                    tiePassword -> {
//                        this@ProfileActivity.btnReset.isEnabled = true;
//                        this@ProfileActivity.btnSave.isEnabled  = ProfileActivity.isUserNameValid
//                                && ProfileActivity.isEmailValid;
                    }
                    else -> {
//                        this@ProfileActivity.btnReset.isEnabled = false;
//                        this@ProfileActivity.btnSave.isEnabled = false;
                    }
                }

                this@ProfileActivity.btnReset.isEnabled = true;
                this@ProfileActivity.btnSave.isEnabled = ProfileActivity.isUserNameValid
                        && ProfileActivity.isEmailValid;

//                btnSave.isEnabled = !txt.isBlank() &&
//                        ( txt != cmpTo || editText == tiePassword );
//
//                btnReset.isEnabled = s.toString() != cmpTo || editText == tiePassword ;
            }
        })
    }


}