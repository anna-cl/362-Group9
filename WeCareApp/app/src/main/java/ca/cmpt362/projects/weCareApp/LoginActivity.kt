package ca.cmpt362.projects.weCareApp

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.media.Image
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.Nullable
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat.startActivity
import com.facebook.*
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {

    companion object{
        private const val RC_SIGN_IN = 120
    }
    /*private var callbackManager: CallbackManager? = null
    private lateinit var facebookBtn: LoginButton*/
    private lateinit var googleBtn:ImageView
    private lateinit var googleSignInClient: GoogleSignInClient

    private lateinit var accountEmail:EditText
    private lateinit var accountPassword:EditText
    private lateinit var auth:FirebaseAuth
    private lateinit var buttonSignIn:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setStatusBarTransparent(this@LoginActivity)
        // get an instance of the FirebaseAuth
        auth = FirebaseAuth.getInstance()
        googleBtn = findViewById(R.id.google_Btn_SignIn)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        googleBtn.setOnClickListener {
            signInGoogle()
        }

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

    private fun signInGoogle(){
        val intent = googleSignInClient.signInIntent
        launcher.launch(intent)
    }
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result->
        if (result.resultCode == Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResults(task)
        }

    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener{
            if (it.isSuccessful){
                startActivity(Intent(this, MainMenuActivity::class.java))
            }else{
                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account: GoogleSignInAccount? = task.result
            if (account != null) {
                updateUI(account)
            } else {
                Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun onClick(view: View) {
        if (view.id == R.id.button_signup) {
            startActivity(Intent(this@LoginActivity, SignupActivity::class.java))
        } else if (view.id == R.id.button_forgot_password) {
            startActivity(Intent(this@LoginActivity, ForgotPasswordActivity::class.java))
        }
    }
}
