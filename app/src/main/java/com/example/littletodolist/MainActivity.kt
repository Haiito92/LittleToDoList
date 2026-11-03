package com.example.littletodolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.littletodolist.ui.theme.LittleToDoListTheme

class MainActivity : ComponentActivity() {
    private val viewModel: ToDoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val tasks by viewModel.tasks.collectAsState()

            LittleToDoListTheme {
                ToDoView(tasks,
                    viewModel::addTask,
                    viewModel::toggleTask,
                    viewModel::deleteTask)
            }
        }
    }
}

@Composable
fun ToDoView(tasks: List<Task>, onAddTask: (String) -> Unit, onToggleTask: (Task, Boolean) -> Unit, onDeleteTask: (Task) -> Unit) {
    Scaffold() { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TitleRow()
            NewTaskRow(onAddTask)
            TasksColumn(tasks, onToggleTask, onDeleteTask)
        }
    }
}

@Composable
fun TitleRow(){
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("My Tasks",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold)
        Text("2 left",
            style = MaterialTheme.typography.bodyMedium,
            fontStyle = FontStyle.Italic)
    }
}

@Composable
fun NewTaskRow(onAddTask: (String) -> Unit){
    val state = rememberTextFieldState()

    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(state = state,
            placeholder = {Text("New Task...")},
            shape = RoundedCornerShape(32.dp),
            modifier = Modifier
                .weight(3.0f, true))

        Button({ onAddTask.invoke(state.text.toString()) },
            enabled = state.text.isNotBlank(),
            modifier = Modifier
                .weight(1.0f, true)
        ) {
            Text("Add", style = MaterialTheme.typography.bodyMedium,)
        }
    }
}

@Composable
fun TasksColumn(tasks: List<Task>, onToggleTask: (Task, Boolean) -> Unit, onDeleteTask: (Task) -> Unit){
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        items(tasks){ task ->
            TaskRow(task, onToggleTask, onDeleteTask)
        }
    }
}

@Composable
fun TaskRow(task: Task, onToggleTask: (Task, Boolean) -> Unit, onDeleteTask: (Task) -> Unit){
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(task.completed, { checked -> onToggleTask.invoke(task, checked) },
            Modifier.weight(1.0f))

        Text(task.title, modifier = Modifier
            .fillMaxWidth()
            .weight(4.0f),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Left,
            textDecoration = if (task.completed) TextDecoration.LineThrough else TextDecoration.None)

        IconButton({ onDeleteTask.invoke(task) },
            modifier = Modifier.weight(1.0f)) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ToDoPreview() {
    LittleToDoListTheme {
        ToDoView(
            listOf(Task(1, "Workout", true)),
            {},
            { task: Task, bool: Boolean -> },
            {})
    }
}