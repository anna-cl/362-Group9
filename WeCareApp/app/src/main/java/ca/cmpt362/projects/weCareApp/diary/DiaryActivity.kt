package ca.cmpt362.projects.weCareApp.diary

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import ca.cmpt362.projects.weCareApp.adapters.RvAdapter
import ca.cmpt362.projects.weCareApp.R
import ca.cmpt362.projects.weCareApp.databinding.ActivityDiaryBinding
import ca.cmpt362.projects.weCareApp.databinding.ItemDialogPermissionBinding
import ca.cmpt362.projects.weCareApp.models.MyObject
import ca.cmpt362.projects.weCareApp.models.Diary_Entry
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*

class DiaryActivity:AppCompatActivity() {
    lateinit var binding: ActivityDiaryBinding
    lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var storage: FirebaseStorage
    lateinit var storageReference: StorageReference
    lateinit var adapter: RvAdapter
    lateinit var list: ArrayList<Diary_Entry>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseFirestore = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()
        storageReference = storage.getReference("notes")

        list = ArrayList()

        adapter = RvAdapter(list, object : RvAdapter.ClickItem {
            @SuppressLint("NotifyDataSetChanged")
            override fun itemClick(view: View, notes: Diary_Entry, position: Int) {
                val popupMenu = PopupMenu(binding.root.context, view)
                popupMenu.inflate(R.menu.my_menu)

                popupMenu.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.edit -> {
                            MyObject.entry = notes
                            MyObject.type = "Edit"
                            startActivity(Intent(this@DiaryActivity, AddEntryActivity::class.java))
                        }
                        R.id.delete -> {
                            val document = firebaseFirestore.collection("Notes").document(notes.id)
                            val alertDialog = AlertDialog.Builder(this@DiaryActivity).create()
                            val itemDialogPermissionBinding = ItemDialogPermissionBinding.inflate(layoutInflater)
                            document.delete()
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        this@DiaryActivity,
                                        "need to restart the program",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }.addOnFailureListener {
                                    Toast.makeText(
                                        this@DiaryActivity,
                                        "Failed",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                            itemDialogPermissionBinding.btnOk.setOnClickListener {
                                finish()
                                startActivity(Intent(this@DiaryActivity, DiaryIntroActivity::class.java))
                                Toast.makeText(
                                    this@DiaryActivity,
                                    "Delete",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            itemDialogPermissionBinding.btnCancel.setOnClickListener {
                                alertDialog.cancel()
                            }
                            alertDialog.setView(itemDialogPermissionBinding.root)
                            alertDialog.show()
                        }
                    }
                    true
                }
                popupMenu.show()
            }

            override fun cardClick(notes: Diary_Entry, position: Int) {
                MyObject.entry = notes
                startActivity(Intent(this@DiaryActivity, ShowEntryActivity::class.java))
            }
        })

        binding.rv.adapter = adapter

        binding.apply {
            firebaseFirestore.collection("Notes")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        task.result.forEach {
                            val notes = it.toObject(Diary_Entry::class.java)
                            list.add(notes)
                        }
                    }
                    adapter.notifyDataSetChanged()
                }
            binding.floating.setOnClickListener {
                finish()
                MyObject.type = "Add"
                startActivity(Intent(this@DiaryActivity, AddEntryActivity::class.java))
            }
        }
    }
}