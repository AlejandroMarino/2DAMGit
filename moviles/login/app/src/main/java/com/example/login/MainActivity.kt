package com.example.login

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

lateinit var usu: EditText
lateinit var contra: EditText
lateinit var btn: Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usu = this.findViewById(R.id.editextusu)
        contra = this.findViewById(R.id.editTexContra)
        btn = this.findViewById(R.id.button)

        btn.setOnClickListener {
            if (usu.text.toString() == getString(R.string.usuarioValido) && contra.text.toString() == getString(
                    R.string.contraseniaValida
                )
            ) {
                Toast.makeText(applicationContext, R.string.saludo, Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(applicationContext, R.string.error, Toast.LENGTH_LONG).show()
            }
        }
    }
}