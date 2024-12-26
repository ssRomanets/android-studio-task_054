package com.example.task_054

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task_054.data.api.FilmsRepository
import com.example.task_054.models.FilmsResponse
import com.example.task_054.utils.Constants.Companion.limitFilms
import com.example.task_054.utils.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: FilmsRepository) :
    ViewModel() {
        val newsLiveData: MutableLiveData<Resources<FilmsResponse>> = MutableLiveData()

        init {
            getFilms()
        }

        private fun getFilms() =
            viewModelScope.launch{
                newsLiveData.postValue(Resources.Loading())
                val response = repository.getFilms(limitFilms)
                if (response.isSuccessful) {
                    response.body().let { res ->
                        newsLiveData.postValue(Resources.Success(res))
                    }
                } else {
                    newsLiveData.postValue(Resources.Error(message = response.message()))
                }
            }
    }

