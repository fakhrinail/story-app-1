package com.bangkit.storyapp.view.login

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.bangkit.storyapp.R
import com.bangkit.storyapp.databinding.ActivityLoginBinding
import com.bangkit.storyapp.databinding.ActivityMainBinding
import com.bangkit.storyapp.util.showError
import com.bangkit.storyapp.util.showLoading
import com.bangkit.storyapp.view.register.RegisterActivity
import org.w3c.dom.Text

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val pass = binding.passwordEditText.text.toString()
            viewModel.login(email, pass)
        }

        binding.registerButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        viewModel.isError.observe(this) {
            showError(it, this@LoginActivity, "Login error, please try again")
        }

        viewModel.isLoading.observe(this) {
            showLoading(it, binding.progressBar)
        }

        setContentView(binding.root)
    }
}