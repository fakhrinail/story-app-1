package com.bangkit.storyapp.view.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bangkit.storyapp.data.Result
import com.bangkit.storyapp.databinding.ActivityRegisterBinding
import com.bangkit.storyapp.factory.ViewModelFactory
import com.bangkit.storyapp.util.showError
import com.bangkit.storyapp.view.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(this, ViewModelFactory(this))[RegisterViewModel::class.java]

        binding.registerButton.setOnClickListener {
            val name = binding.nameEditText.text?.trim().toString()
            val email = binding.emailEditText.text?.trim().toString()
            val pass = binding.passwordEditText.text?.trim().toString()

            viewModel.register(name, email, pass).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Result.Success -> {
                            binding.progressBar.visibility = View.GONE

                            Toast.makeText(this, "Register successful", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, LoginActivity::class.java))
                            finish()
                        }
                        is Result.Error -> {
                            binding.progressBar.visibility = View.GONE
                            showError(true, this, "Register error, please try again")
                        }
                    }
                }
            }
        }

        binding.loginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        setContentView(binding.root)
    }
}
