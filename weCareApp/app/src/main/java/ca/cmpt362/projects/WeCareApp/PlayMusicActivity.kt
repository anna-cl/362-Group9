package ca.cmpt362.projects.weCareApp

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_music)


        val bundle: Bundle = intent.extras!!
        musicPos = bundle.getInt("MUSIC_POS")
        val imageID: Int = bundle.getInt("MEDI_IMG_ID")
//        TODO: pass song name here !!

        //set background image:
        val image: View = findViewById(R.id.play_music_layout)
        image.setBackgroundResource(imageID)

        //Play music:


    }

    //Todo: music is NOT able to CREATE yet. will FIX later ---------
    fun playBtnClicked(view: View){
        if(mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(this, musicList[musicPos])
            println("=============== $musicPos")
        }else if (!mediaPlayer!!.isPlaying){
            mediaPlayer!!.start()

            println("-------play!")
            //TOdo: setimagesource pause button
        }else{
            println("-------pause!")
            mediaPlayer!!.pause()

            //TOdo: setimagesource play button
        }
    }
}