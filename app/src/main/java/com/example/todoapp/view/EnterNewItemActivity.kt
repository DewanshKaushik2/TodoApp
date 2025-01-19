package com.example.todoapp.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.todoapp.Database.DatabaseClient
import com.example.todoapp.IndeterminateCircularIndicator
import com.example.todoapp.MainActivity
import com.example.todoapp.model.TodoItem
import com.example.todoapp.ui.theme.TodoAppTheme
import com.example.todoapp.viewmodel.EnterNewItemViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EnterNewItemActivity : ComponentActivity() {
    private val enterNewItemViewModel: EnterNewItemViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoAppTheme {
                FloatingBar()
            }

        }
    }

    @Composable
    fun FloatingBar() {
        var text by rememberSaveable { mutableStateOf("") }
        Column {
            Text(
                text = "Add To Do!",
                modifier = Modifier.padding(2.dp)
            )

            TextField(
                value = text,
                onValueChange = { newText -> text = newText },
                label = { Text("Enter text") },
                placeholder = { Text("Type here...") },
                shape = MaterialTheme.shapes.medium, // Rounded corners
                modifier = Modifier.fillMaxWidth(),
            )
            Button(
                onClick = { onclick(text) },
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Add Todo")
            }
        }
    }

    fun onclick(text: String) {
        val user = TodoItem()
        if (!text.trim().equals("Error",true)) {
            user.itemName = text;
            lifecycleScope.launch {
                val async = lifecycleScope.async(Dispatchers.IO) {
                    DatabaseClient.getInstance(getApplicationContext())?.getAppDatabase()
                        ?.userDao()
                        ?.insertUser(user)
                }
                async.await();
                setContent {
                    IndeterminateCircularIndicator()
                }
                Handler().postDelayed(Runnable {
                    finish();
                }, 3000)
            }
        }else{
            Toast.makeText(this, "Failed to add TODO", Toast.LENGTH_SHORT).show()
            sendDataBackToPreviousActivity()
            super.onBackPressed()
        }
    }

    private fun sendDataBackToPreviousActivity() {
        val intent = Intent().apply {
            putExtra("message", "error")
            // Put your data here if you want.
        }
        setResult(Activity.RESULT_OK, intent)
    }
}