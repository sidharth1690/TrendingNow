package com.example.trendingnow.ui.main.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trendingnow.R
import com.example.trendingnow.data.source.model.Items
import com.example.trendingnow.ui.main.view.viewmodel.RepoViewModel
import com.example.trendingnow.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_repo.*

@AndroidEntryPoint
class GitRepoActivity :  AppCompatActivity() {

    private val mainViewModel : RepoViewModel by viewModels()
    private lateinit var adapter: RepoAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo)
        setData()
        setObserver()
    }


    private fun setData() {
        rv_repo.layoutManager = LinearLayoutManager(this)
        adapter = RepoAdapter(arrayListOf())
        rv_repo.addItemDecoration(
            DividerItemDecoration(
                rv_repo.context,
                (rv_repo.layoutManager as LinearLayoutManager).orientation
            )
        )
        rv_repo.adapter = adapter
    }


    override fun onCreateOptionsMenu(menu: android.view.Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val item = menu.findItem(R.id.action_search);
        val searchView = item?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                Log.d("onQueryTextChange", "query: " + query)
                adapter.filter.filter(query)
                return true
            }
        })

        item.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                adapter.filter.filter("")
                return true
            }

            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    private fun setObserver() {
        mainViewModel.gitTrendingList.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    it.dataset?.items?.let { users -> renderList(users) }
                    rv_repo.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    rv_repo.visibility = View.GONE
                }
                Status.ERROR -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun renderList(items: List<Items>) {
        adapter.addData(items)
        adapter.notifyDataSetChanged()
    }

}