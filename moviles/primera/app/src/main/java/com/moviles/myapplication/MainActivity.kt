package com.moviles.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    lateinit var tx: TextView
    lateinit var bt: Button
    lateinit var txoculto: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tx = this.findViewById(R.id.texto)
        bt = this.findViewById(R.id.button)
        txoculto = this.findViewById(R.id.textoculto)

        bt.setOnClickListener {
            tx.text = "Gracias por tus Datos"
            bt.visibility = View.INVISIBLE
            txoculto.visibility = View.VISIBLE
        }
    }
}