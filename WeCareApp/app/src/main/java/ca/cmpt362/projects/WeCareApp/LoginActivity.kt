package ca.cmpt362.projects.weCareApp

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var accountEmail:EditText
    private lateinit var accountPassword:EditText
    private lateinit var auth:FirebaseAuth
    private lateinit var buttonSignIn:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setStatusBarTransparent(this@LoginActivity)

        // getting variables' instances
        accountEmail = findViewById(R.id.et_login_email)
        accountPassword = findViewById(R.id.et_login_password)
        auth = FirebaseAuth.getInstance()
        buttonSignIn = findViewById(R.id.button_signin)
        // if user has existing account, direct them to the MainMenu
        buttonSignIn.setOnClickListener {
            var getEmail = accountEmail.text.toString()  // get the email entered
            var getPass = accountPassword.text.toString() // get the password entered

            if (TextUtils.isEmpty(getEmail)){
                accountEmail.setError("Email required!")  // if email field is empty, show error
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(getPass)){
                accountPassword.setError("Password required!") // if password field is empty, show error
                return@setOnClickListener
            }
            if (getPass.length < 6){
                accountPassword.setError("Try again - that is not your current password") // if password too short - it's invalid
                return@setOnClickListener
            }
            // sign user into their account
            auth.signInWithEmailAndPassword(getEmail, getPass).addOnCompleteListener(this){task->
                if (task.isSuccessful){
                    Toast.makeText(this, "SignIn Successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainMenuActivity::class.java))
                }else{
                    Toast.makeText(baseContext, task.exception?.message,
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setStatusBarTransparent(activity: AppCompatActivity){
        //Make Status bar transparent
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
        //Make status bar icons color dark
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            activity.window.statusBarColor = Color.WHITE
        }
    }

    fun onClick(view: View) {
        if (view.id == R.id.button_signup){
            startActivity(Intent(this@LoginActivity, SignupActivity::class.java))
        }
        else if(view.id == R.id.button_forgot_password){
            startActivity(Intent(this@LoginActivity, ForgotPasswordActivity::class.java))
        }
    }
}