package com.example.littletodolist

import androidx.lifecycle.ViewModel

class ToDoViewModel : ViewModel() {
    val tasks = listOf("Send mail", "Workout")
}