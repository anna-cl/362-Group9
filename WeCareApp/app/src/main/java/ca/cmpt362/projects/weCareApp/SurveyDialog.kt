package ca.cmpt362.projects.weCareApp

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import ca.cmpt362.projects.weCareApp.diary.DiaryActivity

class SurveyDialog:DialogFragment(), DialogInterface.OnClickListener {
    companion object{
        const val DIALOG_KEY = "DIALOG"
        const val UNSTABLE_HEALTH = 1
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        lateinit var dialog: Dialog
        val dialogID = arguments?.getInt(DIALOG_KEY)
        var choice = arrayOf("MEDITATE FOR 10 MINUTES","FIND A CLINIC","WRITE IN YOUR DIARY")
        val builder = AlertDialog.Builder(requireActivity())
        if (dialogID == UNSTABLE_HEALTH){
            val view = requireActivity().layoutInflater.inflate(
                R.layout.custom_dialog,
                null
            )
            builder.setView(view)
            builder.setTitle("Do you require support? Please choose a suitable option")
            builder.setNegativeButton("CANCEL", DialogInterface.OnClickListener { dialogInterface, i ->
                startActivity(Intent(requireActivity(), MainMenuActivity::class.java))
            })
            builder.setSingleChoiceItems(choice, 0){ dialogInterface, i: Int ->
                startActivity(Intent(requireActivity(), MeditationLandActivity::class.java))
            }
            builder.setSingleChoiceItems(choice, 1){ dialogInterface, i: Int ->
                startActivity(Intent(requireActivity(), FindClinicActivity::class.java))
            }
            builder.setSingleChoiceItems(choice, 2){ dialogInterface, i: Int ->
                startActivity(Intent(requireActivity(), DiaryActivity::class.java))
            }
            dialog = builder.create()
        }
        return dialog
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        TODO("Not yet implemented")
    }
}