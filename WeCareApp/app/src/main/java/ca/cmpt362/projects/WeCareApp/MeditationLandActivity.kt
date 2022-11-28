package ca.cmpt362.projects.weCareApp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ca.cmpt362.projects.weCareApp.R
//import okhttp3.OkHttpClient
//import okhttp3.Request
//import okhttp3.RequestBody;
//import okhttp3.Response;

class MeditationLandActivity:AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meditation_land)
//        val client = OkHttpClient()
//
//        val request = Request.Builder()
//            .url("https://theaudiodb.p.rapidapi.com/searchalbum.php?s=meditation")
//            .get()
//            .addHeader("X-RapidAPI-Key", "e3d576ba6amshb5bcf4f80c7dea5p1a2472jsn851e7a615f66")
//            .addHeader("X-RapidAPI-Host", "theaudiodb.p.rapidapi.com")
//            .build()
//
//        val response = client.newCall(request).execute()
//
//        println("------------ $response")
//


    }
}