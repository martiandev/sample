package team.standalone.feature.userinfo.ui.selectbirthdate

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import team.standalone.core.common.extension.sendDestinationResult
import team.standalone.core.data.domain.model.param.BirthdateParam
import team.standalone.core.ui.util.NavKeys
import team.standalone.feature.userinfo.R

@AndroidEntryPoint
class SelectBirthdateDialog : DialogFragment(),
    DatePickerDialog.OnDateSetListener {
    private val args: SelectBirthdateDialogArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val year = args.birthdateParam?.year ?: 2000
        val month = args.birthdateParam?.month?.let { it - 1 } ?: 0
        val dayOfMonth = args.birthdateParam?.day ?: 1
        val dpDialog = DatePickerDialog(requireContext(), this, year, month, dayOfMonth)
        dpDialog.setButton(
            DialogInterface.BUTTON_NEUTRAL,
            getString(R.string.user_info_fragment_update_user_btn_update_clear_date_of_birth)
        ) { _, _ ->
            sendDestinationResult(NavKeys.BIRTHDATE_PARAM, null)
        }
        return dpDialog
    }

    override fun onDateSet(datePicker: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val birthdateParam = BirthdateParam(
            day = dayOfMonth,
            month = monthOfYear + 1,
            year = year
        )
        sendDestinationResult(NavKeys.BIRTHDATE_PARAM, birthdateParam)
    }
}