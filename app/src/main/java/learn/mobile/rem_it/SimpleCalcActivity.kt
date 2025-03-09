package learn.mobile.rem_it

import android.content.Intent
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
    private lateinit var btnClear: Button;
    private lateinit var btnBack: Button;

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
        this.btnClear = this.activitySimpleCalcBinding.btnClear;
        this.btnBack = this.activitySimpleCalcBinding.btnSimpleCalculatorBack;

        this.etI = this.activitySimpleCalcBinding.inputI;
        this.etII = this.activitySimpleCalcBinding.inputII;
        this.tvResult = this.activitySimpleCalcBinding.result;


        var strR = "---";

        this.tvResult.setText(strR);

        this.btnAdd.setOnClickListener {
            strR = "---";
            strR = try {
                val x = this.etI.text.toString().trim().toDouble();
                val y = this.etII.text.toString().trim().toDouble();

                val r = x + y;

                r.toString();
            } catch( exception: Exception ) {
                ("Error: " /*+ exception.localizedMessage*/);
            }

            this.tvResult.setText(strR);
        }

        this.btnSub.setOnClickListener {
            strR = "---";
            strR = try {
                val x = this.etI.text.toString().trim().toDouble();
                val y = this.etII.text.toString().trim().toDouble();

                val r = x - y;

                r.toString()
            } catch( exception: Exception ) {
                ("Error: " /*+ exception.localizedMessage*/);
            }
            this.tvResult.setText(strR);
        }

        this.btnDiv.setOnClickListener {
            strR = "---";
            strR = try {

                val x = this.etI.text.toString().trim().toDouble();
                val y = this.etII.text.toString().trim().toDouble();

                var r = 0.0;

                r = x / y;

                r.toString()
            } catch( exception: Exception ) {
                ("Error: " /*+ exception.localizedMessage*/);
            }

            this.tvResult.setText(strR);

        }


        this.btnMult.setOnClickListener {
            strR = "---";
            strR = try {
            val x = this.etI.text.toString().trim().toDouble();
            val y = this.etII.text.toString().trim().toDouble();

            val r = x * y;

                r.toString()
            } catch( exception: Exception ) {
                ("Error: " /*+ exception.localizedMessage*/);
            }

            this.tvResult.setText(strR);
        }


        this.btnClear.setOnClickListener{

            this@SimpleCalcActivity.tvResult.setText("---");
            this@SimpleCalcActivity.etI.setText("");
            this@SimpleCalcActivity.etII.setText("");
        };


        this.btnBack.setOnClickListener{
//            val intent = Intent( this, AccordionActivity::class.java )
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
//            super.startActivity(intent);
            super@SimpleCalcActivity.finish();
        };


    }
}

