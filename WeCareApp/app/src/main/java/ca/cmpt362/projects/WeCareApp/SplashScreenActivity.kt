package ca.cmpt362.projects.weCareApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView


//Citation:
//https://www.youtube.com/watch?v=Iajl3LEmqKo&ab_channel=CodingwithDev
//https://www.youtube.com/watch?v=JLIFqqnSNmg&ab_channel=CodingWithTea
//https://www.geeksforgeeks.org/how-to-create-a-splash-screen-in-android-using-kotlin/

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        playAnimation()

//        open Main Activity:
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 4000)
    }

    private fun playAnimation(){
        val topAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        val bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)

        val splashLogo: ImageView = findViewById(R.id.splash_logo)
        val splashAppName:TextView = findViewById(R.id.splash_app_name)
        val splashSlogan: TextView = findViewById(R.id.splash_slogan)

        splashLogo.animation = topAnimation
        splashAppName.animation = bottomAnimation
        splashSlogan.animation = bottomAnimation
    }


}