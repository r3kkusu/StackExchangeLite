package com.stackexchangelite.app.ui.activities.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stackexchangelite.app.data.model.Welcome10
import com.stackexchangelite.app.data.network.MainApi
import com.stackexchangelite.app.ui.activities.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onErrorResume
import kotlinx.coroutines.launch
import org.jetbrains.annotations.NotNull
import javax.inject.Inject

class MainViewModel @Inject constructor(
    @NotNull private val mainApi: MainApi
) : ViewModel() {

    private val welcome10Data = MutableLiveData<Resource<Welcome10>>()

    fun pullQuestions(pageNumber: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            welcome10Data.postValue(Resource.loading(Welcome10()))
            try {
                var result = mainApi.getQuestions(pageNumber, "desc", "activity", "stackoverflow")
                if (result.isSuccessful) {
                    result.body()?.let { welcome10Data.postValue(Resource.success(it)) }
                } else {
                    welcome10Data.postValue(Resource.error("Failed to fetch data", Welcome10()))
                }
            } catch (exception: Exception) {
                welcome10Data.postValue(Resource.error("Failed to fetch data", Welcome10()))
            }
        }
    }

    fun getQuestionsData() = welcome10Data
}