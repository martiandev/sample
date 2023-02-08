package team.standalone.fumiya.ui.auth.birthdate

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface.BUTTON_NEUTRAL
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import team.standalone.core.common.extension.sendDestinationResult
import team.standalone.core.data.domain.model.param.BirthdateParam
import team.standalone.core.ui.util.NavKeys
import team.standalone.fumiya.R

@AndroidEntryPoint
class SelectBirthdateDialog : DialogFragment(),
    DatePickerDialog.OnDateSetListener {
    private val args: SelectBirthdateDialogArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val year = args.birthdateParam?.year ?: 2000
        val month = args.birthdateParam?.month ?: 0
        val dayOfMonth = args.birthdateParam?.day ?: 1
        val dpDialog = DatePickerDialog(
            requireContext(),
            this,
            year,
            month,
            dayOfMonth
        ).apply { datePicker.touchables[0].performClick() }
        dpDialog.setButton(BUTTON_NEUTRAL, getString(R.string.fragment_sign_up_input_btn_clear_date_of_birth)) { _, _ ->
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