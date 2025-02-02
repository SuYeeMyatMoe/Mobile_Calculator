package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private val expression = StringBuilder()// Holds the ongoing expression as a StringBuilder.
    private lateinit var txtResult: TextView// The TextView for displaying the expression and results.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        txtResult = findViewById<TextView>(R.id.txtResult)// Link the TextView using the id from XML.

        // Digit buttons from 0 to 9 using the IDs defined in XML.
        val btn1 = findViewById<Button>(R.id.button)      // "1"
        val btn2 = findViewById<Button>(R.id.button2)     // "2"
        val btn3 = findViewById<Button>(R.id.button3)     // "3"
        val btn4 = findViewById<Button>(R.id.button4)     // "4"
        val btn5 = findViewById<Button>(R.id.button5)     // "5"
        val btn6 = findViewById<Button>(R.id.button6)     // "6"
        val btn7 = findViewById<Button>(R.id.button7)     // "7"
        val btn8 = findViewById<Button>(R.id.button8)     // "8"
        val btn9 = findViewById<Button>(R.id.button9)     // "9"
        val btn0 = findViewById<Button>(R.id.button10)    // "0"

        // Create a list for easier iteration over the digit buttons.
        val digitButtons = listOf(btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9)

        // Set click listeners for digit buttons.
        for (btn in digitButtons) {
            btn.setOnClickListener {
                expression.append(btn.text)
                updateResultView()
            }
        }

        // Operator buttons (using IDs from XML)
        val btnPlus = findViewById<Button>(R.id.btnPlus)
        val btnMinus = findViewById<Button>(R.id.btnMinus)
        val btnMultiply = findViewById<Button>(R.id.btnMultiply)
        val btnDivide = findViewById<Button>(R.id.button14)  // "/" operator button

        // List of operator buttons for easy iteration.
        val operatorButtons = listOf(btnPlus, btnMinus, btnMultiply, btnDivide)

        // Append operator to the expression ensuring proper spacing.
        for (btn in operatorButtons) {
            btn.setOnClickListener {
                // Avoid appending an operator if expression is empty or last character is already an operator.
                if (expression.isNotEmpty() && !"+-*/".contains(expression.last())) {
                    expression.append(" ${btn.text} ")
                    updateResultView()
                }
            }
        }

        // Equals button: when pressed, the expression is evaluated.
        val btnEqual = findViewById<Button>(R.id.button15)
        btnEqual.setOnClickListener {
            val result = evaluateExpression(expression.toString())
            expression.clear()
            expression.append(result)
            updateResultView()
        }
        // Clear button: clears the expression and result.
        val clear = findViewById<Button>(R.id.button12)
        clear.setOnClickListener {
            expression.clear()
            updateResultView() // This will update txtResult with an empty string.
        }

        // If needed, you can add a Clear button following similar patterns.
    }

    // Updates the txtResult TextView with the current expression.
    private fun updateResultView() {
        txtResult.text = expression.toString()
    }

    // Simple evaluator that splits the expression string on spaces.
    // Note: This evaluator processes operations left-to-right without operator precedence.
    private fun evaluateExpression(exp: String): String {
        return try {
            val tokens = exp.split(" ")
            if (tokens.isEmpty()) return ""
            // Start with the first number.
            var result = tokens[0].toDouble()

            var i = 1
            while (i < tokens.size) {
                val operator = tokens[i]
                val nextNumber = tokens[i + 1].toDouble()

                result = when (operator) {
                    "+" -> result + nextNumber
                    "-" -> result - nextNumber
                    "*" -> result * nextNumber
                    "/" -> result / nextNumber
                    else -> result
                }
                i += 2
            }
            // If the result has no decimal fraction, display as an integer.
            if (result % 1.0 == 0.0) result.toInt().toString()
            else result.toString()
        } catch (e: Exception) {
            "Error"
        }
    }

}