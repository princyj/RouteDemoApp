package au.com.princyj.routedemoapp

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class NotificationsDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return AlertDialog.Builder(requireActivity())
            .setTitle("Notifications Dialog")
            .setMessage("Hello")
            .setPositiveButton("OK", null)
            .create()
    }

}
