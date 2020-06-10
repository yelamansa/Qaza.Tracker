package kz.qazatracker.widgets

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.text.format.DateUtils
import android.util.AttributeSet
import android.widget.DatePicker
import androidx.appcompat.widget.AppCompatTextView
import java.util.*

class DatePickerTextView(
    context: Context,
    attributeSet: AttributeSet
) : AppCompatTextView(context, attributeSet), DatePickerDialog.OnDateSetListener {

    private val dateAndTime: Calendar = Calendar.getInstance()
    private var dateDialog: DatePickerDialog? = null

    init {
        setDate()
        initDialog()

        setOnClickListener {
            if (dateDialog?.isShowing == true) return@setOnClickListener

            dateDialog?.show()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        dateAndTime[Calendar.YEAR] = year
        dateAndTime[Calendar.MONTH] = month
        dateAndTime[Calendar.DAY_OF_MONTH] = dayOfMonth
        setDate()
    }

    fun getTimeInMillis(): Long = dateAndTime.timeInMillis

    fun setDateInMillis(timeInMillis: Long) {
        dateDialog?.datePicker?.maxDate = timeInMillis
        dateAndTime.timeInMillis = timeInMillis
        setDate()
    }

    private fun setDate() {
        text = DateUtils.formatDateTime(
            context,
            dateAndTime.timeInMillis,
            DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR
        )
    }

    private fun initDialog() {
        dateDialog = DatePickerDialog(
            context,
            this,
            dateAndTime.get(Calendar.YEAR),
            dateAndTime.get(Calendar.MONTH),
            dateAndTime.get(Calendar.DAY_OF_MONTH)
        ).apply {
            datePicker.maxDate = dateAndTime.timeInMillis
        }
    }
}