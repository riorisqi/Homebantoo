package com.example.homebantoo.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homebantoo.R
import com.example.homebantoo.RecipeListViewModel
import com.example.homebantoo.adapter.RecipeAdapter
import com.example.homebantoo.databinding.ActivityRecipeListBinding
import com.example.homebantoo.helper.ViewModelFactory

class RecipeListActivity : AppCompatActivity() {
    private var _activityRecipeListBinding: ActivityRecipeListBinding? = null
    private val binding get() = _activityRecipeListBinding
    private lateinit var adapter: RecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)

        _activityRecipeListBinding = ActivityRecipeListBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        adapter = RecipeAdapter()
        binding?.rvNotes?.layoutManager = LinearLayoutManager(this)
        binding?.rvNotes?.setHasFixedSize(true)
        binding?.rvNotes?.adapter = adapter

        val recipeListViewModel = obtainViewModel(this@RecipeListActivity)
        recipeListViewModel.getAllNotes().observe(this, { recipeList ->
            if (recipeList != null) {
                adapter.setListNotes(recipeList)
            }
        })

        binding?.fabAdd?.setOnClickListener { view ->
            if (view.id == R.id.fab_add) {
                val intent = Intent(this@RecipeListActivity, AddRecipeActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): RecipeListViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(RecipeListViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityRecipeListBinding = null
    }
}