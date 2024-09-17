package com.example.calculatorapp.uilayer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.example.calculatorapp.R
import com.example.calculatorapp.businesslayer.CalculatorViewModel

class MainActivity : AppCompatActivity() {
    private val viewmodal : CalculatorViewModel = CalculatorViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val customToolbar: Toolbar = findViewById(R.id.app_bar)
        setSupportActionBar(customToolbar)

        val menu:ImageView = findViewById(R.id.menu)
        val displaytextview: TextView = findViewById(R.id.textview)
        val changetheme: ImageView = findViewById(R.id.change)
        val sharedPreferences = getSharedPreferences("AppThemePrefs", MODE_PRIVATE)

// Load the current theme setting from SharedPreferences
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

        viewmodal.display.observe(this, Observer { displaytext->
            displaytextview.text = displaytext
        })

        val allclear: TextView = findViewById(R.id.allclear)

        allclear.setOnClickListener{
            viewmodal.AllClear()
        }

        val one: TextView = findViewById(R.id.one)
        one.setOnClickListener{
            viewmodal.onBtnClick("1")
        }
        val two: TextView = findViewById(R.id.two)
        two.setOnClickListener{
            viewmodal.onBtnClick("2")
        }
        val three: TextView = findViewById(R.id.three)
        three.setOnClickListener{
            viewmodal.onBtnClick("3")
        }
        val four: TextView = findViewById(R.id.four)
        four.setOnClickListener{
            viewmodal.onBtnClick("4")
        }
        val five: TextView = findViewById(R.id.five)
        five.setOnClickListener{
            viewmodal.onBtnClick("5")
        }
        val six: TextView = findViewById(R.id.six)
        six.setOnClickListener{
            viewmodal.onBtnClick("6")
        }
        val seven: TextView = findViewById(R.id.seven)
        seven.setOnClickListener{
            viewmodal.onBtnClick("7")
        }
        val eight: TextView = findViewById(R.id.eight)
        eight.setOnClickListener{
            viewmodal.onBtnClick("8")
        }
        val nine: TextView = findViewById(R.id.nine)
        nine.setOnClickListener{
            viewmodal.onBtnClick("9")
        }
        val zero: TextView = findViewById(R.id.zero)
        zero.setOnClickListener{
            viewmodal.onBtnClick("0")
        }
        val add: TextView = findViewById(R.id.plus)
        add.setOnClickListener{
            viewmodal.addSymbol()
        }
        val sub: TextView = findViewById(R.id.minus)
        sub.setOnClickListener{
            viewmodal.subSymbol()
        }
        val div: TextView = findViewById(R.id.divide)
        div.setOnClickListener{
            viewmodal.divSymbol()
        }
        val mul: TextView = findViewById(R.id.multiply)
        mul.setOnClickListener{
            viewmodal.mulSymbol()
        }
        val equalto: TextView = findViewById(R.id.equalto)
        equalto.setOnClickListener{
            viewmodal.equalto(this)
        }
        val dot:TextView = findViewById(R.id.point)
        dot.setOnClickListener{
            viewmodal.onBtnClick(".")
        }
        val delete:TextView = findViewById(R.id.delete)
        delete.setOnClickListener{
            viewmodal.delete()
        }
    }
}