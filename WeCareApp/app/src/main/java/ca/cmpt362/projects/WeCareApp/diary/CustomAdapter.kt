package ca.cmpt362.projects.WeCareApp.diary

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import ca.cmpt362.projects.weCareApp.R
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.custom_list.view.*
import kotlinx.android.synthetic.main.custom_update_entry.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CustomAdapter(private val context: Context , private val myList: ArrayList<Constants>) : RecyclerView.Adapter<CustomAdapter.EntryViewHolder>() {
    val database = Firebase.database
    val myRef = database.getReference().child("Entries")

     class EntryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val entryTitle = itemView.tv_titleEntry
        val entryTime = itemView.tv_timeEntry
        fun bind(entries:Constants){
            entryTitle.text = entries.title
            entryTime.text = entries.timestamp
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryViewHolder {
        return EntryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_list , parent,false))
    }

    override fun onBindViewHolder(holder: EntryViewHolder, position: Int) {
        val entry = myList[position]
        holder.bind(entry)
        holder.itemView.setOnClickListener{
            //val intent = Intent(context , ShowDiaryActivity::class.java)
            //intent.putExtra("title",entry.title)
            //intent.putExtra("entry",entry.entry)
            //context.startActivity(intent)
        }


        holder.itemView.setOnLongClickListener  { v ->
            showDialogDeleteAndUpdateEntries(entry)
            false
        }
    }

    override fun getItemCount(): Int {
       return myList.size
    }

    fun showDialogDeleteAndUpdateEntries(entries:Constants){
        val view = LayoutInflater.from(context).inflate(R.layout.custom_update_entry,null,false)

        val dialog = AlertDialog.Builder(context)
        dialog.setView(view)
        dialog.setTitle(R.string.delOrUpdate)
        val alert = dialog.create()
        alert.show()

        view.btn_delete.setOnClickListener {
            myRef.child(entries.id).removeValue()
            alert.dismiss()
        }

        view.btn_update.setOnClickListener {
            view.et_update_title.visibility = View.VISIBLE
            view.et_update_entry.visibility = View.VISIBLE
            val data_title = view.et_update_title.text.toString()
            val data_entry = view.et_update_entry.text.toString()

            if(data_title.isEmpty() && data_entry.isEmpty()){
                view.et_update_title.setText(entries.title)
                view.et_update_entry.setText(entries.entry)
            }

            if (data_title.isNotEmpty() && data_entry.isNotEmpty()) {
                val childRef = myRef.child(entries.id)
                val entriesAfterUpdate = Constants(entries.id , data_title , data_entry , getCurrentDate())
                childRef.setValue(entriesAfterUpdate)
                alert.dismiss()
            }else
                Toast.makeText(context, "Please Enter title & entry",Toast.LENGTH_LONG).show()

        }

    }

    // date
    fun getCurrentDate():String{
        val calender = Calendar.getInstance()
        val mdFormat = SimpleDateFormat("EEEE hh:mm a ")
        val strDate = mdFormat.format(calender.time)
        return strDate
    }

}