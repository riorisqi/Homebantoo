package com.example.homebantoo.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.homebantoo.databinding.ItemRecipeBinding
import com.example.homebantoo.db.Recipe
import com.example.homebantoo.helper.RecipeDiffCallback
import com.example.homebantoo.ui.AddRecipeActivity

class RecipeAdapter : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {
    private val listNotes = ArrayList<Recipe>()
    fun setListNotes(listNotes: List<Recipe>) {
        val diffCallback = RecipeDiffCallback(this.listNotes, listNotes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listNotes.clear()
        this.listNotes.addAll(listNotes)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(listNotes[position])
    }

    override fun getItemCount(): Int {
        return listNotes.size
    }

    inner class RecipeViewHolder(private val binding: ItemRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: Recipe) {
            with(binding) {
                tvItemName.text = recipe.title
                cardView.setOnClickListener {
                    val intent = Intent(it.context, AddRecipeActivity::class.java)
                    intent.putExtra(AddRecipeActivity.EXTRA_NOTE, recipe)
                    it.context.startActivity(intent)
                }
            }
        }
    }
}