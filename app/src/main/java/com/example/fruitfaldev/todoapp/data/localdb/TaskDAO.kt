package com.example.fruitfaldev.todoapp.data.localdb

import androidx.room.*
import com.example.fruitfaldev.todoapp.data.Task

@Dao
interface TasksDAO {

    @Query("SELECT * FROM Task") fun getTasks(): List<Task>


    @Query("SELECT * FROM Task WHERE entryid = :taskId") fun getTaskById(taskId: String): Task?


    @Insert(onConflict = OnConflictStrategy.REPLACE) fun insertTask(task: Task)


    @Update
    fun updateTask(task: Task): Int


    @Query("UPDATE task SET completed = :completed WHERE entryid = :taskId")
    fun updateCompleted(taskId: String, completed: Boolean)

    @Query("DELETE FROM Task WHERE entryid = :taskId") fun deleteTaskById(taskId: String): Int


    @Query("DELETE FROM Task") fun deleteTasks()


    @Query("DELETE FROM Task WHERE completed = 1") fun deleteCompletedTasks(): Int
}