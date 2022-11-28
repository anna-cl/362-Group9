//citation:
//https://www.geeksforgeeks.org/android-recyclerview-in-kotlin/

package ca.cmpt362.projects.weCareApp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.cmpt362.projects.WeCareApp.ItemsViewModel
import ca.cmpt362.projects.WeCareApp.RecyclerListAdapter

class MeditationLandActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meditation_land)


        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.music_recyclerview)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<ItemsViewModel>()

        // This loop will create 20 Views containing
        // the image with the count of view
        for (i in 1..8) {
            data.add(ItemsViewModel(R.drawable.splash_logo,  " "+ i))
        }

        // This will pass the ArrayList to our Adapter
        val adapter = RecyclerListAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter


    }
}