package com.example.calculatorapp.uilayer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import androidx.core.text.HtmlCompat
import com.example.calculatorapp.R

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        val customToolbar: Toolbar = findViewById(R.id.app_bar_history)
        setSupportActionBar(customToolbar)

        // Access the menu ImageView inside the custom toolbar
        val menu: ImageView = customToolbar.findViewById(R.id.menu_history)
        val changetheme: ImageView = customToolbar.findViewById(R.id.change_history)

        val aboutTextView: TextView = findViewById(R.id.historyshow)
        aboutTextView.text = HtmlCompat.fromHtml(getString(R.string.about_description), HtmlCompat.FROM_HTML_MODE_COMPACT)
        aboutTextView.movementMethod = LinkMovementMethod.getInstance()

        // Set onClickListeners for the menu and change icons
        val sharedPreferences = getSharedPreferences("AppThemePrefs", MODE_PRIVATE)
        var isDarkTheme = sharedPreferences.getBoolean("isDarkTheme", false)

// Set the initial theme when the activity starts
        if (isDarkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        menu.setOnClickListener {
            // Create a PopupMenu
            val popupMenu = PopupMenu(this, menu)

            // Inflate the menu from the resource file
            popupMenu.menuInflater.inflate(R.menu.menu_options, popupMenu.menu)

            // Set a click listener for each menu item
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.history -> {
                        val intent = Intent(this, HistoryActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    R.id.about -> {
                        val intent = Intent(this, AboutActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    R.id.home->{
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    else -> false
                }
            }

            // Show the popup menu
            popupMenu.show()
        }
        changetheme.setOnClickListener {
            // Toggle the theme
            isDarkTheme = !isDarkTheme

            // Save the new theme setting in SharedPreferences
            with(sharedPreferences.edit()) {
                putBoolean("isDarkTheme", isDarkTheme)
                apply()
            }

            // Apply the new theme
            if (isDarkTheme) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

            // Recreate the activity to reflect the theme change
            recreate()
        }
    }
}