package com.bangkit.storyapp.view.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bangkit.storyapp.data.Result
import com.bangkit.storyapp.databinding.ActivityLoginBinding
import com.bangkit.storyapp.factory.ViewModelFactory
import com.bangkit.storyapp.pref.UserPreference
import com.bangkit.storyapp.util.showError
import com.bangkit.storyapp.view.main.MainActivity
import com.bangkit.storyapp.view.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(this, ViewModelFactory(this))[LoginViewModel::class.java]

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text?.trim().toString()
            val pass = binding.passwordEditText.text?.trim().toString()

            viewModel.login(email, pass).observe(this) { result ->
                if(result != null) {
                    when (result) {
                        is Result.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Result.Success -> {
                            binding.progressBar.visibility = View.GONE

                            val pref = UserPreference(applicationContext)
                            pref.setUser(result.data)

                            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                        is Result.Error -> {
                            binding.progressBar.visibility = View.GONE
                            showError(true, this, "Login error, please try again")
                        }
                    }
                }
            }
        }

        binding.registerButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
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