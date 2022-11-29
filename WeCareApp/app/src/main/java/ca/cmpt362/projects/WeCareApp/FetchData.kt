package ca.cmpt362.projects.WeCareApp


import android.os.AsyncTask
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.net.URL

class FetchData: AsyncTask<Any, String, String>() {
    lateinit var googleNearByPlacesData: String
    lateinit var googleMap: GoogleMap
    lateinit var url: String

    @Deprecated("Deprecated in Java")
    override fun onPostExecute(s: String){
        try {
            val jsonObject:JSONObject = JSONObject(s)
            val jsonArray: JSONArray = jsonObject.getJSONArray("results")


            for (i in 0 until jsonArray.length()) {
                val jsonObject1 = jsonArray.getJSONObject(i)
                val getLocation = jsonObject1.getJSONObject("geometry").getJSONObject("location")
                val lat = getLocation.getString("lat")
                val lng = getLocation.getString("lng")

                val getName:JSONObject = jsonArray.getJSONObject(i)
                val name:String = getName.getString("name")
                val latLng:LatLng = LatLng(lat.toDouble(), lng.toDouble())
                val markerOptions: MarkerOptions = MarkerOptions()

                markerOptions.title(name)
                markerOptions.position(latLng)
                googleMap.addMarker(markerOptions)
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun doInBackground(vararg objects: Any?): String {
        try {
            if(objects[0] is GoogleMap)
                googleMap = objects[0] as GoogleMap
            if(objects[1] is String)
                url = objects[1] as String

            val downloadUrl = DownloadUrl()
            googleNearByPlacesData = downloadUrl.retrieveUrl(url)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return googleNearByPlacesData
    }
}