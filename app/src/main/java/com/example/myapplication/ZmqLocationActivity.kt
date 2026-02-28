package com.example.myapplication

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import org.zeromq.SocketType
import org.zeromq.ZContext
import org.zeromq.ZMQ

class ZmqLocationActivity : AppCompatActivity(), LocationListener {

    companion object {
        private const val PERMISSION_REQUEST_LOCATION = 100
    }

    private lateinit var locationManager: LocationManager
    private lateinit var etServerIp: EditText
    private lateinit var tvLog: TextView
    private var serverIp: String = ""
    private var isRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zmq_location)

        etServerIp = findViewById(R.id.etServerIp)
        tvLog = findViewById(R.id.tvLog)

        findViewById<Button>(R.id.btnStart).setOnClickListener {
            serverIp = etServerIp.text.toString().trim()
            if (serverIp.isEmpty()) {
                Toast.makeText(this, "Введите IP сервера", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            isRunning = true
            requestLocationPermissions()
        }

        findViewById<Button>(R.id.btnStop).setOnClickListener {
            isRunning = false
            locationManager.removeUpdates(this)
            tvLog.text = "Остановлено"
        }

        findViewById<Button>(R.id.btnBack).setOnClickListener {
            finish()
        }

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    private fun requestLocationPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                PERMISSION_REQUEST_LOCATION
            )
        }
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) return

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000L, 1f, this)
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000L, 1f, this)
        tvLog.text = "Ожидаем геолокацию..."
    }

    override fun onLocationChanged(location: Location) {
        if (!isRunning) return

        val json = """{"latitude":${location.latitude},"longitude":${location.longitude},"altitude":${location.altitude}}"""

        runOnUiThread {
            tvLog.text = "Отправляем:\n$json"
        }

        sendToServer(json)
    }

    private fun sendToServer(json: String) {
        Thread {
            val endpoint = "tcp://$serverIp:2222"
            try {
                ZContext().use { ctx ->
                    val sock = ctx.createSocket(SocketType.REQ)
                    sock.setReceiveTimeOut(3000)
                    sock.connect(endpoint)
                    sock.send(json.toByteArray(ZMQ.CHARSET), 0)
                    val reply = String(sock.recv(0), ZMQ.CHARSET)

                    runOnUiThread {
                        tvLog.text = "✓ Отправлено:\n$json\nОтвет сервера: $reply"
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    tvLog.text = "⚠ Ошибка: ${e.message}\nПереподключение через 2 сек..."
                }
                Thread.sleep(2000)
                if (isRunning) sendToServer(json)
            }
        }.start()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_LOCATION &&
            grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
            startLocationUpdates()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
        locationManager.removeUpdates(this)
    }
}