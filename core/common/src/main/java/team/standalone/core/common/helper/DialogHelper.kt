package team.standalone.core.common.helper

import android.content.Context
import android.content.DialogInterface
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object DialogHelper {

    fun showAlertDialog(
        context: Context, title: String,
        message: String, labelPositive: String, labelNegative: String,
        cancellable: Boolean,
        alertDialogInterface: AlertDialogInterface
    ) {
        MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(cancellable)
            .setPositiveButton(labelPositive) { dialog, _ ->
                alertDialogInterface.onPositive(dialog)
            }
            .setNegativeButton(labelNegative) { dialog, _ ->
                alertDialogInterface.onNegative(dialog)
            }.show()
    }

    interface AlertDialogInterface {
        fun onPositive(dialog: DialogInterface)
        fun onNegative(dialog: DialogInterface)
    }
}