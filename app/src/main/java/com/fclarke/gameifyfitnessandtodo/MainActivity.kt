package com.fclarke.gameifyfitnessandtodo

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.res.Resources
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
import com.fclarke.gameifyfitnessandtodo.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel:  MainActivityViewModel
    private lateinit var listAdapter: ListAdapter


    companion object {
        var todoistAuth: String = ""
    }

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
    }

    private fun initSearchBox() {
        inputName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loadAPIData("*","[\"projects\"]")//s.toString()
            }
        })
    }

    private fun initRecyclerView(){
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            val decoration  = DividerItemDecoration(applicationContext, StaggeredGridLayoutManager.VERTICAL)
            addItemDecoration(decoration)
            listAdapter = ListAdapter()
            adapter =listAdapter
        }
    }

    fun loadAPIData(input: String, input2: String) {
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.getListObserver().observe(this, {
            if(it != null) {
                //update adapter...
                listAdapter.listData = it.projects
                listAdapter.notifyDataSetChanged()
            }
            else {
                Toast.makeText(this, "Error in fetching data", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.makeApiCall(input, input2)//input
    }
}