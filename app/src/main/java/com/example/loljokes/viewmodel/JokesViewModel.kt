package com.example.loljokes.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loljokes.model.Joke
import com.example.loljokes.model.JokeRepository

import kotlinx.coroutines.launch


class JokesViewModel : ViewModel() {

    private val repository = JokeRepository()
    val jokesLiveData : LiveData<List<Joke>>
    get() = repository.jokes

    init {
        viewModelScope.launch {
            repository.updateList()
            Log.e("ViewModel", "Api call, update List, ${repository.jokes}")
        }
    }

    fun refreshContent() {
        viewModelScope.launch {
            repository.updateList()
            Log.e("ViewModel", "Api call, update List, ${repository.jokes}")
        }
    }
}