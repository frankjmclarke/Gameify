package com.fclarke.gameifyfitnessandtodo
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import com.fclarke.gameifyfitnessandtodo.adapter.ListAdapter
import com.fclarke.gameifyfitnessandtodo.business.Level
import com.fclarke.gameifyfitnessandtodo.business.MyClass
import com.fclarke.gameifyfitnessandtodo.business.attributes.DemonClass
import com.fclarke.gameifyfitnessandtodo.business.characters.Hero
import com.fclarke.gameifyfitnessandtodo.local.Shared
import com.fclarke.gameifyfitnessandtodo.network.ItemsItem
import com.fclarke.gameifyfitnessandtodo.viewmodel.MainActivityViewModel
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var listAdapter: ListAdapter
    private lateinit var sharedPreferences: Shared
    private lateinit var dateTimeString: String
    private lateinit var todoistAuth: String
    private lateinit var myChar : Hero

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val ai: ApplicationInfo = applicationContext.packageManager
            .getApplicationInfo(applicationContext.packageName, PackageManager.GET_META_DATA)

        //initSearchBox()
        sharedPreferences = Shared(this)
        iv_demon.setOnClickListener { launchActivity<FightActivity>() }
        val demon = DemonClass(sharedPreferences)
        //demon.value=1
        demon.write()
        demon.read()
        iv_demon.setImageResource(demon.getImage())
        val txtDemonName = findViewById(R.id.demon_name) as TextView
        txtDemonName.text="Inner Demon: "+demon.getName()

        val txtGoldAmount = findViewById(R.id.gold_amount) as TextView
        val txtExpAmount = findViewById(R.id.exp_amount) as TextView
        val txtManaAmount = findViewById(R.id.mana_amount) as TextView
        val txtStrengthAmount = findViewById(R.id.strength_amount) as TextView
        val txtLevelAmount = findViewById(R.id.level_amount) as TextView
        val txtPlayerClass = findViewById(R.id.player_class) as TextView

        initRecyclerView()
        val value = ai.metaData["todoistKey"]
        todoistAuth = "Bearer " + value.toString()

        //we want to know how many todoist tasks have been completed since last time, or 200 days ago if no last time

        //var dt: String? = sharedPreferences?.getString("TODOIST_SINCE_DATE")
        var dt: String? = "2021-08-01T20:16:09Z" //just for testing
        var ldt: LocalDateTime = LocalDateTime.now().minus(1, ChronoUnit.DAYS)
        dateTimeString = dt ?: ldt.toString()

        val btn_click_me = findViewById(R.id.insert_item) as Button
        btn_click_me.setOnClickListener {
            Toast.makeText(this@MainActivity, "You clicked me.", Toast.LENGTH_SHORT).show()
            listAdapter.insertItem(btn_click_me)
        }
        val btn_remove = findViewById(R.id.remove_item) as Button
        btn_remove.setOnClickListener {
            Toast.makeText(this@MainActivity, "You remove me.", Toast.LENGTH_SHORT).show()
            listAdapter.removeItem(btn_remove)
        }
        loadAPIData()

        viewModel.getGold()
            ?.observe(this) { n ->
                txtGoldAmount.setText("Gold: " + java.lang.String.valueOf(n))
            }
        viewModel.getExp()
            ?.observe(this) { exp ->
                val level =Level()
                var expGood = exp ?: 0
                var expNeededToLevelUp = level.exp2Level(expGood)+1
                expNeededToLevelUp=level.level2Exp(expNeededToLevelUp)
                txtExpAmount.setText("Exp: " + java.lang.String.valueOf(expGood)+"/"+expNeededToLevelUp.toString())
            }
        viewModel.getMana()
            ?.observe(this) { n ->
                txtManaAmount.setText("Mana: " + java.lang.String.valueOf(n))
            }
        viewModel.getStrength()
            ?.observe(this) { n ->
                txtStrengthAmount.setText("Strength: " + java.lang.String.valueOf(n))
            }
        viewModel.getStrength()
            ?.observe(this) { n ->
                txtLevelAmount.setText("Level: " + java.lang.String.valueOf(n))
            }
        viewModel.getClassNum()
            ?.observe(this) { n ->
                val myClass = MyClass(sharedPreferences)
                txtPlayerClass.setText("Class: " + n?.let { myClass.toString(it) })
            }

        myChar = Hero(sharedPreferences)
        myChar.addExperience(1)
        viewModel.setExp(myChar.exp.value)
        viewModel.setMana(3)
        viewModel.setStrength(3)
        viewModel.recalcLevel()
        viewModel.setClassNum(1)


    }

    private fun initRecyclerView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            val decoration =
                DividerItemDecoration(applicationContext, StaggeredGridLayoutManager.VERTICAL)
            addItemDecoration(decoration)
            listAdapter = ListAdapter()
            adapter = listAdapter
        }
    }

    fun loadAPIData() {
        //make the data observable, then call api
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.getListObserver().observe(this, {
            if (it != null) {
                if (it.items != null) {
                    listAdapter.listData = it.items as ArrayList<ItemsItem>
                    listAdapter.notifyDataSetChanged()
                    //myChar.addGold(it.items.size)
                    viewModel.goldL.value=it.items.size//You have to use postValue(), when you are changing the value from a background thread.
                }

            } else {
                Toast.makeText(this, "Error in fetching data", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.makeApiCall(todoistAuth, dateTimeString)//input
    }
    private inline fun<reified T> launchActivity(){
        val intent = Intent(this, T::class.java)
        startActivity(intent)
    }
}