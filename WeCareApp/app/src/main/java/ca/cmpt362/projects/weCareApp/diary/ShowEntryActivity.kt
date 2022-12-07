package ca.cmpt362.projects.weCareApp.diary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ca.cmpt362.projects.weCareApp.databinding.ActivityShowEntryBinding
import ca.cmpt362.projects.weCareApp.models.MyObject

class ShowEntryActivity : AppCompatActivity() {
    lateinit var binding: ActivityShowEntryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvName.text = MyObject.entry.name
        binding.tvNotes.text = MyObject.entry.entry
    }
}