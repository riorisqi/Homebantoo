package com.example.homebantoo.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homebantoo.MainViewModel
import com.example.homebantoo.R
import com.example.homebantoo.adapter.SearchListAdapter
import com.example.homebantoo.api.ResultsItem
import com.example.homebantoo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var searchListAdapter: SearchListAdapter
    private var list = ArrayList<ResultsItem>()
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.app_nav_title)

        viewModel =
            ViewModelProvider(this,
                ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        val layoutManager = LinearLayoutManager(this)
        binding.rvRecipe.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvRecipe.addItemDecoration(itemDecoration)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        viewModel.isLoading.observe(this@MainActivity) {
            showLoading(it)
        }
        viewModel.getListRecipe().observe(this@MainActivity){
            setRecipeData(it)
        }

        binding.svRecipe.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        binding.svRecipe.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return if(query.isEmpty())
                    true
                else {
                    val size = list.size
                    list.clear()
                    binding.rvRecipe.adapter?.notifyItemRangeRemoved(0, size)

                    viewModel.getRecipe(applicationContext, query)

                    true
                }
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    private fun setRecipeData(items: List<ResultsItem>) {
        for (recipe in items) {
            val new = ResultsItem(recipe.difficulty, recipe.times, recipe.thumb,
                recipe.title, recipe.key, recipe.serving)
            list.add(new)
        }
        searchListAdapter = SearchListAdapter(list)
        binding.rvRecipe.adapter = searchListAdapter
        binding.rvRecipe.layoutManager = LinearLayoutManager(this)

        searchListAdapter.setOnItemClickCallback(object : SearchListAdapter.OnItemClickCallback {
            override fun onItemClicked(recipe: ResultsItem) {
                moveToDetail(recipe)
            }
        })
    }

    private fun catchUsername(data: ResultsItem): ResultsItem? {
        var select: ResultsItem? = null

        for(recipe in list) {
            if(recipe.key == data.key)
                select = recipe
        }

        return select
    }

    private fun moveToDetail(user: ResultsItem){
        val usernameCatch = catchUsername(user)
        val intentToDetail = Intent(this@MainActivity, DetailActivity::class.java)
        intentToDetail.putExtra(DetailActivity.DATA_KEY, usernameCatch?.key.toString())
        startActivity(intentToDetail)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        val id = item.getItemId()

        if (id == R.id.add_recipe) {
            val intent = Intent(this@MainActivity, RecipeListActivity::class.java)
            startActivity(intent)

            return true
        }
        if (id == R.id.food_list) {
            Toast.makeText(this, "Item Two Clicked", Toast.LENGTH_LONG).show()
            return true
        }

        return super.onOptionsItemSelected(item)

    }

}