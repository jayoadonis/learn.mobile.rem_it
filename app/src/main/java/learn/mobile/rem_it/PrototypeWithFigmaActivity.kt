package learn.mobile.rem_it

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PrototypeWithFigmaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learn__mobile__rem_it__prototype_with_figma);

        val btnBack = super.findViewById<Button>(R.id.btn_prototype_with_figma_back);

        btnBack.setOnClickListener {
            val intent = Intent(this, AccordionActivity::class.java)
            //REM: FLAG_ACTIVITY_CLEAR_TOP brings an existing instance to the front if present,
            //REM: and FLAG_ACTIVITY_SINGLE_TOP ensures that if the activity is already at the top,
            //REM: it won't be recreated.
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
        }

    }
}