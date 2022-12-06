package ca.cmpt362.projects.weCareApp.diary

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import ca.cmpt362.projects.weCareApp.R
import ca.cmpt362.projects.weCareApp.databinding.ActivityDiaryIntroBinding
import ca.cmpt362.projects.weCareApp.diary.DiaryActivity
import kotlinx.coroutines.Runnable

class DiaryIntroActivity : AppCompatActivity() {
    lateinit var binding: ActivityDiaryIntroBinding
    lateinit var handler: Handler
    var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiaryIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handler = Handler(mainLooper)

        binding.apply {

            handler.postDelayed(
                runnable,800
            )
            handler.postDelayed(
                runnable,1600
            )
            handler.postDelayed(
                runnable,2400
            )
            handler.postDelayed(runnable,
            3200)
        }
    }

    private val runnable = object : Runnable {
        override fun run() {
            if (count == 0) {
                binding.linie1.startAnimation(AnimationUtils.loadAnimation(this@DiaryIntroActivity,
                    R.anim.alpha
                )) ; ++count
            }
            else if (count == 1) {
                binding.linie1.visibility = View.VISIBLE
                binding.linie2.startAnimation(AnimationUtils.loadAnimation(this@DiaryIntroActivity,
                    R.anim.alpha
                )) ; ++count
            }
            else if(count == 2){
                binding.linie2.visibility = View.VISIBLE
                binding.linie3.startAnimation(AnimationUtils.loadAnimation(this@DiaryIntroActivity,
                    R.anim.alpha
                )) ; ++count
            }
            else{
                binding.linie3.visibility = View.VISIBLE
                finish()
                startActivity(Intent(this@DiaryIntroActivity, DiaryActivity::class.java))
            }
        }
    }
}