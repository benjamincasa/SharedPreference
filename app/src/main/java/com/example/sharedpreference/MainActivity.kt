package com.example.sharedpreference

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    private var name = "Benjamin Castaneda IV"
    private var career = "Informatica"
    private var phone = "3318085556"
    lateinit var txtNombre: TextView
    lateinit var txtCarrera: TextView
    lateinit var txtCelular: TextView
    lateinit var btnEdit: Button

    companion object {
        private const val REQUEST_CODE_EDIT = 1
    }

    private lateinit var editLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtCarrera = findViewById(R.id.txtCarrera)
        txtCelular = findViewById(R.id.txtCelular)
        txtNombre = findViewById(R.id.txtNombre)
        btnEdit = findViewById(R.id.btnEdit)

        loadData()

        btnEdit.setOnClickListener {
            abrirEdicion()
        }

        editLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                    val nuevoNombre = result.data!!.getStringExtra("extra_nombre")
                    val nuevaCarrera = result.data!!.getStringExtra("extra_carrera")
                    val nuevoTelefono = result.data!!.getStringExtra("extra_telefono")

                    if (!nuevoNombre.isNullOrEmpty()) {
                        name = nuevoNombre
                    }

                    if (!nuevaCarrera.isNullOrEmpty()) {
                        career = nuevaCarrera
                    }

                    if (!nuevoTelefono.isNullOrEmpty()) {
                        phone = nuevoTelefono
                    }

                    displayUserData()
                }
            }
    }

    private fun loadData() {
        val data = SharedPrefManager.loadData(this)
        name = data.first ?: name
        career = data.second ?: career
        phone = data.third ?: phone
        displayUserData()
    }

    private fun displayUserData() {
        txtNombre.text = name
        txtCarrera.text = career
        txtCelular.text = phone


    }

    private fun abrirEdicion() {
        val intent = Edicion.newIntent(this, name, career, phone)
        editLauncher.launch(intent)
    }

    override fun onStop() {
        super.onStop()
        saveData()
    }

    private fun saveData() {
        SharedPrefManager.saveData(this, name, career, phone)
    }
}