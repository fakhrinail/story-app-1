package com.bangkit.storyapp.view.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.bangkit.storyapp.data.Result
import com.bangkit.storyapp.model.user.UserModel
import com.bangkit.storyapp.util.DataDummy
import com.bangkit.storyapp.util.getOrAwaitValue
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var loginViewModel: LoginViewModel

    private var dummyEmail: String? = null
    private var dummyPassword: String? = null
    private val dummyUser = DataDummy.generateDummyUserModel()
    private val dummyFailedResponse = DataDummy.generateDummyFailedResponseModel()

    @Test
    fun `when Login Input Should Not Null and Success`() {
        dummyEmail = "test@gmail.com"
        dummyPassword = "test123"

        val expectedUser = MutableLiveData<Result<UserModel>>()
        expectedUser.value = Result.Success(dummyUser)

        Mockito.`when`(loginViewModel.login(dummyEmail!!, dummyPassword!!)).thenReturn(expectedUser)

        val actualUser = loginViewModel.login(dummyEmail!!, dummyPassword!!).getOrAwaitValue()

        Assert.assertNotNull(actualUser)
        Assert.assertTrue(actualUser is Result.Success)
        Assert.assertEquals(dummyUser.userId, (actualUser as Result.Success).data.userId)
    }

    @Test
    fun `when Login Input Invalid and Failed`() {
        dummyEmail = "test@gmail.com"
        dummyPassword = "test123"

        val expectedResponse = MutableLiveData<Result<UserModel>>()
        expectedResponse.value = dummyFailedResponse.message?.let { Result.Error(it) }

        Mockito.`when`(loginViewModel.login(dummyEmail!!, dummyPassword!!))
            .thenReturn(expectedResponse)

        val actualResponse = loginViewModel.login(dummyEmail!!, dummyPassword!!).getOrAwaitValue()

        Assert.assertNotNull(actualResponse)
        Assert.assertTrue(actualResponse is Result.Error)
        Assert.assertEquals(dummyFailedResponse.message, (actualResponse as Result.Error).error)
    }

    @Test(expected = NullPointerException::class)
    fun `when Login Input Null and Exception Thrown`() {
        Mockito.`when`(loginViewModel.login(dummyEmail!!, dummyPassword!!))
            .thenThrow(NullPointerException())

        loginViewModel.login(dummyEmail!!, dummyPassword!!).getOrAwaitValue()
    }
}