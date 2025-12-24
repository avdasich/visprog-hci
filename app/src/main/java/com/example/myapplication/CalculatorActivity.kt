package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CalculatorActivity : AppCompatActivity() {
    private var currentInput = ""
    private var operator = ""
    private var firstValue = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnBackToMenu: Button = findViewById(R.id.btnBackToMenu)
        btnBackToMenu.setOnClickListener {
            finish()
        }

        val tvResult: TextView = findViewById(R.id.tvResult)
        val tvInput: TextView = findViewById(R.id.tvInput)

        val btn0: Button = findViewById(R.id.btn0)
        val btn1: Button = findViewById(R.id.btn1)
        val btn2: Button = findViewById(R.id.btn2)
        val btn3: Button = findViewById(R.id.btn3)
        val btn4: Button = findViewById(R.id.btn4)
        val btn5: Button = findViewById(R.id.btn5)
        val btn6: Button = findViewById(R.id.btn6)
        val btn7: Button = findViewById(R.id.btn7)
        val btn8: Button = findViewById(R.id.btn8)
        val btn9: Button = findViewById(R.id.btn9)
        val btnA: Button = findViewById(R.id.btnA)

        val btnPlus: Button = findViewById(R.id.btnPlus)
        val btnMinus: Button = findViewById(R.id.btnMinus)
        val btnMultiply: Button = findViewById(R.id.btnMultiply)
        val btnDivide: Button = findViewById(R.id.btnDivide)
        val btnClear: Button = findViewById(R.id.btnClear)
        val btnDot: Button = findViewById(R.id.btnDot)
        val btnPlusMinus: Button = findViewById(R.id.btnPlusMinus)
        val btnPercent: Button = findViewById(R.id.btnPercent)
        val btnEquals: Button = findViewById(R.id.btnEquals)


        btn0.setOnClickListener {
            currentInput += "0"
            tvResult.text = currentInput
        }

        btn1.setOnClickListener {
            currentInput += "1"
            tvResult.text = currentInput
        }

        btn2.setOnClickListener {
            currentInput += "2"
            tvResult.text = currentInput
        }

        btn3.setOnClickListener {
            currentInput += "3"
            tvResult.text = currentInput
        }

        btn4.setOnClickListener {
            currentInput += "4"
            tvResult.text = currentInput
        }

        btn5.setOnClickListener {
            currentInput += "5"
            tvResult.text = currentInput
        }

        btn6.setOnClickListener {
            currentInput += "6"
            tvResult.text = currentInput
        }

        btn7.setOnClickListener {
            currentInput += "7"
            tvResult.text = currentInput
        }

        btn8.setOnClickListener {
            currentInput += "8"
            tvResult.text = currentInput
        }

        btn9.setOnClickListener {
            currentInput += "9"
            tvResult.text = currentInput
        }

        btnDot.setOnClickListener {
            if (!currentInput.contains(".")) {
                currentInput += "."
                tvResult.text = currentInput
            }
        }

        btnPlus.setOnClickListener {
            firstValue = currentInput.toDoubleOrNull() ?: 0.0
            operator = "+"
            tvInput.text = "$firstValue +"
            currentInput = ""
        }

        btnMinus.setOnClickListener {
            firstValue = currentInput.toDoubleOrNull() ?: 0.0
            operator = "-"
            tvInput.text = "$firstValue -"
            currentInput = ""
        }

        btnMultiply.setOnClickListener {
            firstValue = currentInput.toDoubleOrNull() ?: 0.0
            operator = "*"
            tvInput.text = "$firstValue *"
            currentInput = ""
        }

        btnDivide.setOnClickListener {
            firstValue = currentInput.toDoubleOrNull() ?: 0.0
            operator = "/"
            tvInput.text = "$firstValue /"
            currentInput = ""
        }

        btnPercent.setOnClickListener {
            val value = currentInput.toDoubleOrNull() ?: 0.0
            val result = value / 100.0
            currentInput = result.toString()
            tvResult.text = currentInput
        }

        btnPlusMinus.setOnClickListener {
            val value = currentInput.toDoubleOrNull() ?: 0.0
            val result = value * -1
            currentInput = result.toString()
            tvResult.text = currentInput
        }

        btnEquals.setOnClickListener {
            val secondValue = currentInput.toDoubleOrNull() ?: 0.0
            val result = when(operator) {
                "+" -> firstValue + secondValue
                "-" -> firstValue - secondValue
                "*" -> firstValue * secondValue
                "/" -> {
                    if (secondValue != 0.0) {
                        firstValue / secondValue
                    } else {
                        0.0
                    }
                }
                else -> secondValue
            }

            tvResult.text = result.toString()
            tvInput.text = ""
            currentInput = result.toString()
            operator = ""
        }

        btnClear.setOnClickListener {
            currentInput = ""
            firstValue = 0.0
            operator = ""
            tvResult.text = "0"
            tvInput.text = ""
        }
    }
}