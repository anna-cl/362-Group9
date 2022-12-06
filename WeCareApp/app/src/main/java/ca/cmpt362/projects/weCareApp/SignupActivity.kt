package ca.cmpt362.projects.weCareApp

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore


class SignupActivity : AppCompatActivity() {
    /*private lateinit var gso: GoogleSignInOptions
    private lateinit var gsc: GoogleSignInClient
    private lateinit var googleSignUp:ImageView*/

    // to register user, we need the following variables first:
    private lateinit var email:EditText
    private lateinit var fName:EditText
    private lateinit var phone:EditText
    private lateinit var password:EditText
    private lateinit var confirmPassword:EditText
    private lateinit var firebaseAuth:FirebaseAuth
    private lateinit var fireStore:FirebaseFirestore
    private lateinit var userID:String
    private lateinit var buttonSignUp:Button
    private val EMAIL = "email"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener { super.onBackPressed() }
        setStatusBarWhite(this@SignupActivity)

        /*gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        gsc = GoogleSignIn.getClient(this, gso)
        googleSignUp = findViewById(R.id.google_btn_SignUp)*/

        // getting variables' instances
        email = findViewById(R.id.et_email)
        fName = findViewById(R.id.et_fullname)
        phone = findViewById(R.id.et_phone)
        password = findViewById(R.id.et_password)
        confirmPassword = findViewById(R.id.et_confirm_password)
        firebaseAuth = FirebaseAuth.getInstance()
        fireStore = FirebaseFirestore.getInstance()
        buttonSignUp = findViewById(R.id.button_signin)

        if (firebaseAuth.currentUser != null){   // check if user is already logged in
            startActivity(Intent(this, MainMenuActivity::class.java)) // direct to MainMenu if yes
            finish()
        }
        // if user is not logged in,
        buttonSignUp.setOnClickListener {
            var getEmail = email.text.toString()  // get the email entered
            var getPass = password.text.toString() // get the password entered
            var get_confirm_pass = confirmPassword.text.toString() // get final password for the account
            var getFullName = fName.text.toString()  // get the full name entered
            var getPhoneNum = phone.text.toString() // get the phone number

            if (TextUtils.isEmpty(getEmail)){
                email.setError("Email required!")  // if email field is empty, show error
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(getPass)){
                password.setError("Password required!") // if password field is empty, show error
                return@setOnClickListener
            }
            if (getPass.length < 6){
                password.setError("Not a valid password!") // if password too short (less than 6 characters)
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(get_confirm_pass)){  // if password isn't confirmed, show error
                confirmPassword.setError("Confirm password!")
                return@setOnClickListener
            }
            if (getPass != get_confirm_pass){   // if confirmed password is not same as entered password, show error
                confirmPassword.setError("Passwords do not match!")
                return@setOnClickListener
            }

            // register user account into Firebase
            // https://firebase.google.com/docs/auth/android/password-auth#kotlin+ktx_3
            firebaseAuth.createUserWithEmailAndPassword(getEmail, getPass)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = firebaseAuth.currentUser
                        // visualization: "Users" is a collection of docs and each userid is a doc in that collection
                        userID = firebaseAuth.currentUser!!.uid // we use an instance of the firestore db
                        // to get currently registered/loggedin userId
                        var docRef: DocumentReference = fireStore.collection("users").document(userID)
                        var userHashMap = HashMap<String, Any>() // we will use a HashMap to store user data in our db
                        // specifying the values we want to store for each user
                        userHashMap.put("userName", getFullName)   // the first parameter in each put is the attribute for the value we want to
                        // have in db
                        userHashMap.put("email", getEmail)
                        userHashMap.put("phoneNumber",getPhoneNum)
                        userHashMap.put("password", getPass)

                        docRef.set(userHashMap).addOnSuccessListener {
                                void:Void? ->
                            Toast.makeText(this, "User profile created!", Toast.LENGTH_SHORT).show()
                            // user creation successful only: if user is authenticated (we then allow them to write the data to the db, else not)
                            // change to db rules for above purpose: allow read, write: if request.auth != null
                        }.addOnFailureListener{
                                exception: java.lang.Exception -> Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()
                        }
                        // create account for user and direct to MainMenu Activity
                        startActivity(Intent(this@SignupActivity, MainMenuActivity::class.java))
                        // updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, task.exception?.message,
                            Toast.LENGTH_SHORT).show()
                        // updateUI(null)
                    }
                }
        }
    }
    private fun setStatusBarWhite(activity: AppCompatActivity){
        //Make status bar icons color dark
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            activity.window.statusBarColor = Color.WHITE
        }
    }
}