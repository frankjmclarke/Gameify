package com.fclarke.gameifyfitnessandtodo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.fclarke.gameifyfitnessandtodo.R
import com.fclarke.gameifyfitnessandtodo.network.ItemsItem
import kotlinx.android.synthetic.main.recycler_list_row.view.*
import kotlin.random.Random

class ListAdapter() : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    var listData = ArrayList<ItemsItem>() //loadAPIData updates this

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_list_row, parent, false)
        val mv = MyViewHolder(inflater, listData.size)
        mv.itemView.setOnClickListener {
            val pos = mv.bindingAdapterPosition
            if(pos != NO_POSITION){
                onItemClick(pos)
            }
        }
        return mv

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    fun onItemClick(position: Int) {
        var clickedItem = listData[position]
        clickedItem.content = "Clicked"
        notifyItemChanged(position)
    }

    fun insertItem(view: View) {
        val index = Random.nextInt(8)
        val newItem = ItemsItem(3)
        listData.add(index, newItem)
        notifyItemInserted(index)
    }

    fun removeItem(view: View) {
        val index = Random.nextInt(8)
        listData.removeAt(index)
        notifyItemInserted(index)
    }

    class MyViewHolder(view: View, size: Int) : RecyclerView.ViewHolder(view) {

        private val tvTitle = view.tv_title
        private val tvPublisher = view.tv_description
        private val theSize :Int = size

        fun bind(data: ItemsItem) {
            tvTitle.text = theSize.toString()
            tvPublisher.text = data.content
           // tvPublisher.text = theSize.toString()
        }

    }
}