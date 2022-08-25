package com.example.homebantoo.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.homebantoo.db.Recipe

class RecipeDiffCallback(private val mOldRecipeList: List<Recipe>, private val mNewRecipeList: List<Recipe>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldRecipeList.size
    }

    override fun getNewListSize(): Int {
        return mNewRecipeList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldRecipeList[oldItemPosition].id == mNewRecipeList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldRecipeList[oldItemPosition]
        val newEmployee = mNewRecipeList[newItemPosition]
        return oldEmployee.title == newEmployee.title &&
                oldEmployee.ingredients == newEmployee.ingredients &&
                oldEmployee.step == newEmployee.step
    }
}