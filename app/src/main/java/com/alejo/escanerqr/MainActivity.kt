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
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        integrator.setPrompt("Scan a QR Code")
        integrator.setCameraId(0)
        integrator.setBeepEnabled(false)
        integrator.setBarcodeImageEnabled(true)
        integrator.initiateScan()
    }
}