package learn.mobile.rem_it

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import learn.mobile.rem_it.utils.SessionManager

import learn.mobile.rem_it.databinding.ActivityMainBinding;
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager;
    private lateinit var mainActivityBind: ActivityMainBinding;

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);

        this.mainActivityBind = ActivityMainBinding.inflate(super.getLayoutInflater());
//        super.setContentView(R.layout.activity_main);
        super.setContentView(this.mainActivityBind.root);

        this.sessionManager = SessionManager(this);

        val txtInputEditPassword: TextInputEditText
            = super.findViewById<TextInputEditText>(R.id.txt_input_edit_password);

        val btnLogIn: Button
            = this.mainActivityBind.btnSignIn;

        btnLogIn.setOnClickListener {
            if( txtInputEditPassword.text.toString().equals("123") ) {
                Toast.makeText(it?.context, "Login button clicked!!!", Toast.LENGTH_SHORT).show()
                println(String.format("%s", "Got it... But not best practice."))
            }
            else
                println(String.format("%s", "Better luck next time..."))
        }

//        btnLogIn.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(v: View?) {
//                Toast.makeText(v?.context, "Login button clicked!", Toast.LENGTH_SHORT).show()
//            }
//        });
    }
}