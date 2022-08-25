package com.example.homebantoo

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.homebantoo.db.Recipe
import com.example.homebantoo.db.RecipeRepository

class AddRecipeViewModel(application: Application) : ViewModel() {
    private val mRecipeRepository: RecipeRepository = RecipeRepository(application)

    fun insert(recipe: Recipe) {
        mRecipeRepository.insert(recipe)
    }

    fun update(recipe: Recipe) {
        mRecipeRepository.update(recipe)
    }

    fun delete(recipe: Recipe) {
        mRecipeRepository.delete(recipe)
    }
}