package com.alejo.escanerqr

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.alejo.escanerqr.databinding.ActivityMainBinding
import com.google.zxing.integration.android.IntentIntegrator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnScanner.setOnClickListener {
            // Mostrar el escaner en pantalla vertical
            requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

            // Iniciar el escáner automáticamente
            initScanner()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // Recuperar el resultado del escaneo del código QR en la variable result
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        // Si el resultado no es nulo y contiene datos
        if (result != null && result.contents != null) {
            // Mostrar el resultado en un Toast
            Toast.makeText(this, "El valor escaneado es: ${result.contents}", Toast.LENGTH_LONG).show()
        } else {
            // Si no se escaneó ningún código QR, mostrar un mensaje en un Toast
            Toast.makeText(this, "No se escaneó ningún código QR", Toast.LENGTH_LONG).show()
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun initScanner() {
        try {
            val integrator = IntentIntegrator(this)
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            integrator.setPrompt("Escaner de QR")
            integrator.setCameraId(0) // Usar la cámara trasera
            integrator.setBeepEnabled(false) // Desactivar el sonido
            integrator.setBarcodeImageEnabled(true) // Mostrar la imagen del código QR

            // Iniciar el escáner en modo vertical
            integrator.captureActivity = VerticalCaptureActivity::class.java

            // Iniciar el escáner
            integrator.initiateScan()
        } catch (e: Exception) {
            Toast.makeText(this, "Error al iniciar el escáner", Toast.LENGTH_LONG).show()
        }
    }
}