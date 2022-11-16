package com.ruhul.paginationwithoutpaginglibrary.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ruhul.paginationwithoutpaginglibrary.databinding.MovieRowItemBinding
import com.ruhul.paginationwithoutpaginglibrary.repository.model.Item

class MovieIItemAdapter(
    private var movieList: MutableList<Item>?,
    private var context: Context

) : RecyclerView.Adapter<MovieIItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            MovieRowItemBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = movieList?.get(position)
        item?.let {
            Glide.with(context).load(item.poster).into(holder.binding.Image)
        }
        holder.binding.movieName.text = item?.title + "index = $position "

    }

    override fun getItemCount(): Int {
        return movieList?.size ?: 0
    }


    fun addItems(items: MutableList<Item?>) {

        val size = movieList?.size

        if (items.isNotEmpty()) {
            movieList?.toCollection(items)?.addAll(items)
        }
        if (size != null) {
            notifyItemInserted(size)
        }
    }

    class ViewHolder(val binding: MovieRowItemBinding) : RecyclerView.ViewHolder(binding.root)


}