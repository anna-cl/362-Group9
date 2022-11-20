package ca.cmpt362.projects.weCareApp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import ca.cmpt362.projects.weCareApp.R

class MainMenuActivity:AppCompatActivity() {
    private lateinit var surveyBtn:Button
    private lateinit var findClinicBtn:Button
    private lateinit var diaryBtn:Button
    private lateinit var meditationBtn:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        surveyBtn = findViewById(R.id.SurveyBtnId)
        findClinicBtn = findViewById(R.id.findClinicBtnId)
        diaryBtn = findViewById(R.id.diaryBtnId)
        meditationBtn = findViewById(R.id.meditationBtnId)

        surveyBtn.setOnClickListener {
            startActivity(Intent(this, SurveyActivity::class.java))
        }
        findClinicBtn.setOnClickListener {
            startActivity(Intent(this, FindAClinicActivity::class.java))
        }
        diaryBtn.setOnClickListener {
            startActivity(Intent(this, DiaryActivity::class.java))
        }
        meditationBtn.setOnClickListener {
            startActivity(Intent(this, MeditationLandActivity::class.java))
        }
    }
}