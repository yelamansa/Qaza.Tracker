package kz.qazatracker.main.progress

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.qazatracker.R
import kz.qazatracker.qaza_input.data.QazaData

class QazaProgressAdapter : RecyclerView.Adapter<QazaProgressViewHolder>() {

    private val qazaProgressList: MutableList<QazaData> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QazaProgressViewHolder =
        QazaProgressViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_item_qaza_progress, parent, false)
        )

    override fun getItemCount(): Int = qazaProgressList.size

    override fun onBindViewHolder(
        holder: QazaProgressViewHolder,
        position: Int
    ) {
        holder.onBind(qazaProgressList[position])
    }

    fun setList(list: List<QazaData>) {
        qazaProgressList.clear()
        qazaProgressList.addAll(list)
        notifyDataSetChanged()
    }
}

class QazaProgressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val progressTextView: TextView = itemView.findViewById(R.id.qaza_progress_text_view)
    private val solatName: TextView = itemView.findViewById(R.id.solat_name_text_view)
    private val progressBar: ProgressBar = itemView.findViewById(R.id.qaza_progress_bar)

    fun onBind(
        qazaProgressData: QazaData
    ) {
        progressTextView.text = "${qazaProgressData.solatCount}"
        solatName.text = qazaProgressData.solatName
        progressBar.progress = 100 - qazaProgressData.getTotalCompletedQazaPercent().toInt()
    }
}