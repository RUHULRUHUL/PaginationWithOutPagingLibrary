package com.ruhul.paginationwithoutpaginglibrary.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ruhul.paginationwithoutpaginglibrary.repository.MovieRepository
import com.ruhul.paginationwithoutpaginglibrary.repository.model.Item
import com.ruhul.paginationwithoutpaginglibrary.repository.model.ItemResponse

class MovieViewModel : ViewModel() {
    private val movieRepository = MovieRepository()

    fun getMovies(): MutableLiveData<List<Item>> {
        return movieRepository.getMovieList()
    }

    fun getProgressStatus(): MutableLiveData<Boolean> {
        return movieRepository.responseProgressStatus
    }
}