package com.example.visprog
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var textOperation: TextView
    private lateinit var textResult: TextView

    private var currentInput = ""
    private var operator = ""
    private var firstNumber = 0.0
    private var isNewOperation = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textOperation = findViewById(R.id.operation)
        textResult = findViewById(R.id.result)

        setupButtons()
    }

    private fun setupButtons() {
        val numberButtons = listOf(
            R.id.but_0 to "0", R.id.but_1 to "1", R.id.but_2 to "2",
            R.id.but_3 to "3", R.id.but_4 to "4", R.id.but_5 to "5",
            R.id.but_6 to "6", R.id.but_7 to "7", R.id.but_8 to "8",
            R.id.but_9 to "9", R.id.but_dot to "."


        )

        numberButtons.forEach { (id, value) ->
            findViewById<TextView>(id).setOnClickListener {
                addDigit(value)
            }
        }

        findViewById<TextView>(R.id.but_plus).setOnClickListener { setOperation("+") }
        findViewById<TextView>(R.id.but_minus).setOnClickListener { setOperation("-") }
        findViewById<TextView>(R.id.but_mult).setOnClickListener { setOperation("*") }
        findViewById<TextView>(R.id.but_division).setOnClickListener { setOperation("/") }

        findViewById<TextView>(R.id.but_equals).setOnClickListener { calculate() }
        findViewById<TextView>(R.id.but_clear).setOnClickListener { clear() }
        findViewById<TextView>(R.id.but_plus_minus).setOnClickListener { toggleSign() }
        findViewById<TextView>(R.id.but_percent).setOnClickListener { applyPercent() }
    }

    private fun addDigit(digit: String) {

        if (isNewOperation) {
            currentInput = ""
            isNewOperation = false
        }

        if (digit == "." && currentInput.contains(".")) return

        if (digit == "." && currentInput.isEmpty()) {
            currentInput = "0"
        }

        currentInput += digit
        updateDisplay()
    }

    private fun setOperation(op: String) {
        if (currentInput.isEmpty()) return

        if (operator.isNotEmpty() && !isNewOperation) {
            calculate()
        }

        firstNumber = currentInput.toDoubleOrNull() ?: 0.0
        operator = op
        isNewOperation = true

        textOperation.text = "${formatNumber(firstNumber)} $operator"
    }

    private fun calculate() {
        if (operator.isEmpty() || currentInput.isEmpty()) return

        val secondNumber = currentInput.toDoubleOrNull() ?: return

        val result = when (operator) {
            "+" -> firstNumber + secondNumber
            "-" -> firstNumber - secondNumber
            "*" -> firstNumber * secondNumber
            "/" -> {
                if (secondNumber == 0.0) {
                    textResult.text = "Ошибка"
                    clear()
                    return
                }
                firstNumber / secondNumber
            }
            else -> return
        }

        textOperation.text = "${formatNumber(firstNumber)} $operator ${formatNumber(secondNumber)}"
        textResult.text = formatNumber(result)

        currentInput = result.toString()
        operator = ""
        isNewOperation = true
    }

    private fun toggleSign() {
        if (currentInput.isEmpty() || currentInput == "0") return

        currentInput = if (currentInput.startsWith("-")) {
            currentInput.substring(1)
        } else {
            "-$currentInput"
        }
        updateDisplay()
    }

    private fun applyPercent() {
        if (currentInput.isEmpty()) return

        val number = currentInput.toDoubleOrNull() ?: return
        currentInput = (number / 100).toString()
        updateDisplay()
    }

    private fun clear() {
        currentInput = ""
        operator = ""
        firstNumber = 0.0
        isNewOperation = true
        textOperation.text = ""
        textResult.text = ""
    }

    private fun updateDisplay() {
        if (operator.isEmpty()) {
            textOperation.text = currentInput
        } else {
            textOperation.text = "${formatNumber(firstNumber)} $operator $currentInput"
        }
    }

    private fun formatNumber(number: Double): String {
        return if (number % 1.0 == 0.0) {
            number.toInt().toString()
        } else {
            number.toString().trimEnd('0').trimEnd('.')
        }
    }
}
