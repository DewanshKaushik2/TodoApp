package com.example.todoapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.database.DatabaseClient
import com.example.todoapp.dialog.AlertDialog
import com.example.todoapp.model.TodoItem
import com.example.todoapp.ui.theme.TodoAppTheme
import com.example.todoapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val sharedViewModel: MainViewModel by viewModels()
    lateinit var navController: NavHostController;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TodoAppTheme {
                navController = rememberNavController()

                NavHost(navController, startDestination = "firstScreen") {
                    composable("firstScreen") {
                        FloatingBar(navController, sharedViewModel)
                    }
                    composable("secondScreen") { backStackEntry ->
                        val message = backStackEntry.arguments?.getString("message") ?: "No message"
                        AddToDO(navController, sharedViewModel)
                    }
                }

                //d   FloatingBar()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.getData()
    }

    private fun onClick() {
        //  gotoNextActivity()
        navController.navigate("secondScreen")

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
    fun FloatingBar(navController: NavController, sharedViewModel: MainViewModel) {
        val result = rememberSaveable { mutableStateOf<String?>(null) }
        val context = LocalContext.current
        // Listen for the result
        LaunchedEffect(result) {
            navController.currentBackStackEntry?.savedStateHandle?.getLiveData<String>("resultKey")
                ?.observeForever { newResult ->
                    result.value = newResult;
                    Toast.makeText(context, "Failed to add TODO", Toast.LENGTH_SHORT).show()
                }
        }
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
                    List(sharedViewModel)
                }
            })
    }

    @Composable
    fun List(mainViewModel: MainViewModel) {
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
                                HorizontalDivider(
                                    modifier = Modifier.fillMaxWidth(),
                                    color = Color.Black,
                                    thickness = 1.dp
                                )
                                ShowDialog("hi", openAlertDialog)
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
    fun ShowDialog(data: String, openAlertDialog: MutableState<Boolean>) {

        if (openAlertDialog.value) {
            AlertDialog(
                onDismissRequest = {
                    openAlertDialog.value = false
                },
                dialogTitle = "Error",
                dialogText = data,
                icon = Icons.Default.Info,
            )
        }
    }

    // new screen
    @Composable
    fun AddToDO(
        navController: NavController,
        sharedViewModel: MainViewModel
    ) {
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


    private fun onclick(text: String) {
        val user = TodoItem()
        if (!text.trim().equals("Error", true)) {
            user.itemName = text
            lifecycleScope.launch {
                val async = lifecycleScope.async(Dispatchers.IO) {
                    DatabaseClient.getInstance(applicationContext)?.getAppDatabase()
                        ?.itemDao()
                        ?.insertItem(user)
                }
                async.await()
                setContent {
                    IndeterminateCircularIndicator()
                    Handler().postDelayed({
                        setContent {
                            FloatingBar(navController = navController, sharedViewModel = sharedViewModel)
                        }
                    },3000)
                }
            }
        } else {
            Toast.makeText(this, "Failed to add TODO", Toast.LENGTH_SHORT).show()
            navController.previousBackStackEntry?.savedStateHandle?.set("resultKey", "error")
            setContent {
                FloatingBar(navController = navController, sharedViewModel = sharedViewModel)
            }
        }
    }
}
