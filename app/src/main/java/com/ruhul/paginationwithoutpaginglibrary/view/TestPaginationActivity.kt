package com.ruhul.paginationwithoutpaginglibrary.view

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ruhul.paginationwithoutpaginglibrary.databinding.ActivityTestPaginationBinding
import com.ruhul.paginationwithoutpaginglibrary.repository.model.Item
import com.ruhul.paginationwithoutpaginglibrary.viewModel.MovieViewModel

class TestPaginationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestPaginationBinding

    private lateinit var movieViewModel: MovieViewModel
    private lateinit var adapter: MovieIItemAdapter

    private val log = "MainActivityDebug"
    private lateinit var progressDialog: ProgressDialog

    private var startItemLoad = 0

    //pagination wise data merge
    private lateinit var list: ArrayList<Item>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestPaginationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        clickEvent()
    }

    private fun clickEvent() {
        binding.showItemBtn.setOnClickListener {
            if (!TextUtils.isEmpty(binding.enterNumber.text)
                && binding.enterNumber.text?.isDigitsOnly() == true
            ) {
                val maxItemLoad: Int = binding.enterNumber.text.toString().toInt()
                getMovies(maxItemLoad)
            } else {
                binding.enterNumber.error = "Enter number "
            }
        }

    }

    private fun getMovies(maxItemLoad: Int) {

        progressDialog.show()
        //Progress Status Manage
        movieViewModel.getProgressStatus().observe(this, Observer {
            if (it) {
                progressDialog.dismiss()
            } else {
                progressDialog.show()
            }
        })

        movieViewModel.getMovies()
            .observe(this, Observer {
                Log.d(log, "getMovies startItem: $startItemLoad")
                Log.d(log, "getMovies maxItemLoad: $maxItemLoad")

                list.clear()

                if (it.size >= maxItemLoad) {
                    if (it.isNotEmpty()) {
                        for (i in startItemLoad until maxItemLoad) {
                            if (it.size - 1 >= i) {
                                list.add(it[i])
                            } else {
                                break
                            }
                        }
                    }
                } else {
                    binding.enterNumberLayout.helperText = " max Item:" + it.size
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

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("please wait load item")
        progressDialog.setCanceledOnTouchOutside(false)
    }
}