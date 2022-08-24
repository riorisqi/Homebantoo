package com.example.homebantoo

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.homebantoo.api.ApiConfig
import com.example.homebantoo.api.ResultsItem
import com.example.homebantoo.api.SearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val recipes = MutableLiveData<ArrayList<ResultsItem>>()

    fun getRecipe(context: Context, title: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getSearch(title)
        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    recipes.postValue(response.body()?.results as ArrayList<ResultsItem>?)
                } else {
                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                _isLoading.value = false
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getListRecipe(): LiveData<ArrayList<ResultsItem>>{
        return recipes
    }
}