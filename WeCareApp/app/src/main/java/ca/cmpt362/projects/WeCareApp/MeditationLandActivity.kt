//citation:
//https://www.geeksforgeeks.org/android-recyclerview-in-kotlin/

package ca.cmpt362.projects.weCareApp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.cmpt362.projects.weCareApp.RecyclerListAdapter
import ca.cmpt362.projects.weCareApp.ItemsViewModel

class MeditationLandActivity:AppCompatActivity() {
    private val musicArrayList: ArrayList<ItemsViewModel> = arrayListOf<ItemsViewModel>()
    private lateinit var recyclerview: RecyclerView
    lateinit var musicTitleList: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meditation_land)


        musicTitleList = arrayOf(
            "Epona Meditation",
            "Breath Meditation",
            "Relaxing Radiance Noise",
            "Relaxing Radiance Peace",
            "Relaxing Radiance Energy",
            "Riopy Meditation"
        )

        recyclerview = findViewById<RecyclerView>(R.id.music_recyclerview) // getting the recyclerview by its id
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.setHasFixedSize(true)

        getMusicList()
    }

    private fun getMusicList(){

        for (i in musicTitleList.indices){
            val eachMusic = ItemsViewModel(R.drawable.music_icon, musicTitleList[i])
            musicArrayList.add(eachMusic)
        }

        recyclerview.adapter = RecyclerListAdapter(musicArrayList)
    }
}