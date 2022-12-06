package ca.cmpt362.projects.weCareApp

import android.os.Bundle
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import ca.cmpt362.projects.weCareApp.R

class SurveyActivity:AppCompatActivity() {
    private lateinit var radioGroup1: RadioGroup
    private lateinit var radioGroup2:RadioGroup
    private lateinit var radioGroup3:RadioGroup
    private lateinit var radioGroup4:RadioGroup
    private lateinit var radioGroup5:RadioGroup
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)

        // get all the radioGroup ids
        /*
        radioGroup1 = findViewById(R.id.radioGroup1)
        radioGroup2 = findViewById(R.id.radioGroup2)
        radioGroup3 = findViewById(R.id.radioGroup3)
        radioGroup4 = findViewById(R.id.radioGroup4)
        radioGroup5 = findViewById(R.id.radioGroup5)*/
    }
}