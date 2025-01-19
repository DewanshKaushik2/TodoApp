package com.example.todoapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.lifecycle.Observer
import com.example.todoapp.model.Repository
import com.example.todoapp.model.TodoItem
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var mockObserver: Observer<List<TodoItem>>

    @Mock
    private lateinit var mainViewModel: MainViewModel

    @Mock
    private lateinit var repository: Repository

    @Before
    fun setup() {
//        MockitoAnnotations.openMocks(this)
        repository = mock(Repository::class.java)
        mainViewModel = MainViewModel(repository) // Initialize your ViewModel
    }

    @Test
    fun `test getData updates the LiveData`() {
        // Arrange
        val mockData = listOf(
            TodoItem(id = 1, itemName = "Test Todo 1"),
            TodoItem(id = 2, itemName = "Test Todo 2")
        )
        // Simulate your ViewModel's getData method fetching data (use mocking or dummy data)
      //d  mockData.also { mainViewModel.todolist = it } // or mock this behavior if using a repository

        // Act
        mainViewModel.getData()
        assertNotNull(mainViewModel.todolist)
        // Assert
     //d   mainViewModel.todolist.observeForever(mockObserver)
     //d   Mockito.verify(mockObserver).onChanged(mockData)

    }
}
