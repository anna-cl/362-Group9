package ca.cmpt362.projects.weCareApp

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var resetWithEmail:EditText
    private lateinit var forgotPassTV: Button
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener { super.onBackPressed() }
        setStatusBarWhite(this@ForgotPasswordActivity)

        firebaseAuth = FirebaseAuth.getInstance()
        resetWithEmail = findViewById(R.id.et_email)
        forgotPassTV = findViewById(R.id.button_send)
        forgotPassTV.setOnClickListener{
            var getResetMail = resetWithEmail.text.toString()
            firebaseAuth.sendPasswordResetEmail(getResetMail).addOnSuccessListener {
                // notify listener if reset link has been sent to their mailbox
                Toast.makeText(this, "Reset link has been sent to your email", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                // notify listener if system has detected some error and display exception message
                // possible errors: email entered is invalid, email entered was not account email (not used for signup in the app)
                exception: java.lang.Exception -> Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()
            }

        }
    }
    fun setStatusBarWhite(activity: AppCompatActivity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            activity.window.statusBarColor = Color.WHITE
        }
    }
}