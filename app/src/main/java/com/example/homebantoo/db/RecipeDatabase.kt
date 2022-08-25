package com.example.homebantoo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Recipe::class], version = 1)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

    companion object {
        @Volatile
        private var INSTANCE: RecipeDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): RecipeDatabase {
            if (INSTANCE == null) {
                synchronized(RecipeDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        RecipeDatabase::class.java, "note_database")
                        .build()
                }
            }
            return INSTANCE as RecipeDatabase
        }
    }
}