package ca.cmpt362.projects.weCareApp.diary

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import ca.cmpt362.projects.weCareApp.databinding.ActivityAddEntryBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import ca.cmpt362.projects.weCareApp.models.MyObject
import ca.cmpt362.projects.weCareApp.models.Diary_Entry

class AddEntryActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddEntryBinding
    lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var storage: FirebaseStorage
    lateinit var storageReference: StorageReference
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var references: DatabaseReference
    lateinit var list: ArrayList<String>

    @SuppressLint("NotifyDataSetChanged", "NewApi", "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseDatabase = FirebaseDatabase.getInstance()
        references = firebaseDatabase.getReference("uid")
        firebaseFirestore = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()
        storageReference = storage.getReference("imageNotes")
        list = ArrayList()
        list.add("#FF8A80")
        list.add("#FF9E80")
        list.add("#FFD180")
        list.add("#FFE57F")
        list.add("#FFFF8D")
        list.add("#CCFF90")
        list.add("#B9F6CA")
        list.add("#A7FFEB")
        list.add("#84FFFF")
        list.add("#80D8FF")
        list.add("#82B1FF")
        list.add("#B388FF")
        list.add("#EA80FC")
        list.add("#FF8A80")

        binding.apply {
            when (MyObject.type) {
                "Add" -> {
                    btnSave.setOnClickListener { view ->
                        val name = edtName.text.toString()
                        var notes = edtNotes.text.toString()
                        val current = LocalDateTime.now()
                        val formatter = DateTimeFormatter.ofPattern("MM-dd HH:mm")
                        val formatted = current.format(formatter)
                        if (name.isNotEmpty() && notes.isNotEmpty()) {
                            firebaseFirestore.collection("Notes")
                                .add(
                                    Diary_Entry(
                                        name = name,
                                        entry = notes,
                                        dataTime = formatted
                                    )
                                )
                                .addOnSuccessListener {
                                    references.child(it.id).setValue(it.id)
                                    val document =
                                        firebaseFirestore.collection("Notes").document(it.id)
                                    val map = mapOf<String, Any>("id" to it.id)
                                    document.update(map)
                                        .addOnSuccessListener { void ->
                                            Toast.makeText(
                                                this@AddEntryActivity,
                                                "Saved",
                                                Toast.LENGTH_SHORT
                                            )
                                            startActivity(
                                                Intent(
                                                    this@AddEntryActivity,
                                                    DiaryActivity::class.java
                                                )
                                            )
                                        }.addOnFailureListener {
                                            Toast.makeText(
                                                this@AddEntryActivity,
                                                "Failed",
                                                Toast.LENGTH_SHORT
                                            )
                                                .show()
                                        }
                                }
                        }
                        else {
                            Toast.makeText(
                                this@AddEntryActivity,
                                "EditTexts blank",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
                "Edit" -> {
                    binding.edtName.setText(MyObject.entry.name)
                    binding.edtNotes.setText(MyObject.entry.entry)
                    btnSave.setOnClickListener {

                        var document =
                            firebaseFirestore.collection("Notes")
                                .document(MyObject.entry.id)

                        val name = binding.edtName.text.toString()
                        var notes = binding.edtNotes.text.toString()
                        val current = LocalDateTime.now()
                        val formatter = DateTimeFormatter.ofPattern("MM-dd HH:mm")
                        val formatted = current.format(formatter)
                        if (name.isNotEmpty() && notes.isNotEmpty()) {
                            val map = HashMap<String, Any>()
                            map["name"] = name
                            map["notes"] = notes
                            document.update(map)
                                .addOnSuccessListener {
                                    Toast.makeText(this@AddEntryActivity, "Edit", Toast.LENGTH_SHORT)

                                    startActivity(
                                        Intent(
                                            this@AddEntryActivity,
                                            DiaryActivity::class.java
                                        )
                                    )
                                }
                                .addOnFailureListener {
                                    Toast.makeText(this@AddEntryActivity, "Failed", Toast.LENGTH_SHORT)
                                        .show()
                                }
                        } else {
                            Toast.makeText(
                                this@AddEntryActivity,
                                "EditTexts blank",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }


    private var getImageContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri ?: return@registerForActivityResult
            val hours = System.currentTimeMillis()

            val tesk = storageReference.child(hours.toString()).putFile(uri)

            tesk.addOnSuccessListener {

            }.addOnFailureListener {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        startActivity(Intent(this, DiaryActivity::class.java))
    }
}