package com.bangkit.storyapp.view.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
            viewModel.login(email, pass)
        }

        binding.registerButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        viewModel.isError.observe(this) {
            if (it) {
                showError(it, this@LoginActivity, "Login error, please try again")
            } else {
                Toast.makeText(this@LoginActivity, "Login successful", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        viewModel.isLoading.observe(this) {
            showLoading(it, binding.progressBar)
        }

        setContentView(binding.root)
        playAnimation()
    }

    private fun playAnimation() {
        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(500)
        val emailText = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(500)
        val emailEdit = ObjectAnimator.ofFloat(binding.emailEditText, View.ALPHA, 1f).setDuration(500)
        val passText = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(500)
        val passEdit = ObjectAnimator.ofFloat(binding.passwordEditText, View.ALPHA, 1f).setDuration(500)
        val loginButton = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(500)
        val registerText = ObjectAnimator.ofFloat(binding.registerTextView, View.ALPHA, 1f).setDuration(500)
        val registerButton = ObjectAnimator.ofFloat(binding.registerButton, View.ALPHA, 1f).setDuration(500)

        val email = AnimatorSet().apply {
            playTogether(emailText, emailEdit)
        }

        val pass = AnimatorSet().apply {
            playTogether(passText, passEdit)
        }

        val register = AnimatorSet().apply {
            playTogether(registerText, registerButton)
        }

        AnimatorSet().apply {
            playSequentially(title, email, pass, loginButton, register)
            start()
        }
    }
}