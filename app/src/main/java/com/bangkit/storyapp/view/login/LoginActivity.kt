package com.bangkit.storyapp.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.bangkit.storyapp.R
import com.bangkit.storyapp.databinding.ActivityLoginBinding
import com.bangkit.storyapp.databinding.ActivityMainBinding
import com.bangkit.storyapp.view.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        binding.loginButton.isEnabled = binding.passwordEditText.length() >= 6

        binding.registerButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        setContentView(binding.root)
    }
}