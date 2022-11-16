package com.ruhul.paginationwithoutpaginglibrary.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ruhul.paginationwithoutpaginglibrary.R
import com.ruhul.paginationwithoutpaginglibrary.databinding.ActivityMainBinding
import com.ruhul.paginationwithoutpaginglibrary.repository.model.Item
import com.ruhul.paginationwithoutpaginglibrary.viewModel.MovieViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var adapter: MovieIItemAdapter

    private val log = "MainActivityDebug"

    private var maxItemLoad = 10
    private var startItemLoad = 0

    //pagination wise data merge
    private lateinit var list: ArrayList<Item>

    //server responses(reserved list)
    private lateinit var responseList: List<Item>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initViewModel()
        getMovies()
        clickEvent()
    }

    private fun clickEvent() {
        binding.itemRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!binding.itemRV.canScrollVertically(1)) {

                    if (responseList.size != list.size) {
                        binding.progressBar.visibility = View.VISIBLE

                        startItemLoad = maxItemLoad + 1
                        maxItemLoad += 10

                        Log.d(log, "onScrolled startItem: $startItemLoad")
                        Log.d(log, "onScrolled maxItemLoad: $maxItemLoad")

                        for (i in startItemLoad..maxItemLoad) {
                            if (responseList.size - 1 >= i) {
                                list.add(responseList[i])
                            } else {
                                break
                            }
                        }

                        adapter.addItems(list.toMutableList())
                        binding.progressBar.visibility = View.GONE

                    } else {
                        Toast.makeText(this@MainActivity, "no data found", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    binding.progressBar.visibility = View.GONE
                }
            }
        })

        binding.testBtn.setOnClickListener {
            val intent = Intent(this, TestPaginationActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getMovies() {
        movieViewModel.getMovies()
            .observe(this, Observer {
                responseList = it

                Log.d(log, "response reserved list " + responseList.size)

                Log.d(log, "getMovies startItem: $startItemLoad")
                Log.d(log, "getMovies maxItemLoad: $maxItemLoad")

                if (responseList.isNotEmpty()) {
                    for (i in startItemLoad..maxItemLoad) {
                        if (responseList.size - 1 >= i) {
                            list.add(responseList[i])
                        } else {
                            break
                        }
                    }
                }
                setAdapter()
            })
    }

    private fun setAdapter() {
        adapter = MovieIItemAdapter(list, this)
        binding.itemRV.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.itemRV.adapter = adapter
    }

    private fun initViewModel() {
        movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        list = arrayListOf()
    }
}