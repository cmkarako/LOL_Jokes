package com.example.loljokes.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class JokeRepository () {

    private val _jokes = MutableLiveData<List<Joke>>()
    val jokes : LiveData<List<Joke>>
    get() = _jokes

    suspend fun updateList() {
        val result = RetrofitInstance.api.getJokes()
        if (result.isSuccessful && result.body() != null) {
            _jokes.postValue(result.body())
            Log.e("Repo", "Response Successful")
        } else {
            Log.e("Repo", "Response not successful")
        }
    }
}