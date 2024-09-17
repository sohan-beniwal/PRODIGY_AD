package com.example.calculatorapp.dblayer

import android.content.Context
import java.io.File
import java.io.FileOutputStream

class history {

    fun writeHistoryToFile(context: Context, history: String) {
        val fileName = "calculator_history.txt"
        val fileOutputStream: FileOutputStream

        try {
            fileOutputStream = context.openFileOutput(fileName, Context.MODE_APPEND)
            fileOutputStream.write((history + "\n").toByteArray())
            fileOutputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun readHistoryFromFile(context: Context): String {
        val fileName = "calculator_history.txt"
        val file = File(context.filesDir, fileName)
        var fileContents = ""

        if (file.exists()) {
            fileContents = file.readText()
        }
        return fileContents
    }

    fun deletehistory(context : Context) : Boolean{
        val fileName = "calculator_history.txt"
        val file = File(context.filesDir, fileName)

        return if (file.exists()) {
            file.writeText("")  // Overwrite the file with an empty string
            true  // Returns true when the file is cleared
        } else {
            false  // File didn't exist
        }
    }
}