package com.netcracker.application.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.netcracker.application.R
import com.netcracker.application.data.entity.Hardware
import com.netcracker.application.listener.ChooseDetailListener
import kotlinx.android.synthetic.main.source_item.view.*

class HardwareListAdapter(list: MutableList<Hardware>) : RecyclerView.Adapter<HardwareListAdapter.ViewHolder>() {

    private val mItems: MutableList<Hardware> = list
    private val mListeners: MutableList<ChooseDetailListener> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.source_item, parent, false)
        return ViewHolder(view).listen { position, type ->
            showDetailedHardware(position)
        }
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mItems[position]
        holder.textView.text = item.name
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val textView = view.hardware_text_view!!
    }

    fun addListener(listener: ChooseDetailListener) {
        mListeners.add(listener)
    }

    fun showDetailedHardware(position: Int) {
        mListeners.forEach {
            it.showDetailedHardware(position)
        }
    }

    fun <T: RecyclerView.ViewHolder > T.listen(event: (position: Int, type: Int) -> Unit): T {
        itemView.setOnClickListener {
            event.invoke(adapterPosition, itemViewType)
        }
        return this
    }

    operator fun get(position: Int): Hardware {
        return mItems[position]
    }

}