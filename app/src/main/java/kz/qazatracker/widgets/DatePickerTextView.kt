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
    private var dateDialog: Dialog? = null

    init {
        setDate()
        setOnClickListener {
            if (dateDialog?.isShowing == true) return@setOnClickListener

            dateDialog = DatePickerDialog(
                context,
                this,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH)
            ).apply {
                datePicker.maxDate = System.currentTimeMillis()
            }
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

    private fun setDate() {
        text = DateUtils.formatDateTime(
            context,
            dateAndTime.timeInMillis,
            DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR
        )
    }
}