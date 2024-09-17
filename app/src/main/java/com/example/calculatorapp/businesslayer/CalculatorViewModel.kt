package com.example.calculatorapp.businesslayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calculatorapp.dblayer.history
import com.example.calculatorapp.uilayer.HistoryActivity
import org.mozilla.javascript.Context
import org.mozilla.javascript.Scriptable


class CalculatorViewModel :  ViewModel() {

    private val historyManager : history = history()
    private val displaydata : MutableLiveData<String> = MutableLiveData()
    val display : LiveData<String> get() = displaydata
    private var currentexpression = ""

    fun onBtnClick(value : String){
        currentexpression+=value
        displaydata.value=currentexpression
    }
    fun evaluate(data: String): String {
        return try {
            // Enter the context for Rhino (JavaScript engine)
            val context = Context.enter()
            context.optimizationLevel = -1  // Disable compiler optimizations for compatibility
            val scriptable: Scriptable = context.initStandardObjects()

            if(data=="") return "0"
            // Evaluate the JavaScript code
            var finalResult = context.evaluateString(scriptable, data, "JavaScript", 1, null).toString()

            // If result ends with ".0", remove it (handle integers in decimal format)
            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "")
            }
            finalResult
        } catch (e: Exception) {
            // Return "Err" in case of any error during JavaScript evaluation
            "Err"
        } finally {
            Context.exit()  // Ensure context is exited to prevent memory leaks
        }
    }

    fun AllClear(){
        currentexpression=""
        displaydata.value=""
    }

    fun addSymbol(){
        onBtnClick("+")
    }
    fun subSymbol(){
        onBtnClick("-")
    }
    fun divSymbol(){
        onBtnClick("/")
    }
    fun mulSymbol(){
        onBtnClick("*")
    }
    fun equalto(context : android.content.Context){
        var result = evaluate(currentexpression)
        historyManager.writeHistoryToFile(context,currentexpression+"="+result)
        currentexpression = result
        displaydata.value=result
    }
    fun delete(){
        currentexpression = if (currentexpression.isNotEmpty()) currentexpression.dropLast(1) else currentexpression
        displaydata.value=currentexpression
    }

    fun readHistoryFromFile(historyActivity: HistoryActivity): CharSequence? {
        return historyManager.readHistoryFromFile(historyActivity)
    }

    fun deletehistory(historyActivity: HistoryActivity) {
        historyManager.deletehistory(historyActivity)
    }

}