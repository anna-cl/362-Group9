package ca.cmpt362.projects.weCareApp

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import ca.cmpt362.projects.weCareApp.SurveyDialog

class SurveyActivity:AppCompatActivity() {
    // getting the variables
    private lateinit var submitBtn:Button
    private var totalScore:Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)

        submitBtn = findViewById(R.id.btnSubmit)
        submitBtn.setOnClickListener {
            if (totalScore >= 5.0 && totalScore <= 10.0){
                // patient's mental health is relatively stable
                println("value of: $totalScore")
                startActivity(Intent(this, InGoodHealth::class.java))
            }else if (totalScore < 5.0 && totalScore > 0.0){
                // patient needs support
                val dialog = SurveyDialog()
                val bundle = Bundle()
                bundle.putInt(SurveyDialog.DIALOG_KEY, SurveyDialog.UNSTABLE_HEALTH)
                dialog.arguments = bundle
                dialog.show(supportFragmentManager, "unstable health")
            }
            else if (totalScore == 0.0){
                // the user did not choose any options
                println("value of: $totalScore")
                var builder = AlertDialog.Builder(this)
                builder.setCancelable(true)
                builder.setMessage("Please complete the Survey!")
                builder.setPositiveButton("OK", null)
                // creating and showing the dialog for when user has not taken survey
                var dialog = builder.create()
                dialog.show()
            }
        }
    }
    // when any of the options for question1 are clicked we store the score associated
    fun onClickRadioButton1 (view: View){
        if (view is RadioButton){
            val btn1:RadioButton = findViewById(R.id.btn1)
            val btn2:RadioButton = findViewById(R.id.btn2)
            val btn3:RadioButton = findViewById(R.id.btn3)
            val btn4:RadioButton = findViewById(R.id.btn4)
            val btn5:RadioButton = findViewById(R.id.btn5)
            if (btn1.isChecked){
                totalScore += 0.125   // poor
            }
            if (btn2.isChecked){
                totalScore += 0.25   // fair
            }
            if (btn3.isChecked){
                totalScore += 0.5   // good
            }
            if (btn4.isChecked){
                totalScore += 1.0    // very good
            }
            if (btn5.isChecked){
                totalScore += 2.0    // excellent
            }
        }
    }
    // when any of the options for question2 are clicked we store the score associated
    fun onClickRadioButton2(view: View){
        if (view is RadioButton){
            val btn6:RadioButton = findViewById(R.id.btn6)
            val btn7:RadioButton = findViewById(R.id.btn7)
            val btn8:RadioButton = findViewById(R.id.btn8)
            if (btn6.isChecked){
                totalScore += 0.5   // yes
            }
            if (btn7.isChecked){
                totalScore += 2.0   // no
            }
            if (btn8.isChecked){
                totalScore += 1.5   // not sure
            }
        }
    }
    // when any of the options for question3 are clicked we store the score associated
    fun onClickRadioButton3 (view: View){
        if (view is RadioButton){
            val btn9:RadioButton = findViewById(R.id.btn9)
            val btn10:RadioButton = findViewById(R.id.btn10)
            val btn11:RadioButton = findViewById(R.id.btn11)
            val btn12:RadioButton = findViewById(R.id.btn12)
            if (btn9.isChecked){
                totalScore += 0.125      // very often
            }
            if (btn10.isChecked){
                totalScore += 0.5        // somewhat often
            }
            if (btn11.isChecked){
                totalScore += 1.0        // not so often
            }
            if (btn12.isChecked){
                totalScore += 2.0        // not at all
            }
        }
    }
    // when any of the options for question4 are clicked we store the score associated
    fun onClickRadioButton4 (view: View){
        if (view is RadioButton){
            val btn13:RadioButton = findViewById(R.id.btn13)
            val btn14:RadioButton = findViewById(R.id.btn14)
            val btn15:RadioButton = findViewById(R.id.btn15)
            val btn16:RadioButton = findViewById(R.id.btn16)
            if (btn13.isChecked){
                totalScore += 0.125     // very often
            }
            if (btn14.isChecked){
                totalScore += 0.5       // somewhat often
            }
            if (btn15.isChecked){
                totalScore += 1.0       // not so often
            }
            if (btn16.isChecked){
                totalScore += 2.0       // not at all
            }
        }
    }
    // when any of the options for question5 are clicked we store the score associated
    fun onClickRadioButton5 (view: View){
        if (view is RadioButton){
            val btn17:RadioButton = findViewById(R.id.btn17)
            val btn18:RadioButton = findViewById(R.id.btn18)
            val btn19:RadioButton = findViewById(R.id.btn19)
            val btn20:RadioButton = findViewById(R.id.btn20)
            val btn21:RadioButton = findViewById(R.id.btn21)
            if (btn17.isChecked){
                totalScore += 0.0    // never
            }
            if (btn18.isChecked){
                totalScore += 0.5    // once in a while
            }
            if (btn19.isChecked){
                totalScore += 1.0    // about half the time
            }
            if (btn20.isChecked){
                totalScore += 1.5    // most of the time
            }
            if (btn21.isChecked){
                totalScore += 2.0    // always
            }
        }
    }
}