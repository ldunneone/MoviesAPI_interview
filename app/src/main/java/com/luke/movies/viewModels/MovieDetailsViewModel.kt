package com.luke.movies.viewModels

import androidx.lifecycle.ViewModel
import com.luke.movies.base.output.SimpleOutput
import com.luke.movies.data.models.movieDetails.MovieDetails
import com.luke.movies.data.repositories.movieDetails.MovieDetailsRepository
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsViewModel(private val repository: MovieDetailsRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    var output = SimpleOutput<MovieDetails, String>()

    fun getMovieDetails(movieId: Int) {
        repository.getMovieDetails(compositeDisposable, movieId)
    }

    init {
        output = repository.output
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}