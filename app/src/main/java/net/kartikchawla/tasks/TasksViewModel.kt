package net.kartikchawla.tasks

import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.util.*

class TasksViewModel(private val tasksDao: Tasks_DAO): ViewModel() {
    var newTaskName = ""
    private val tasks = tasksDao.getAll()
    var deleteTaskID = "";
    val tasksString = Transformations.map(tasks) {
        tasks -> formatTasks(tasks)
    }
    fun addTask() {
        viewModelScope.launch {
            val newTask = Tasks()
            newTask.taskName = newTaskName
            tasksDao.insert(newTask)
        }
    }
    fun getAndDeleteTask() {
        if(deleteTaskID == null || deleteTaskID == "") {
            return;
        }
        viewModelScope.launch {
            println(deleteTaskID.toLong())
            val task = tasksDao.get(deleteTaskID.toLong())
            task.observeForever(Observer {
                newValue -> if (newValue != null) {
                    deleteTask(newValue)
            }
            })
        }
    }

    private fun deleteTask(task: Tasks) {
        viewModelScope.launch {
            tasksDao.delete(task)
        }
    }

    fun formatTasks(tasks: List<Tasks>): String {
        return tasks.fold("") {
            str, item -> str + '\n' + formatTask(item)
        }
    }
    fun formatTask(task: Tasks): String {
        return "ID: ${task.taskID}\nName: ${task.taskName}\nComplete: ${task.taskDone}\n"
    }
}