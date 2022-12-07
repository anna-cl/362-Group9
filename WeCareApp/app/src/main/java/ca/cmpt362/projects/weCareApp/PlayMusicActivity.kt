package ca.cmpt362.projects.weCareApp

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import java.lang.Exception

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

    lateinit var runnable: Runnable
    private var handler = Handler()


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

        val seekbar = findViewById<SeekBar>(R.id.seekBar)

        //initialize seek bar and run:
        seekbar.progress = 0
        if (mediaPlayer != null){
            seekbar.max = mediaPlayer!!.duration

            handler.postDelayed(object: Runnable{
                override fun run() {
                    try{
                        seekbar.progress = mediaPlayer!!.currentPosition
                        handler.postDelayed(this, 1000)
                    }catch (e: Exception){
                        seekbar.progress = 0
                    }
                }
            }, 0)
        }

        //running seek bar:
        seekbar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, position: Int, changed: Boolean) {
                if (changed){
                    mediaPlayer!!.seekTo(position)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()

        //stop music when leaving page:
        if(mediaPlayer == null) {
            mediaPlayer!!.stop()
        }

    }
}