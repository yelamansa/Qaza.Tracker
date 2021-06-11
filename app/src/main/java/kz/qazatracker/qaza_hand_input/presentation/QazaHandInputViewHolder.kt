package kz.qazatracker.qaza_hand_input.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.qazatracker.R
import kz.qazatracker.qaza_hand_input.data.QazaData
import kz.qazatracker.utils.hide
import kz.qazatracker.utils.show
import kz.qazatracker.widgets.CounterWidget
import kz.qazatracker.widgets.CounterWidgetCallback

class QazaInputViewHolder(
    qazaInputViewHolderCallback: QazaInputViewHolderCallback,
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    private val titleTextView: TextView = itemView.findViewById(R.id.layout_qaza_input_title)
    private val solatTitleTextView: TextView = itemView.findViewById(R.id.layout_qaza_input_sapar)
    private val counterWidget: CounterWidget = itemView.findViewById(R.id.layout_qaza_input_counter)
    private val saparCounterWidget: CounterWidget =
        itemView.findViewById(R.id.layout_qaza_input_sapar_counter)

    init {
        counterWidget.setCounterCallback(object : CounterWidgetCallback {
            override fun onCounterChanged(value: Int) {
                qazaInputViewHolderCallback.onSolatCounterChange(adapterPosition, value)
            }
        })
        saparCounterWidget.setCounterCallback(object : CounterWidgetCallback {
            override fun onCounterChanged(value: Int) {
                qazaInputViewHolderCallback.onSaparSolatCounterChange(adapterPosition, value)
            }
        })
    }

    fun onBind(qazaData: QazaData) {
        titleTextView.text = qazaData.solatName
        counterWidget.setCounter(qazaData.solatCount)
        counterWidget.setMinCounterValue(qazaData.minSolatCount)
        saparCounterWidget.setCounter(qazaData.saparSolatCount)
        saparCounterWidget.setMinCounterValue(qazaData.minSaparSolatCount)
        if (qazaData.hasSaparSolat) {
            saparCounterWidget.show()
            solatTitleTextView.show()
        } else {
            saparCounterWidget.hide()
            solatTitleTextView.hide()
        }
    }
}

interface QazaInputViewHolderCallback {

    fun onSolatCounterChange(position: Int, count: Int)

    fun onSaparSolatCounterChange(position: Int, count: Int)
}