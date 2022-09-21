package com.example.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

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
            if (usu.text.toString() == "vini" && contra.text.toString() == "gol"){
                Toast.makeText(applicationContext, R.string.saludo, Toast.LENGTH_LONG).show()
            }else {
                Toast.makeText(applicationContext, R.string.error, Toast.LENGTH_LONG).show()
            }
        }
    }
}