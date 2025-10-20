package com.example.visprog_hci

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var inputText: TextView
    private lateinit var resultText: TextView
    private var firstNumber = ""
    private var secondNumber = ""
    private var operation = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputText = findViewById(R.id.math_operation)
        resultText = findViewById(R.id.result_text)

        val numberButtons = listOf(
            R.id.but_0, R.id.but_1, R.id.but_2, R.id.but_3,
            R.id.but_4, R.id.but_5, R.id.but_6, R.id.but_7,
            R.id.but_8, R.id.but_9, R.id.but_dot
        )

        for (id in numberButtons) {
            findViewById<TextView>(id).setOnClickListener(this)
        }

        findViewById<TextView>(R.id.but_plus).setOnClickListener { setOperation("+") }
        findViewById<TextView>(R.id.but_minus).setOnClickListener { setOperation("-") }
        findViewById<TextView>(R.id.but_mult).setOnClickListener { setOperation("*") }
        findViewById<TextView>(R.id.but_division).setOnClickListener { setOperation("/") }


        findViewById<TextView>(R.id.but_clear).setOnClickListener {
            firstNumber = ""
            secondNumber = ""
            operation = ""
            inputText.text = ""
            resultText.text = ""
        }

        findViewById<TextView>(R.id.but_equals).setOnClickListener {
            calculate()
        }
    }

    override fun onClick(view: View?) {
        val button = view as TextView
        if (operation.isEmpty()) {
            firstNumber += button.text
            inputText.text = firstNumber
        } else {
            secondNumber += button.text
            inputText.text = "$firstNumber $operation $secondNumber"
        }
    }

    private fun setOperation(op: String) {
        if (firstNumber.isNotEmpty() && operation.isEmpty()) {
            operation = op
            inputText.text = "$firstNumber $operation"
        }
    }

    private fun calculate() {
        if (firstNumber.isEmpty() || secondNumber.isEmpty() || operation.isEmpty()) return

        val num1 = firstNumber.toDouble()
        val num2 = secondNumber.toDouble()
        val result = when (operation) {
            "+" -> num1 + num2
            "-" -> num1 - num2
            "*" -> num1 * num2
            "/" -> if (num2 != 0.0) num1 / num2 else Double.NaN
            else -> 0.0
        }

        resultText.text = result.toString()
        firstNumber = result.toString()
        secondNumber = ""
        operation = ""
    }
}
