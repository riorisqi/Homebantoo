package com.example.homebantoo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.homebantoo.api.ApiConfig
import com.example.homebantoo.api.DetailResponse
import com.example.homebantoo.api.Results
import com.example.homebantoo.databinding.ActivityDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var dataDetail: DetailResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val moveRecipe = intent.getStringExtra(DATA_KEY)
        moveRecipe.let {
            if (it != null) {
                findUser(it)
            }
        }
    }

    private fun findUser(key: String) {
        showLoading(true)

        val client = ApiConfig.getApiService().getDetaiRecipe(key)
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        dataDetail = responseBody

                        Log.e("HEHE", dataDetail.results?.title.toString())

                        binding.apply{
                            tvTitle.text = dataDetail.results?.title
                            tvDescriptionDisease.text = dataDetail.results?.ingredient.toString()
                            tvRecommendationCare.text = dataDetail.results?.step.toString()
                        }
                        Glide.with(this@DetailActivity)
                            .load(dataDetail.results?.thumb)
                            .into(binding.ivFood)
                    }
                } else {
                    Toast.makeText(applicationContext, "onFailure: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
//                showLoading(false)
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar2.visibility = View.VISIBLE
        } else {
            binding.progressBar2.visibility = View.GONE
        }
    }

    companion object {
        const val DATA_KEY = ""
    }
}