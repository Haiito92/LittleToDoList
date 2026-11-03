package com.example.littletodolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class ToDoViewModel : ViewModel() {
    private var _nextId = 0;
    private val _tasks = MutableStateFlow(listOf<Task>())
    val tasks: StateFlow<List<Task>> = _tasks

    fun addTask(title: String){
        _tasks.update { currentList -> currentList + Task(_nextId, title) }
        _nextId++;
    }

    fun toggleTask(task: Task, checked: Boolean){
        _tasks.update { currentList ->
            currentList.map { if(it == task) it.copy(completed = checked) else it } }
    }

    fun deleteTask(task: Task){
        _tasks.update { currentList -> currentList - task }
    }
}