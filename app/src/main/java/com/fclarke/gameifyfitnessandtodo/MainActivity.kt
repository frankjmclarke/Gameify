package com.fclarke.gameifyfitnessandtodo
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import com.fclarke.gameifyfitnessandtodo.adapter.ListAdapter
import com.fclarke.gameifyfitnessandtodo.business.characters.Hero
import com.fclarke.gameifyfitnessandtodo.local.Shared
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

        //Toast.makeText(applicationContext,todoistAuth,Toast.LENGTH_LONG).show()
        initSearchBox()
        initRecyclerView()
        val value = ai.metaData["todoistKey"]
        todoistAuth = "Bearer " + value.toString()

        //we want to know how many todoist tasks have been completed since last time, or 200 days ago if no last time
        sharedPreferences = Shared(this)
        //var dt: String? = sharedPreferences?.getString("TODOIST_SINCE_DATE")
        var dt: String? = "2021-09-29T20:16:09Z" //just for testing
        var ldt: LocalDateTime = LocalDateTime.now().minus(200, ChronoUnit.DAYS)
        dateTimeString = dt ?: ldt.toString()

        myChar = Hero(sharedPreferences)
        myChar.addExperience(1)

    }

    private fun initSearchBox() {
        inputName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loadAPIData()//"*","[\"projects\"]"
                //labels, projects,items, notes, sections, filters, reminders, locations, user, live_notifications,
                //collaborators, user_settings, notification_settings, user_plan_limits, stats.
            }
        })
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
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.getListObserver().observe(this, {
            if (it != null) {
                if (it.items != null) {
                    listAdapter.listData = it.items
                    listAdapter.notifyDataSetChanged()

                    myChar.addGold(it.items.size)
                }

            } else {
                Toast.makeText(this, "Error in fetching data", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.makeApiCall(todoistAuth, dateTimeString)//input
    }
}