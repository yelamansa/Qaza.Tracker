package kz.qazatracker.widgets

import android.app.DatePickerDialog
import android.content.Context
import android.text.format.DateUtils
import android.util.AttributeSet
import android.widget.DatePicker
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import kz.qazatracker.R
import org.joda.time.DateTime
import java.util.*

class DatePickerTextView(
    context: Context,
    attributeSet: AttributeSet
) : AppCompatTextView(context, attributeSet), DatePickerDialog.OnDateSetListener {

    private var dateTime: DateTime = DateTime()
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
        dateTime = DateTime("$year-${month+1}-$dayOfMonth")
        setDate()
        setTextColor(ContextCompat.getColor(context, R.color.black_gray_color))
    }

    fun getDateTime(): DateTime = dateTime

    fun setDate(date: DateTime) {
        dateDialog?.datePicker?.maxDate = date.millis
        dateTime = date
        setDate()
    }

    private fun setDate() {
        text = DateUtils.formatDateTime(
            context,
            dateTime.millis,
            DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR
        )
    }

    private fun initDialog() {
        dateDialog = DatePickerDialog(
            context,
            this,
            dateTime.year,
            dateTime.monthOfYear-1,
            dateTime.dayOfMonth
        ).apply {
            datePicker.maxDate = dateTime.millis
        }
    }
}