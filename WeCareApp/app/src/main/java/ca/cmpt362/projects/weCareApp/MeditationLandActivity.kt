package ca.cmpt362.projects.weCareApp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.cmpt362.projects.weCareApp.R
import kotlin.random.Random

class MeditationLandActivity:AppCompatActivity() {
    private val musicArrayList: ArrayList<ItemsViewModel> = arrayListOf<ItemsViewModel>()
    private lateinit var recyclerview: RecyclerView
    private var imgID: Int = 0
    lateinit var musicTitleList: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meditation_land)

        recyclerview = findViewById<RecyclerView>(R.id.music_recyclerview) // getting the recyclerview by its id
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.setHasFixedSize(true)

        setRandomImage()
        getMusicList()

    }

    fun setRandomImage(){
        var meditationImageList = arrayOf(
            R.drawable.cosmos1,
            R.drawable.lotus,
            R.drawable.cosmos2,
            R.drawable.cosmos3,
            R.drawable.zen1,
            R.drawable.zen_stackstones
        )

        var randomInt: Int = Random.nextInt(0,6)
        imgID = meditationImageList[randomInt]

        var imageView = ImageView(this)
        imageView = findViewById(R.id.meditationImage)
        imageView.setImageResource(imgID)

    }

    private fun getMusicList(){
//        TODO: add song minute here !!
        musicTitleList = arrayOf(
            "Epona Meditation",
            "Breath Meditation",
            "Relaxing Radiance Noise",
            "Relaxing Radiance Peace",
            "Relaxing Radiance Energy",
            "Riopy Meditation"
        )

        for (i in musicTitleList.indices){
            val eachMusic = ItemsViewModel(R.drawable.music_icon, musicTitleList[i])
            musicArrayList.add(eachMusic)
        }

        var adapter = RecyclerListAdapter(musicArrayList)
        recyclerview.adapter = adapter
        adapter.setOnItemClickListener(object: RecyclerListAdapter.onMusicItemClickListener{
            override fun onItemClicked(musicPosition: Int) {
                Toast.makeText(this@MeditationLandActivity, "You click $musicPosition!", Toast.LENGTH_SHORT).show()

//                // pass values and open activity:
//                val bundle= Bundle()
//                val intent = Intent(this@MeditationLandActivity, PlayMusicActivity::class.java)
//                bundle.putInt("MUSIC_POS", musicPosition)
//                bundle.putInt("MEDI_IMG_ID", imgID)
//                //        TODO: pass song name here !!
//                intent.putExtras(bundle)
//                startActivity(intent)

            }
        })
    }

}