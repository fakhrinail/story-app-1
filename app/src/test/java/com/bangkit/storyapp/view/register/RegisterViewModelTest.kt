package com.bangkit.storyapp.view.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.bangkit.storyapp.data.Result
import com.bangkit.storyapp.model.ApiResponse
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
class RegisterViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var registerViewModel: RegisterViewModel

    private var dummyName: String? = null
    private var dummyEmail: String? = null
    private var dummyPassword: String? = null
    private val dummySuccessResponse = DataDummy.generateDummySuccessResponseModel()
    private val dummyFailedResponse = DataDummy.generateDummyFailedResponseModel()

    @Test
    fun `when Register Input Should Not Null and Success`() {
        dummyName = "Cristiano Ronaldo"
        dummyEmail = "test@gmail.com"
        dummyPassword = "test123"

        val expectedResponse = MutableLiveData<Result<ApiResponse>>()
        expectedResponse.value = Result.Success(dummySuccessResponse)

        Mockito.`when`(registerViewModel.register(dummyName!!, dummyEmail!!, dummyPassword!!))
            .thenReturn(expectedResponse)

        val actualResponse =
            registerViewModel.register(dummyName!!, dummyEmail!!, dummyPassword!!).getOrAwaitValue()

        Assert.assertNotNull(actualResponse)
        Assert.assertTrue(actualResponse is Result.Success)
        Assert.assertEquals(
            dummySuccessResponse.error,
            (actualResponse as Result.Success).data.error
        )
        Assert.assertEquals(dummySuccessResponse.message, (actualResponse).data.message)
    }

    @Test
    fun `when Register Input Invalid and Failed`() {
        dummyName = "Cristiano Ronaldo"
        dummyEmail = "test@gmail.com"
        dummyPassword = "test123"

        val expectedResponse = MutableLiveData<Result<ApiResponse>>()
        expectedResponse.value = dummyFailedResponse.message?.let { Result.Error(it) }

        Mockito.`when`(registerViewModel.register(dummyName!!, dummyEmail!!, dummyPassword!!))
            .thenReturn(expectedResponse)

        val actualResponse =
            registerViewModel.register(dummyName!!, dummyEmail!!, dummyPassword!!).getOrAwaitValue()

        Assert.assertNotNull(actualResponse)
        Assert.assertTrue(actualResponse is Result.Error)
        Assert.assertEquals(dummyFailedResponse.message, (actualResponse as Result.Error).error)
    }

    @Test(expected = NullPointerException::class)
    fun `when Register Input Null and Exception Thrown`() {
        Mockito.`when`(registerViewModel.register(dummyName!!, dummyEmail!!, dummyPassword!!))
            .thenThrow(NullPointerException())

        registerViewModel.register(dummyName!!, dummyEmail!!, dummyPassword!!).getOrAwaitValue()
    }
}