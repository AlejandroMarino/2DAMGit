package com.moviles.f1app.ui.pantalla.user

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.moviles.f1app.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}