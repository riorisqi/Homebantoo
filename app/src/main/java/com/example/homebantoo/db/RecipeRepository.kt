package com.example.homebantoo.db

import android.app.Application
import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class RecipeRepository(application: Application) {
    private val mRecipesDao: RecipeDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = RecipeDatabase.getDatabase(application)
        mRecipesDao = db.recipeDao()
    }

    fun getAllNotes(): LiveData<List<Recipe>> = mRecipesDao.getAllNotes()

    fun insert(recipe: Recipe) {
        executorService.execute { mRecipesDao.insert(recipe) }
    }

    fun delete(recipe: Recipe) {
        executorService.execute { mRecipesDao.delete(recipe) }
    }

    fun update(recipe: Recipe) {
        executorService.execute { mRecipesDao.update(recipe) }
    }
}