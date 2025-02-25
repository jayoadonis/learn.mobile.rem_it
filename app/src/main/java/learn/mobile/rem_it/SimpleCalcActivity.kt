package learn.mobile.rem_it

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import learn.mobile.rem_it.databinding.ActivitySimpleCalcBinding;

class SimpleCalcActivity : AppCompatActivity() {

    private lateinit var activitySimpleCalcBinding: ActivitySimpleCalcBinding;

    private lateinit var btnAdd: Button;
    private lateinit var btnSub: Button;
    private lateinit var btnDiv: Button;
    private lateinit var btnMult: Button;

    private lateinit var etI: EditText;
    private lateinit var etII: EditText;

    private lateinit var tvResult: TextView;

    public override fun onCreate(saveInstanceState: Bundle?): Unit {
        super.onCreate( saveInstanceState );

        this.activitySimpleCalcBinding = ActivitySimpleCalcBinding.inflate(super.getLayoutInflater());

        super.setContentView( this.activitySimpleCalcBinding.root );


        this.btnAdd = this.activitySimpleCalcBinding.btnAdd;
        this.btnSub = this.activitySimpleCalcBinding.btnSub;
        this.btnMult = this.activitySimpleCalcBinding.btnMult;
        this.btnDiv = this.activitySimpleCalcBinding.btnDiv;

        this.etI = this.activitySimpleCalcBinding.inputI;
        this.etII = this.activitySimpleCalcBinding.inputII;
        this.tvResult = this.activitySimpleCalcBinding.result;



        this.btnAdd.setOnClickListener {
            val x = this.etI.text.toString().trim().toDouble();
            val y = this.etII.text.toString().trim().toDouble();

            val r = x + y;

            this.tvResult.setText(r.toString());
        }

        this.btnSub.setOnClickListener {
            val x = this.etI.text.toString().trim().toDouble();
            val y = this.etII.text.toString().trim().toDouble();

            val r = x - y;

            this.tvResult.setText(r.toString());
        }

        this.btnDiv.setOnClickListener {
            val x = this.etI.text.toString().trim().toDouble();
            val y = this.etII.text.toString().trim().toDouble();

            var r = 0.0;
            var strR = "---";

            try {
                r = x / y;
                strR = r.toString();
            }
            catch( exception: Exception ) {
                strR = exception.localizedMessage?:"Error";
            }

            this.tvResult.setText(strR.toString());

        }


        this.btnMult.setOnClickListener {
            val x = this.etI.text.toString().trim().toDouble();
            val y = this.etII.text.toString().trim().toDouble();

            val r = x * y;

            this.tvResult.setText(r.toString());
        }


    }
}

