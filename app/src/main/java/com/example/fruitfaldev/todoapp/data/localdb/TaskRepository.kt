package com.example.fruitfaldev.todoapp.data.localdb

import android.content.Context
import com.example.fruitfaldev.todoapp.data.Task
import com.example.fruitfaldev.todoapp.data.TasksDataSource

class TaskRepository(val context: Context){
    val tasksLocalDataSource:TasksLocalDataSource = TasksLocalDataSource.getInstance(AppExecutors(),
                                                                                   TaskDatabase.getInstance(context).taskDao())
    fun getTask(taskId: String,callback: TasksDataSource.GetTaskCallback){
        tasksLocalDataSource.getTask(taskId,callback)

    }
    fun getTasks(callback:TasksDataSource.LoadTasksCallback){
        tasksLocalDataSource.getTasks(callback)
    }

    fun updateTask(task:Task){
        tasksLocalDataSource.updateTask(task)
    }

    fun createTask(task:Task){
        tasksLocalDataSource.saveTask(task)

    }

    fun deleteTask(taskId:String){
        tasksLocalDataSource.deleteTask(taskId)

    }

}
