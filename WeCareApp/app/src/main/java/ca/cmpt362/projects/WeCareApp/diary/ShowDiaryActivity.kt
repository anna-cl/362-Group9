package ca.cmpt362.projects.WeCareApp.diary

import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import ca.cmpt362.projects.weCareApp.R
import kotlinx.android.synthetic.main.activity_show_entry.*

class ShowDiaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_entry)

        val animation2 = AnimationUtils.loadAnimation(this,R.anim.zoomin)
        tv_show_entry.startAnimation(animation2)

        val title = intent.getStringExtra("title")
        val entry = intent.getStringExtra("entry")

        tv_show_title.text = title
        tv_show_entry.text = entry
    }
}