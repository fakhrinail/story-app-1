package com.bangkit.storyapp.view.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bangkit.storyapp.databinding.ActivityRegisterBinding
import com.bangkit.storyapp.util.showError
import com.bangkit.storyapp.util.showLoading
import com.bangkit.storyapp.view.login.LoginActivity
import com.bangkit.storyapp.view.login.LoginViewModel
import com.bangkit.storyapp.view.main.MainActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val loginViewModel by viewModels<LoginViewModel>()
    private val registerViewModel by viewModels<RegisterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)

        registerViewModel.isError.observe(this) {
            if (it) {
                showError(it, this@RegisterActivity, "Register error, please try again")
            } else {
                val email = binding.emailEditText.text?.trim().toString()
                val pass = binding.passwordEditText.text?.trim().toString()

                loginViewModel.login(email, pass)
            }
        }

        loginViewModel.isError.observe(this) {
            if (it) {
                showError(it, this@RegisterActivity, "Login error, please try again")
            } else {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        registerViewModel.isLoading.observe(this) {
            showLoading(it, binding.progressBar)
        }

        loginViewModel.isLoading.observe(this) {
            showLoading(it, binding.progressBar)
        }

        binding.registerButton.setOnClickListener {
            val name = binding.nameEditText.text?.trim().toString()
            val email = binding.emailEditText.text?.trim().toString()
            val pass = binding.passwordEditText.text?.trim().toString()

            registerViewModel.register(name, email, pass)
        }

        binding.loginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        setContentView(binding.root)
    }
}
