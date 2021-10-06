package com.fclarke.gameifyfitnessandtodo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.fclarke.gameifyfitnessandtodo.R
import com.fclarke.gameifyfitnessandtodo.business.cards.CardItem
import kotlinx.android.synthetic.main.fight_list_row.view.*
import kotlinx.android.synthetic.main.recycler_list_row.view.image_view
import kotlin.random.Random

class CardListAdapter() : RecyclerView.Adapter<CardListAdapter.MyViewHolder>() {

    var listData = ArrayList<CardItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        listData.add(
            CardItem("Rage@",R.drawable.ic_enrage,
                R.drawable.ic_fist,R.drawable.ic_fist,R.drawable.ic_fist,R.drawable.ic_fist))
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.fight_list_row, parent, false)
        val mv = MyViewHolder(inflater, listData.size)
        mv.itemView.setOnClickListener {
            val pos = mv.bindingAdapterPosition
            if (pos != NO_POSITION) {
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
        clickedItem.fightTitle = "Clicked"
        notifyItemChanged(position)
    }

    fun insertItem(view: View) {
        val index = Random.nextInt(8)
        val newItem = CardItem(
            "Rage!",
            R.drawable.ic_enrage,
            R.drawable.ic_fist,
            R.drawable.ic_fist,
            R.drawable.ic_fist,
            R.drawable.ic_fist
        )
        listData.add( newItem)
        notifyItemInserted(index)
    }

    fun removeItem(view: View) {
        val index = Random.nextInt(8)
        listData.removeAt(index)
        notifyItemInserted(index)
    }

    class MyViewHolder(view: View, size: Int) : RecyclerView.ViewHolder(view) {

        private val tvTitle = view.tv_fight_title
        private var ivCardIcon = view.fight_icon
        private var ivCardIcon1 = view.image_view1
        private var ivCardIcon2 = view.image_view2
        private var ivCardIcon3 = view.image_view3
        private var ivCardIcon4 = view.image_view4

        fun bind(data: CardItem) {
            ivCardIcon.setImageResource(data.fightIcon)
            ivCardIcon1.setImageResource(data.icon1)
            ivCardIcon2.setImageResource(data.icon2)
            ivCardIcon3.setImageResource(data.icon3)
            ivCardIcon4.setImageResource(data.icon4)
            tvTitle.text = data.fightTitle
        }

    }
}