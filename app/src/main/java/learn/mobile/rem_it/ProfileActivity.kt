package learn.mobile.rem_it

import android.content.Intent
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

    private lateinit var btnBack: Button;
    private lateinit var btnReset: Button;

    public override fun onCreate(saveInstanceState: Bundle?): Unit {
        super.onCreate(saveInstanceState)

        this.activityProfileBinding = ActivityProfileBinding.inflate( super.getLayoutInflater() );
        super.setContentView( this.activityProfileBinding.root );

        this.userDAO = UserDataAccessObject(this);
        this.sessionManager = SessionManager(this);

        var tvUserName: TextView = this.activityProfileBinding.txtViewUserName
        var tvEmail: TextView = this.activityProfileBinding.txtViewEmail

        this.btnBack = this.activityProfileBinding.btnBack;

        this.btnSave = this.activityProfileBinding.btnSave
        this.btnSave.isEnabled = false;

        this.btnReset = this.activityProfileBinding.btnReset;
        this.btnReset.isEnabled = false;

        var tieUserName = this.activityProfileBinding.txtInputEditUserName
        var tieEmail = this.activityProfileBinding.txtInputEditEmail
        this.tiePassword = this.activityProfileBinding.txtInputEditPassword

        val USER_NAME = this.sessionManager.getUserName()?:"N/a";
        val EMAIL = this.sessionManager.getEmail()?:"N/a";

        tvUserName.text = USER_NAME;
        tvEmail.text = EMAIL;

        tieUserName.setText(USER_NAME);
        tieEmail.setText(EMAIL);
        tiePassword.setText("")

        btnSave.setOnClickListener {
            this.userDAO.updateUserById(
                this.sessionManager.getId(),
                tieUserName.text.toString(),
                tieEmail.text.toString(),
                tiePassword.text.toString().ifEmpty {
                    userDAO.getUserByEmail(sessionManager.getEmail())?.password ?: ""
                }
            );

            val USER = this.userDAO.getUserByEmail(tieEmail.text.toString())
            USER?.isLoggedIn = this.sessionManager.isLoggedIn();

            if( USER != null ) {
                this.sessionManager.saveUserSession(USER);
                tvUserName.text = USER.userName;
                tvEmail.text = USER.email;

                Toast.makeText(this, "Successfully updated", Toast.LENGTH_LONG).show();

                btnSave.isEnabled = false;
                btnReset.isEnabled = false;
            }
        }

        this.addTextWatcher( tieUserName, sessionManager.getUserName() );
        this.addTextWatcher( tieEmail, sessionManager.getEmail() );
        this.addTextWatcher( tiePassword, sessionManager.getEmail() );

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

    private fun addTextWatcher(editText: TextInputEditText, cmpTo: String?) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {


                var txt = s.toString().trim();

                btnSave.isEnabled = !txt.isBlank() &&
                        ( txt != cmpTo );

                btnReset.isEnabled = s.toString() != cmpTo;
            }
        })
    }


}