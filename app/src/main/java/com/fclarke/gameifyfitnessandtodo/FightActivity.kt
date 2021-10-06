package com.fclarke.gameifyfitnessandtodo

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.fclarke.gameifyfitnessandtodo.adapter.CardListAdapter
import com.fclarke.gameifyfitnessandtodo.business.attributes.DemonClass
import com.fclarke.gameifyfitnessandtodo.business.cards.CardItem
import com.fclarke.gameifyfitnessandtodo.business.characters.Hero
import com.fclarke.gameifyfitnessandtodo.local.Shared
import com.fclarke.gameifyfitnessandtodo.viewmodel.FightActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fight_list_row.*
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit


class FightActivity  : AppCompatActivity(){

    private lateinit var viewModel: FightActivityViewModel
    private lateinit var listAdapter: CardListAdapter
    private lateinit var sharedPreferences: Shared
    private lateinit var dateTimeString: String
    private lateinit var myChar : Hero

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fight)
        val ai: ApplicationInfo = applicationContext.packageManager
            .getApplicationInfo(applicationContext.packageName, PackageManager.GET_META_DATA)

        //initSearchBox()
        sharedPreferences = Shared(this)
        val ivDemon = findViewById(R.id.iv_demon) as ImageView
        val demon = DemonClass(sharedPreferences)
        //demon.value=1
        demon.write()
        demon.read()
        ivDemon.setImageResource(demon.getImage())
        val txtDemonName = findViewById(R.id.demon_name) as TextView
        txtDemonName.text="Inner Demon: "+demon.getName()

        val txtGoldAmount = findViewById(R.id.gold_amount) as TextView
        val txtExpAmount = findViewById(R.id.exp_amount) as TextView
        val txtManaAmount = findViewById(R.id.mana_amount) as TextView
        val txtStrengthAmount = findViewById(R.id.strength_amount) as TextView
        val txtLevelAmount = findViewById(R.id.level_amount) as TextView
        val txtPlayerClass = findViewById(R.id.player_class) as TextView

        initRecyclerView()

        //we want to know how many todoist tasks have been completed since last time, or 200 days ago if no last time

        //var dt: String? = sharedPreferences?.getString("TODOIST_SINCE_DATE")
        var dt: String? = "2021-08-01T20:16:09Z" //just for testing
        var ldt: LocalDateTime = LocalDateTime.now().minus(1, ChronoUnit.DAYS)
        dateTimeString = dt ?: ldt.toString()

        val btn_click_me = findViewById(R.id.insert_item) as Button
        btn_click_me.setOnClickListener {
            Toast.makeText(this@FightActivity, "You clicked me.", Toast.LENGTH_SHORT).show()
            listAdapter.insertItem(btn_click_me)
        }
        val btn_remove = findViewById(R.id.remove_item) as Button
        btn_remove.setOnClickListener {
            Toast.makeText(this@FightActivity, "You remove me.", Toast.LENGTH_SHORT).show()
            listAdapter.removeItem(btn_remove)
        }
        loadAPIData()

        viewModel.getDescription()
            ?.observe(this) { n ->
                tv_fight_title.setText(n)
            }
        viewModel.getIcon()
            ?.observe(this) { n ->
                if (n != null) {
                    fight_icon.setImageResource(n)
                }
            }
        viewModel.getIcon1()
            ?.observe(this) { n ->
                if (n != null) {
                    image_view1.setImageResource(n)
                }
            }
        viewModel.getIcon2()
            ?.observe(this) { n ->
                if (n != null) {
                    image_view2.setImageResource(n)
                }
            }
        viewModel.getIcon3()
            ?.observe(this) { n ->
                if (n != null) {
                    image_view3.setImageResource(n)
                }
            }
        viewModel.getIcon4()
            ?.observe(this) { n ->
                if (n != null) {
                    image_view4.setImageResource(n)
                }
            }

        myChar = Hero(sharedPreferences)


    }

    private fun initRecyclerView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@FightActivity)
            val decoration =
                DividerItemDecoration(applicationContext, StaggeredGridLayoutManager.VERTICAL)
            addItemDecoration(decoration)
            listAdapter = CardListAdapter()
            adapter = listAdapter
        }
    }

    fun loadAPIData() {
        //make the data observable, then call api
        viewModel = ViewModelProvider(this).get(FightActivityViewModel::class.java)
        viewModel.getListObserver().observe(this, {
            if (it != null) {
                if (it.items != null) {
                    listAdapter.listData = it.items as ArrayList<CardItem>
                    listAdapter.notifyDataSetChanged()
                }

            } else {

                Toast.makeText(this, "Error in fetching data", Toast.LENGTH_SHORT).show()
            }
        })
        //        viewModel.makeApiCall(todoistAuth, dateTimeString)//input

    }

}