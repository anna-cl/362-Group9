package ca.cmpt362.projects.weCareApp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import ca.cmpt362.projects.weCareApp.R
import com.google.firebase.auth.FirebaseAuth

class MainMenuActivity:AppCompatActivity() {
    private lateinit var signoutBtn:Button
    private lateinit var surveyBtn:Button
    // private lateinit var findClinicBtn:Button
    private lateinit var diaryBtn:Button
    private lateinit var meditationBtn:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        signoutBtn = findViewById(R.id.signOut)
        surveyBtn = findViewById(R.id.SurveyBtnId)
        // findClinicBtn = findViewById(R.id.findClinicBtnId)
        diaryBtn = findViewById(R.id.diaryBtnId)
        meditationBtn = findViewById(R.id.meditationBtnId)

        surveyBtn.setOnClickListener {
            startActivity(Intent(this, SurveyActivity::class.java))
        }
        /*
        findClinicBtn.setOnClickListener {
            startActivity(Intent(this, FindAClinicActivity::class.java))
        }*/
        diaryBtn.setOnClickListener {
            startActivity(Intent(this, DiaryActivity::class.java))
        }
        meditationBtn.setOnClickListener {
            startActivity(Intent(this, MeditationLandActivity::class.java))
        }
        signoutBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()  // signs out user
            Toast.makeText(this, "Signing out", Toast.LENGTH_SHORT).show() // notify that user is being signed out of their account
            startActivity(Intent(this, LoginActivity::class.java)) // brings user back to LoginPage
            finish()
        }
    }
}