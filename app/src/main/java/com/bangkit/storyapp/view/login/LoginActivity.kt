package com.bangkit.storyapp.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bangkit.storyapp.databinding.ActivityLoginBinding
import com.bangkit.storyapp.util.showError
import com.bangkit.storyapp.util.showLoading
import com.bangkit.storyapp.view.main.MainActivity
import com.bangkit.storyapp.view.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text?.trim().toString()
            val pass = binding.passwordEditText.text?.trim().toString()
            viewModel.login(applicationContext, email, pass)
        }

        binding.registerButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        viewModel.isError.observe(this) {
            if (it) {
                showError(it, this@LoginActivity, "Login error, please try again")
            } else {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        viewModel.isLoading.observe(this) {
            showLoading(it, binding.progressBar)
        }

        setContentView(binding.root)
    }
}