package com.fclarke.gameifyfitnessandtodo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fclarke.gameifyfitnessandtodo.R
import com.fclarke.gameifyfitnessandtodo.network.CompletedItem
import kotlinx.android.synthetic.main.recycler_list_row.view.*

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    var listData = ArrayList<CompletedItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_list_row, parent, false)
        return MyViewHolder(inflater, listData.size)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    class MyViewHolder(view: View, size: Int) : RecyclerView.ViewHolder(view) {

        private val tvTitle = view.tvTitle
        private val tvPublisher = view.tvPublisher
        private val tvDescription = view.tvDescription
        private val thesize :Int = size

        fun bind(data: CompletedItem) {
            tvTitle.text = data.content
            tvPublisher.text = thesize.toString()
            tvDescription.text = data.content
        }
    }
}