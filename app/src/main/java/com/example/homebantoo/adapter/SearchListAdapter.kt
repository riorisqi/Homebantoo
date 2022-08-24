package com.example.homebantoo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homebantoo.api.ResultsItem
import com.example.homebantoo.databinding.ItemRowRecipeBinding

class SearchListAdapter(private val searchList: List<ResultsItem>) :
    RecyclerView.Adapter<SearchListAdapter.ListViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null

    inner class ListViewHolder(val binding: ItemRowRecipeBinding) :
        RecyclerView.ViewHolder(binding.root){
            fun bind(recipe: ResultsItem){
                binding.root.setOnClickListener {
                    onItemClickCallback?.onItemClicked(recipe)
                }

                binding.tvItemName.text = recipe.title
                Glide.with(itemView.context)
                    .load(recipe.thumb)
                    .circleCrop()
                    .into(binding.imgItemPhoto)
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val itemBinding =
            ItemRowRecipeBinding.inflate(LayoutInflater.from(parent.context),
                parent, false)
        return ListViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(searchList[position])
    }

    override fun getItemCount(): Int = searchList.size

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(recipe: ResultsItem)
    }

}