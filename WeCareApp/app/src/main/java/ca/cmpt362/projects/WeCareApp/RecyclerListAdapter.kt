package ca.cmpt362.projects.weCareApp
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ca.cmpt362.projects.weCareApp.R

class RecyclerListAdapter(private val musicList: List<ItemsViewModel>) : RecyclerView.Adapter<RecyclerListAdapter.MusicViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        // inflates the music_item.xml view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.music_item, parent, false)

        return MusicViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {

        val ItemsViewModel = musicList[position]

        // sets the image to the imageview from our itemHolder class
        holder.imageView.setImageResource(ItemsViewModel.image)

        // sets the text to the textview from our itemHolder class
        holder.textView.text = ItemsViewModel.text

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return musicList.size
    }

    // Holds the views for adding it to image and text
    class MusicViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.music_icon)
        val textView: TextView = itemView.findViewById(R.id.musicNameTV)
    }
}



