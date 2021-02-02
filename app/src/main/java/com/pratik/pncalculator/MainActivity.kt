package com.pratik.pncalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private var lastNumeric: Boolean = false
    private var lastDot: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onDigitClick(view: View) {
        tvEnteredNumber.append((view as Button).text)
        lastNumeric = true
    }

    fun onClearClick(view: View) {
        tvEnteredNumber.text = ""
        lastDot = false
        lastNumeric = false
    }

    fun onDecimalPointClick(view: View) {
        if (lastNumeric && !lastDot) {
            tvEnteredNumber.append(".")
            lastDot = true
            lastNumeric = false
        }
    }

    private fun isOperatorAdded(enteredValue: String): Boolean {
        return if (enteredValue.startsWith("-"))
            false
        else {
            enteredValue.contains("/") || enteredValue.contains("*") || enteredValue.contains("+") || enteredValue.contains(
                "-"
            )
            false
        }
    }

    fun onOperatorClick(view: View) {
        if (lastNumeric && !isOperatorAdded(tvEnteredNumber.text.toString())) {
            tvEnteredNumber.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }

    fun onEqualClick(view: View) {
        if (lastNumeric) {
            var enteredValue: String = tvEnteredNumber.text.toString()
            var prefix: String = ""
            if (enteredValue.startsWith("-")) {
                prefix = "-"
                enteredValue = enteredValue.substring(1)
            }

            try {
                when {
                    enteredValue.contains("-") -> {
                        val splitValue: List<String> = enteredValue.split("-")
                        var first : String = splitValue[0]
                        var second: String = splitValue[1]
                        if (prefix.isNotEmpty()) {
                            first = prefix + first
                        }
                        tvEnteredNumber.text = removeZeroAtLast((first.toDouble() - second.toDouble()).toString())

                    }
                    enteredValue.contains("+") -> {
                        val splitValue: List<String> = enteredValue.split("+")
                        var first : String = splitValue[0]
                        var second: String = splitValue[1]
                        if (prefix.isNotEmpty()) {
                            first = prefix + first
                        }
                        tvEnteredNumber.text = removeZeroAtLast((first.toDouble() + second.toDouble()).toString())

                    }
                    enteredValue.contains("*") -> {
                        val splitValue: List<String> = enteredValue.split("*")
                        var first : String = splitValue[0]
                        var second: String = splitValue[1]
                        if (prefix.isNotEmpty()) {
                            first = prefix + first
                        }
                        tvEnteredNumber.text = removeZeroAtLast((first.toDouble() * second.toDouble()).toString())

                    }
                    else -> {
                        val splitValue: List<String> = enteredValue.split("/")
                        var first : String = splitValue[0]
                        var second: String = splitValue[1]
                        if (prefix.isNotEmpty()) {
                            first = prefix + first
                        }
                        tvEnteredNumber.text = removeZeroAtLast((first.toDouble() / second.toDouble()).toString())
                    }
                }
            } catch (e: ArithmeticException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun  removeZeroAtLast(result: String) : String{
        var value = result;
        if (result.contains(".0"))
            value = result.substring(0 , result.length - 2)
        return value
    }
}