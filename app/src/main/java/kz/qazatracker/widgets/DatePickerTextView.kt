package kz.qazatracker.widgets

import android.app.DatePickerDialog
import android.content.Context
import android.text.format.DateUtils
import android.util.AttributeSet
import android.widget.DatePicker
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import kz.qazatracker.R
import java.util.*

class DatePickerTextView(
    context: Context,
    attributeSet: AttributeSet
) : AppCompatTextView(context, attributeSet), DatePickerDialog.OnDateSetListener {

    private val calendarDate: Calendar = Calendar.getInstance()
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
        calendarDate[Calendar.YEAR] = year
        calendarDate[Calendar.MONTH] = month
        calendarDate[Calendar.DAY_OF_MONTH] = dayOfMonth
        setDate()
        setTextColor(ContextCompat.getColor(context, R.color.black_gray_color))
    }

    fun getTimeInMillis(): Long = calendarDate.timeInMillis

    fun getCalendarDate(): Calendar = calendarDate

    fun setDateInMillis(timeInMillis: Long) {
        dateDialog?.datePicker?.maxDate = timeInMillis
        calendarDate.timeInMillis = timeInMillis
        setDate()
    }

    private fun setDate() {
        text = DateUtils.formatDateTime(
            context,
            calendarDate.timeInMillis,
            DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR
        )
    }

    private fun initDialog() {
        dateDialog = DatePickerDialog(
            context,
            this,
            calendarDate.get(Calendar.YEAR),
            calendarDate.get(Calendar.MONTH),
            calendarDate.get(Calendar.DAY_OF_MONTH)
        ).apply {
            datePicker.maxDate = calendarDate.timeInMillis
        }
    }
}