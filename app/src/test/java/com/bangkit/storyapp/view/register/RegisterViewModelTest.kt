package com.bangkit.storyapp.view.register

import android.app.Application
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RegisterViewModelTest{
    @Mock
    private lateinit var  registerViewModel: RegisterViewModel

    @Before
    fun setUp(){
        registerViewModel = RegisterViewModel(Application())
    }
}