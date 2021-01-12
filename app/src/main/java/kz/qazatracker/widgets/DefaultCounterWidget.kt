package kz.qazatracker.widgets

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import kz.qazatracker.R

private const val DEFAULT_COUNTER_VALUE = -1
private const val DEFAULT_MIN_COUNTER_VALUE = 0
private const val DEFAULT_MAX_COUNTER_VALUE = 10000000

class DefaultCounterWidget(
    context: Context,
    attributeSet: AttributeSet
) : LinearLayout(context, attributeSet) {

    private var minusButton: ImageButton
    private var plusButton: ImageButton
    private var counterEditText: EditText

    private var counter: Int = 0
    private var minCounterValue: Int = 0
    private var maxCounterValue: Int = 12

    init {
        View.inflate(context, R.layout.number_input_view, this)

        minusButton = findViewById(R.id.minus_button)
        plusButton = findViewById(R.id.plus_button)
        counterEditText = findViewById(R.id.counter_edit_text)

        val attributes = context.obtainStyledAttributes(
            attributeSet,
            R.styleable.DefaultCounterWidget
        )
        counter = attributes.getInt(R.styleable.DefaultCounterWidget_counter, DEFAULT_COUNTER_VALUE)
        minCounterValue = attributes.getInt(
            R.styleable.DefaultCounterWidget_min_counter_value,
            DEFAULT_MIN_COUNTER_VALUE
        )
        maxCounterValue = attributes.getInt(
            R.styleable.DefaultCounterWidget_max_counter_value,
            DEFAULT_MAX_COUNTER_VALUE
        )
        if (counter != DEFAULT_COUNTER_VALUE) {
            counterEditText.setText("$counter")
        }
        attributes.recycle()

        setupCounterEditText()
        plusButton.setOnClickListener {
            if (counter >= maxCounterValue) return@setOnClickListener

            counterEditText.setText("${++counter}")
            counterEditText.setSelection(counterEditText.text.length)
        }
        minusButton.setOnClickListener {
            if (counter <= minCounterValue) return@setOnClickListener

            counterEditText.setText("${--counter}")
            counterEditText.setSelection(counterEditText.text.length)
        }
    }

    override fun clearFocus() {
        counterEditText.clearFocus()
    }

    fun getCounter(): Int = counter

    private fun setupCounterEditText() {
        counterEditText.filters = arrayOf(InputFilterMinMax(minCounterValue, maxCounterValue))
        counterEditText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                try {
                    val value: String = s?.toString() ?: return

                    counter = value.toInt()
                    if (counter > maxCounterValue) {
                        counter = maxCounterValue
                        counterEditText.setText("$counter")
                        moveCursorToEnd()
                    }
                    if (counter < minCounterValue) {
                        counter = minCounterValue
                        counterEditText.setText("$counter")
                        moveCursorToEnd()
                    }
                } catch (nfe: NumberFormatException) {
                    counter = 0
                    counterEditText.setText("$counter")
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
        counterEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                moveCursorToEnd()
            }
        }
    }

    private fun moveCursorToEnd() {
        counterEditText.setSelection(counterEditText.text.length)
    }
}