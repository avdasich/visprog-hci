package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnCalc = findViewById<Button>(R.id.btnGoToCalculator)
        val btnPlayer = findViewById<Button>(R.id.btnGoToMediaPlayer)
        val btnLocation = findViewById<Button>(R.id.btnGoToLocation)
        val btnSockets = findViewById<Button>(R.id.btnGoToSockets)

        btnCalc.setOnClickListener {
            val i = Intent(this, CalculatorActivity::class.java)
            startActivity(i)
        }

        btnPlayer.setOnClickListener {
            val i = Intent(this, MediaPlayerActivity::class.java)
            startActivity(i)
        }

        btnLocation.setOnClickListener {
            val i = Intent(this, LocationActivity::class.java)
            startActivity(i)
        }

        btnSockets.setOnClickListener {
            val i = Intent(this, SocketsZmqActivity::class.java)
            startActivity(i)
        }

    }
}