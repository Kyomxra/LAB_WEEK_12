package com.example.test_lab_week_12

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.test_lab_week_12.api.MovieService
import com.example.test_lab_week_12.model.Movie

class MovieRepository(private val movieService: MovieService) {

    private val apiKey = "d236d514801a3dc150aee5227429a9a1"

    // LiveData that contains a list of movies
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    suspend fun fetchMovies() {
        try {
            val response = movieService.getPopularMovies(apiKey)

            Log.d("MovieRepo", "API success. Total results = ${response.results.size}")

            _movies.postValue(response.results)

        } catch (e: Exception) {
            Log.e("MovieRepo", "Error fetching movies", e)
            _error.postValue(e.message)
        }
    }
}
