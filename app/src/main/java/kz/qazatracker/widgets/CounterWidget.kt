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
private const val DEFAULT_MAX_COUNTER_VALUE = Int.MAX_VALUE

class CounterWidget(
    context: Context,
    attributeSet: AttributeSet
) : LinearLayout(context, attributeSet) {

    private var counterWidgetCallback: CounterWidgetCallback? = null
    private var minusButton: ImageButton
    private var plusButton: ImageButton
    private var counterEditText: EditText
    private lateinit var textWatcher: TextWatcher

    private var counter: Int = 0
    private var minCounterValue: Int = 0
    private var maxCounterValue: Int = 1000000

    init {
        View.inflate(context, R.layout.number_input_view, this)

        minusButton = findViewById(R.id.minus_button)
        plusButton = findViewById(R.id.plus_button)
        counterEditText = findViewById(R.id.counter_edit_text)
        initTextWatcher()

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
            setCounter(counter)
        }
        attributes.recycle()

        setupCounterEditText()
        setClickListeners()
    }

    override fun clearFocus() {
        counterEditText.clearFocus()
    }

    fun setCounterCallback(counterWidgetCallback: CounterWidgetCallback) {
        this.counterWidgetCallback = counterWidgetCallback
    }

    fun getCounter(): Int = counter

    fun setCounter(count: Int) {
        counter = count
        counterEditText.removeTextChangedListener(textWatcher)
        counterEditText.setText("$count")
        counterEditText.addTextChangedListener(textWatcher)
    }

    fun setMinCounterValue(value: Int) {
        minCounterValue = value
        updateCounterWidget()
    }

    fun setMaxCounterValue(value: Int) {
        maxCounterValue = value
        updateCounterWidget()
    }

    private fun setClickListeners() {
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

    private fun updateCounterWidget() {
        counterEditText.filters = arrayOf(InputFilterMinMax(minCounterValue, maxCounterValue))
    }

    private fun setupCounterEditText() {
        counterEditText.filters = arrayOf(InputFilterMinMax(minCounterValue, maxCounterValue))
        counterEditText.addTextChangedListener(textWatcher)
        counterEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                moveCursorToEnd()
            }
        }
    }

    private fun moveCursorToEnd() {
        counterEditText.setSelection(counterEditText.text.length)
    }

    private fun initTextWatcher() {
        textWatcher = object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                val value: String = s?.toString() ?: return

                val tempCounter = if (value.isEmpty()) 0 else value.toLong()
                when {
                    tempCounter > maxCounterValue -> {
                        setCounter(maxCounterValue)
                        moveCursorToEnd()
                    }
                    tempCounter < minCounterValue -> {
                        setCounter(minCounterValue)
                        moveCursorToEnd()
                    }
                    else -> {
                        setCounter(tempCounter.toInt())
                        moveCursorToEnd()
                    }
                }
                counterWidgetCallback?.onCounterChanged(counter)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        }
    }
}

interface CounterWidgetCallback {

    fun onCounterChanged(value: Int)
}