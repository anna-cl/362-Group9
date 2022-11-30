package ca.cmpt362.projects.weCareApp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ca.cmpt362.projects.weCareApp.R

class MeditationLandActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meditation_land)
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

                // pass values and open activity:
                val bundle= Bundle()
                val intent = Intent(this@MeditationLandActivity, PlayMusicActivity::class.java)
                bundle.putInt("MUSIC_POS", musicPosition)
                bundle.putInt("MEDI_IMG_ID", imgID)
                //        TODO: pass song name here !!
                intent.putExtras(bundle)
                startActivity(intent)
            }
        })
    }
    
}