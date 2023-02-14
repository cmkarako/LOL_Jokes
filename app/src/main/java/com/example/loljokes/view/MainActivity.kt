package com.example.loljokes.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loljokes.databinding.ActivityMainBinding
import com.example.loljokes.viewmodel.JokesViewModel

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var jokesAdapter: JokesAdapter

    lateinit var viewModel : JokesViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(JokesViewModel::class.java)
        setupRecyclerView()
        observeViewModel()


        binding.swipeRefresh.setOnRefreshListener {
            Log.e(TAG, "Swipe Refresh")
            binding.swipeRefresh.isRefreshing = false
            viewModel.refreshContent()
            binding.progressBar.isVisible = false
        }

    }

    private fun setupRecyclerView() = binding.rvJokes.apply {
        jokesAdapter = JokesAdapter()
        adapter = jokesAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)
    }

    fun observeViewModel() {
        viewModel.jokesLiveData.observe(this, Observer { jokes ->
            binding.rvJokes.visibility = View.VISIBLE
            jokes?.let { jokesAdapter.jokes  = it}
        })
    }
}