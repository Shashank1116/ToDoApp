package com.example.fruitfaldev.todoapp.data.localdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.fruitfaldev.todoapp.data.Task

@Database(entities = arrayOf(Task::class), version = 1)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TasksDAO

    companion object {

        private var INSTANCE: TaskDatabase? = null

        private val lock = Any()

        fun getInstance(context: Context): TaskDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        TaskDatabase::class.java, "Tasks.db")
                        .build()
                }
                return INSTANCE!!
            }
        }
    }

}