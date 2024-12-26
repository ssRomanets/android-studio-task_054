package com.example.task_054

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.lesson_054.adapter.MainLoadStateAdapter
import com.example.task_054.adapter.MainAdapter
import com.example.task_054.databinding.ActivityMainBinding
import com.example.task_054.db.FilmItemDao
import com.example.task_054.db.FilmItemDatabase
import com.example.task_054.utils.Resources
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    private lateinit var dao: FilmItemDao
    private val faoViewModel: FaoMainViewModel by viewModels { MainViewModelFactory(dao) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.newsLiveData.observe(this) { response ->
            when(response) {
                is Resources.Success -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    response.data?.let {

                        val adapter = MainAdapter()
                        binding.filmsAdapterRV.adapter = adapter.withLoadStateFooter(MainLoadStateAdapter())
                        dao = FilmItemDatabase.getInstance(this, it.items).itemDao()
                        lifecycleScope.launch{ faoViewModel.data.collectLatest { adapter.submitData(it) }}
                    }
                }
                is Resources.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resources.Error -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    response.data?.let{Log.e("checkData", "Error $it")}
                }
            }
        }

    }
}
