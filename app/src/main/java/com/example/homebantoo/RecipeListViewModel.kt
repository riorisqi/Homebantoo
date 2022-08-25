package com.example.homebantoo

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.homebantoo.db.Recipe
import com.example.homebantoo.db.RecipeRepository

class RecipeListViewModel(application: Application) : ViewModel() {
    private val mRecipeRepository: RecipeRepository = RecipeRepository(application)

    fun getAllNotes(): LiveData<List<Recipe>> = mRecipeRepository.getAllNotes()
}