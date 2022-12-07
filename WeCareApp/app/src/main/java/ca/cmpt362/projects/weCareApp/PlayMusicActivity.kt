package ca.cmpt362.projects.weCareApp

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView

class PlayMusicActivity : AppCompatActivity() {

    private val musicList = arrayOf(
        R.raw.epona_meditations0,
        R.raw.breath_meditation1,
        R.raw.noise2,
        R.raw.peace3,
        R.raw.energy4,
        R.raw.riopy5
    )
    private var mediaPlayer: MediaPlayer? = null
    private var musicPos = 0
    private lateinit var playBtn: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_music)


        val bundle: Bundle = intent.extras!!
        musicPos = bundle.getInt("MUSIC_POS")
        val imageID: Int = bundle.getInt("MEDI_IMG_ID")

        //set background image:
        val image: View = findViewById(R.id.play_music_layout)
        image.setBackgroundResource(imageID)

    }

    //Play music:
    fun playBtnClicked(view: View){
        if(mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, musicList[musicPos])
            mediaPlayer!!.start()

        }else if (!mediaPlayer!!.isPlaying){
            mediaPlayer!!.start()

            playBtn = findViewById<ImageButton>(R.id.playBtn)
            playBtn.setImageResource(R.drawable.play_circle_100)

        }else if(mediaPlayer!!.isPlaying){
            mediaPlayer!!.pause()
            val pauseBtn = findViewById<ImageButton>(R.id.playBtn)
            pauseBtn.setImageResource(R.drawable.pause_circle_100)
        }
    }


    override fun onDestroy() {
        super.onDestroy()

        //stop music when leaving page:
        mediaPlayer!!.stop()
    }
}