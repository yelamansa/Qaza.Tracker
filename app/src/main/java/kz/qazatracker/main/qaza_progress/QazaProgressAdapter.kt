package kz.qazatracker.main.qaza_progress

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.qazatracker.R
import kz.qazatracker.qaza_hand_input.data.QazaData
import kz.qazatracker.utils.show

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

class QazaProgressViewHolder(
     itemView: View
) : RecyclerView.ViewHolder(itemView) {

    private val qazaProgressTextView: TextView = itemView.findViewById(R.id.qaza_progress_text_view)
    private val saparQazaProgressTextView: TextView =
        itemView.findViewById(R.id.sapar_qaza_progress_text_view)
    private val solatName: TextView = itemView.findViewById(R.id.solat_name_text_view)
    private val progressBar: ProgressBar = itemView.findViewById(R.id.qaza_progress_bar)

    fun onBind(
        qazaProgressData: QazaData
    ) {
        qazaProgressTextView.text = "${qazaProgressData.solatCount}"
        saparQazaProgressTextView.text = "${qazaProgressData.saparSolatCount}"
        solatName.text = itemView.resources.getString(qazaProgressData.solatNameResId)
        progressBar.progress = 100 - qazaProgressData.getTotalCompletedQazaPercent().toInt()
        if (qazaProgressData.hasSaparSolat) saparQazaProgressTextView.show()
    }
}