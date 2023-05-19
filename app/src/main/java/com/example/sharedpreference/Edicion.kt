package com.example.sharedpreference

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class Edicion : AppCompatActivity() {
    companion object {
        private const val EXTRA_NOMBRE = "extra_nombre"
        private const val EXTRA_CARRERA = "extra_carrera"
        private const val EXTRA_TELEFONO = "extra_telefono"
        lateinit var edtextNombre: EditText
        lateinit var textedCarrera: EditText
        lateinit var textedTelefono: EditText
        lateinit var btnEditarr: Button

        fun newIntent(context: Context, nombre: String, carrera: String, telefono: String): Intent {
            val intent = Intent(context, Edicion::class.java)
            intent.putExtra(EXTRA_NOMBRE, nombre)
            intent.putExtra(EXTRA_CARRERA, carrera)
            intent.putExtra(EXTRA_TELEFONO, telefono)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edicion)
        val nombre = intent.getStringExtra(EXTRA_NOMBRE)
        val carrera = intent.getStringExtra(EXTRA_CARRERA)
        val telefono = intent.getStringExtra(EXTRA_TELEFONO)
        edtextNombre = findViewById(R.id.edtextNombre)
        textedCarrera = findViewById(R.id.textedCarrera)
        textedTelefono = findViewById(R.id.textedTelefono)
        btnEditarr = findViewById(R.id.btnEditar)

        edtextNombre.setText(nombre)
        textedCarrera.setText(carrera)
        textedTelefono.setText(telefono)

        btnEditarr.setOnClickListener {
            val nuevoNombre = edtextNombre.text.toString()
            val nuevaCarrera = textedCarrera.text.toString()
            val nuevoTelefono = textedTelefono.text.toString()

            val intent = Intent()
            intent.putExtra(EXTRA_NOMBRE, nuevoNombre)
            intent.putExtra(EXTRA_CARRERA, nuevaCarrera)
            intent.putExtra(EXTRA_TELEFONO, nuevoTelefono)
            setResult(Activity.RESULT_OK, intent)
            saveData(nuevoNombre, nuevaCarrera, nuevoTelefono)
            finish()
        }
    }

    private fun saveData(nombre: String, carrera: String, telefono: String) {
        SharedPrefManager.saveData(this, nombre, carrera, telefono)
    }
}
