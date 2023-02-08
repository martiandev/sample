package team.standalone.core.common.util

import android.content.Context
import android.content.DialogInterface
import androidx.annotation.StringRes
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object DialogUtil {

    fun showDialog(
        context: Context,
        @StringRes titleResId: Int,
        @StringRes messageResId: Int,
        @StringRes positiveLabelResId: Int,
        positiveBlock: (dialog: DialogInterface) -> Unit,
        cancellable: Boolean = true,
    ) {
        showDialog(
            context = context,
            title = context.getString(titleResId),
            message = context.getString(messageResId),
            positiveLabel = context.getString(positiveLabelResId),
            positiveBlock = positiveBlock,
            cancellable = cancellable
        )
    }

    fun showDialog(
        context: Context,
        title: String,
        message: String,
        positiveLabel: String,
        positiveBlock: (dialog: DialogInterface) -> Unit,
        cancellable: Boolean = true,
    ) {
        val builder = MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(cancellable)
            .setPositiveButton(positiveLabel) { dialog, _ ->
                positiveBlock(dialog)
            }
        builder.create().show()
    }

    fun showDialog(
        context: Context,
        @StringRes titleResId: Int,
        @StringRes messageResId: Int,
        @StringRes negativeLabelResId: Int,
        negativeBlock: (dialog: DialogInterface) -> Unit,
        @StringRes positiveLabelResId: Int,
        positiveBlock: (dialog: DialogInterface) -> Unit,
        cancellable: Boolean = true,
    ) {
        showDialog(
            context = context,
            title = context.getString(titleResId),
            message = context.getString(messageResId),
            negativeLabel = context.getString(negativeLabelResId),
            negativeBlock = negativeBlock,
            positiveLabel = context.getString(positiveLabelResId),
            positiveBlock = positiveBlock,
            cancellable = cancellable
        )
    }

    fun showDialog(
        context: Context,
        title: String,
        message: String,
        negativeLabel: String,
        negativeBlock: (dialog: DialogInterface) -> Unit,
        positiveLabel: String,
        positiveBlock: (dialog: DialogInterface) -> Unit,
        cancellable: Boolean = true,
    ) {
        val builder = MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(cancellable)
            .setNegativeButton(negativeLabel) { dialog, _ ->
                negativeBlock(dialog)
            }
            .setPositiveButton(positiveLabel) { dialog, _ ->
                positiveBlock(dialog)
            }
        builder.create().show()
    }

    fun showDialog(
        context: Context,
        @StringRes titleResId: Int,
        @StringRes messageResId: Int,
        @StringRes neutralLabelResId: Int,
        neutralBlock: (dialog: DialogInterface) -> Unit,
        @StringRes negativeLabelResId: Int,
        negativeBlock: (dialog: DialogInterface) -> Unit,
        @StringRes positiveLabelResId: Int,
        positiveBlock: (dialog: DialogInterface) -> Unit,
        cancellable: Boolean = true,
    ) {
        showDialog(
            context = context,
            title = context.getString(titleResId),
            message = context.getString(messageResId),
            neutralLabel = context.getString(neutralLabelResId),
            neutralBlock = neutralBlock,
            negativeLabel = context.getString(negativeLabelResId),
            negativeBlock = negativeBlock,
            positiveLabel = context.getString(positiveLabelResId),
            positiveBlock = positiveBlock,
            cancellable = cancellable
        )
    }

    fun showDialog(
        context: Context,
        title: String,
        message: String,
        neutralLabel: String,
        neutralBlock: (dialog: DialogInterface) -> Unit,
        negativeLabel: String,
        negativeBlock: (dialog: DialogInterface) -> Unit,
        positiveLabel: String,
        positiveBlock: (dialog: DialogInterface) -> Unit,
        cancellable: Boolean = true,
    ) {
        val builder = MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(cancellable)
            .setNeutralButton(neutralLabel) { dialog, _ ->
                neutralBlock(dialog)
            }
            .setNegativeButton(negativeLabel) { dialog, _ ->
                negativeBlock(dialog)
            }
            .setPositiveButton(positiveLabel) { dialog, _ ->
                positiveBlock(dialog)
            }
        builder.create().show()
    }
}