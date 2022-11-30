package ca.cmpt362.projects.WeCareApp.diary

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ca.cmpt362.projects.weCareApp.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_diary.*
import kotlinx.android.synthetic.main.custom_add_entry.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DiaryActivity : AppCompatActivity() {
    private lateinit var myRef: DatabaseReference
    private var listEntries = ArrayList<Constants>()
    private val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)
        val database = Firebase.database
        myRef = database.getReference().child("Entries")

        addDataInList()

        fab_add_entry.setOnClickListener {
            showDialogAddEntries()
        }
    }

    fun showDialogAddEntries(){
        val view = layoutInflater.inflate(R.layout.custom_add_entry , null , false)

        val dialog = AlertDialog.Builder(this)
        dialog.setView(view)
        dialog.setTitle(R.string.addNewEntry)
        val alert = dialog.create()
        alert.show()

        view.btn_add.setOnClickListener {
            val title = view.et_title.text.toString()
            val entry = view.et_entry.text.toString()
            if (title.isNotEmpty() && entry.isNotEmpty()) {
                val id = myRef.push().key
                val myEntries = Constants(id!!, title, entry, getCurrentDate())
                myRef.child(id).setValue(myEntries)
                    .addOnSuccessListener {
                        // Write was successful!
                        Toast.makeText(this, "Write was successful!", Toast.LENGTH_LONG).show()
                        alert.dismiss()
                    }
                    .addOnFailureListener {
                        // Write failed
                    }

            } else
                Toast.makeText(this, "Please Enter title & entry", Toast.LENGTH_LONG).show()
        }
    }

    fun getCurrentDate():String{
        val calender = Calendar.getInstance()
        val mdFormat = SimpleDateFormat("EEEE hh:mm a ")
        val strDate = mdFormat.format(calender.time)
        return strDate
    }

    fun addDataInList(){
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listEntries.clear()
                for (i in snapshot.children){
                    val entry = i.getValue(Constants::class.java)
                    listEntries.add(0, Constants(entry!!.id , entry!!.title , entry.entry , entry.timestamp))
                }
                val adapter = CustomAdapter(context , listEntries)
                rv_entries.layoutManager = LinearLayoutManager(context)
                rv_entries.adapter = adapter
                rv_entries.setHasFixedSize(true)
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })

    }
}
