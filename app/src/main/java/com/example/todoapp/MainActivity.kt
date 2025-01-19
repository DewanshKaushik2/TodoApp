package com.example.todoapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.todoapp.dialog.AlertDialogExample
import com.example.todoapp.ui.theme.TodoAppTheme
import com.example.todoapp.view.EnterNewItemActivity
import com.example.todoapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TodoAppTheme {
                FloatingBar()
            }

        }
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.getData()
    }

    private fun onClick() {
        gotoNextActivity()
    }

    @Composable
    fun ShowDefaultText() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            shadowElevation = 4.dp
        ) {
            Box(
                contentAlignment = Alignment.Center, // Aligns children in the center
                modifier = Modifier.fillMaxSize() // Fills the available space
            ) {
                Text(
                    text = "Press the + button to add a TODO item",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .height(64.dp)
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(2.dp),
                )
            }
        }
    }

    @Composable
    fun FloatingBar() {

        Scaffold(topBar = { },
            floatingActionButtonPosition = FabPosition.End,
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        onClick()
                    }
                ) {
                    Icon(Icons.Filled.Add, "hi")
                }
            }, content = { padding ->
                Surface(modifier = Modifier.padding(padding)) {
                    List(mainViewModel)
                }
            })
    }

    companion object {
        const val START_ACTIVITY_3_REQUEST_CODE = 0
    }

    fun gotoNextActivity() {
        val intent = Intent(this, EnterNewItemActivity::class.java)
        startActivityForResult(intent, START_ACTIVITY_3_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == START_ACTIVITY_3_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val message = data!!.getStringExtra("message")
                if (message != null) {
                    if (message.trim().equals("error")) {
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                        setContent {
                            val openAlertDialog = rememberSaveable { mutableStateOf(true) }
                            showDialog("Failed to add TODO", openAlertDialog)
                            FloatingBar()
                        }
                    }
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    @Composable
    fun List(mainViewModel: MainViewModel) {
        //search
        //list
        val posts by rememberSaveable {
            mainViewModel.todolist
        }
// State to hold the search query
        var searchQuery by rememberSaveable { mutableStateOf("") }

        // Filter items based on the search query
        val filteredItems = posts.filter { it.itemName.startsWith(searchQuery, ignoreCase = true) }

        Column {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Enter text") },
                placeholder = { Text("Type here...") },
                shape = MaterialTheme.shapes.medium, // Rounded corners
                modifier = Modifier.fillMaxWidth(),
            )



            if (filteredItems.isEmpty()) {
                ShowDefaultText()
            } else {
                Log.e("MainActivity", filteredItems.toString())

                //new column
                val context = LocalContext.current
                val openAlertDialog = rememberSaveable { mutableStateOf(false) }

                Column(
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxWidth()
                ) {
                    //this is list
                    LazyColumn {
                        itemsIndexed(filteredItems) { index, item ->

                            Column {
                                if (item == null) return@Column
                                Card(
                                    shape = RoundedCornerShape(4.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                        .clickable(onClick = {
                                            //d gotoNextActivity()
                                        }),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                    )
                                ) {

                                }
                                Text(
                                    text = item.itemName,
                                    modifier = Modifier.padding(2.dp)
                                )
                                Divider(
                                    modifier = Modifier.fillMaxWidth(),
                                    color = Color.Black,
                                    thickness = 1.dp
                                )
                                showDialog("hi", openAlertDialog)
                            }
                        }
                    }
                    DisposableEffect(Unit) {
                        onDispose {}
                    }
                }
            }
        }
    }

    @Composable
    fun showDialog(data: String, openAlertDialog: MutableState<Boolean>) {

        if (openAlertDialog.value) {
            AlertDialogExample(
                onDismissRequest = {
                    openAlertDialog.value = false
                },
                onConfirmation = {

                },
                dialogTitle = "Error",
//            dialogText = "This is an example of an alert dialog with buttons.",
                dialogText = data,
                icon = Icons.Default.Info,
            )
        }
    }
}
