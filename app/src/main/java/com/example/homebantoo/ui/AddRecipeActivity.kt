package com.example.homebantoo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.homebantoo.AddRecipeViewModel
import com.example.homebantoo.R
import com.example.homebantoo.databinding.ActivityAddRecipeBinding
import com.example.homebantoo.db.Recipe
import com.example.homebantoo.helper.ViewModelFactory

class AddRecipeActivity : AppCompatActivity() {
    private var isEdit = false
    private var recipe: Recipe? = null

    private lateinit var addRecipeViewModel: AddRecipeViewModel

    private  var _activityAddRecipeBinding: ActivityAddRecipeBinding? = null
    private val binding get() = _activityAddRecipeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityAddRecipeBinding = ActivityAddRecipeBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        addRecipeViewModel = obtainViewModel(this@AddRecipeActivity)

        recipe = intent.getParcelableExtra(EXTRA_NOTE)

        if (recipe != null) {
            isEdit = true
        } else {
            recipe = Recipe()
        }

        val actionBarTitle: String
        val btnTitle: String

        if (isEdit) {
            actionBarTitle = getString(R.string.change)
            btnTitle = getString(R.string.update)
            if (recipe != null) {
                recipe?.let { recipe ->
                    binding?.edtTitle?.setText(recipe.title)
                    binding?.edtDescription?.setText(recipe.ingredients)
                    binding?.edtStep?.setText(recipe.step)
                }
            }
        } else {
            actionBarTitle = getString(R.string.add)
            btnTitle = getString(R.string.save)
        }

        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding?.btnSubmit?.text = btnTitle

        binding?.btnSubmit?.setOnClickListener {
            val title = binding?.edtTitle?.text.toString().trim()
            val description = binding?.edtDescription?.text.toString().trim()
            val step = binding?.edtStep?.text.toString().trim()
            when {
                title.isEmpty() -> {
                    binding?.edtTitle?.error = getString(R.string.empty)
                }
                description.isEmpty() -> {
                    binding?.edtDescription?.error = getString(R.string.empty)
                }
                step.isEmpty() -> {
                    binding?.edtStep?.error = getString(R.string.empty)
                }
                else -> {
                    recipe.let { recipe ->
                        recipe?.title = title
                        recipe?.ingredients = description
                        recipe?.step = step
                    }
                    if (isEdit) {
                        addRecipeViewModel.update(recipe as Recipe)
                        showToast(getString(R.string.changed))
                    } else {
                        addRecipeViewModel.insert(recipe as Recipe)
                    }
                    finish()
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityAddRecipeBinding = null
    }

    private fun obtainViewModel(activity: AppCompatActivity): AddRecipeViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(AddRecipeViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (isEdit) {
            menuInflater.inflate(R.menu.menu_form, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> showAlertDialog(ALERT_DIALOG_DELETE)
            android.R.id.home -> showAlertDialog(ALERT_DIALOG_CLOSE)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE)
    }

    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String
        if (isDialogClose) {
            dialogTitle = getString(R.string.cancel)
            dialogMessage = getString(R.string.message_cancel)
        } else {
            dialogMessage = getString(R.string.message_delete)
            dialogTitle = getString(R.string.delete)
        }
        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle(dialogTitle)
            setMessage(dialogMessage)
            setCancelable(false)
            setPositiveButton(getString(R.string.yes)) { _, _ ->
                if (!isDialogClose) {
                    addRecipeViewModel.delete(recipe as Recipe)
                    showToast(getString(R.string.deleted))
                }
                finish()
            }
            setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    companion object {
        const val EXTRA_NOTE = "extra_note"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }
}