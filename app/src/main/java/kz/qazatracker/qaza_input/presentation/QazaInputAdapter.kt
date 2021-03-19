package kz.qazatracker.qaza_input.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.qazatracker.R
import kz.qazatracker.qaza_input.data.QazaData

class QazaInputAdapter : RecyclerView.Adapter<QazaInputViewHolder>(), QazaInputViewHolderCallback {

    private val qazaInputList: MutableList<QazaData> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QazaInputViewHolder = QazaInputViewHolder(
        itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_layout_qaza_input,
            parent,
            false
        ),
        qazaInputViewHolderCallback = this
    )

    override fun getItemCount(): Int = qazaInputList.size

    override fun onBindViewHolder(
        holder: QazaInputViewHolder,
        position: Int
    ) {
        holder.onBind(qazaInputList[position])
    }

    override fun onSolatCounterChange(
        position: Int,
        count: Int
    ) {
        qazaInputList[position].solatCount = count
    }

    override fun onSaparSolatCounterChange(
        position: Int,
        count: Int
    ) {
        qazaInputList[position].saparSolatCount = count
    }

    fun setList(qazaInputList: List<QazaData>) {
        this.qazaInputList.clear()
        this.qazaInputList.addAll(qazaInputList)
        notifyDataSetChanged()
    }

    fun getList(): List<QazaData> = qazaInputList
}