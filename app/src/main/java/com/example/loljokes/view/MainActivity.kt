package com.example.loljokes.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loljokes.R
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

        this.supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.action_bar)



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