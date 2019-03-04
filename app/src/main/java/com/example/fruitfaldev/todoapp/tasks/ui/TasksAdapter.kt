package com.example.fruitfaldev.todoapp.tasks.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fruitfaldev.todoapp.data.Task
import com.example.fruitfaldev.todoapp.databinding.TaskLayoutBinding
import com.example.fruitfaldev.todoapp.navigator.OnClickListener

class TasksAdapter(val context: Context, var taskList: ArrayList<Task>, val listener:OnClickListener): RecyclerView.Adapter<TasksAdapter.TasksViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        val binding = TaskLayoutBinding.inflate(inflator,parent,false)
        return TasksViewHolder(binding,listener)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        val task = taskList[position]
        holder.bind(task)
        holder.attachListener(task.id)

    }

    fun refreshTasks(tasks:ArrayList<Task>) {
        taskList = tasks
        notifyDataSetChanged()
    }

    class TasksViewHolder(val binding: TaskLayoutBinding,val listener: OnClickListener):RecyclerView.ViewHolder(binding.root){
        fun bind(task:Task){
            binding.title.text = task.titleForList
            binding.subtitle.text = task.description
        }

        fun attachListener(position: String) {
            binding.root.setOnClickListener {
                listener.onClick(position)
            }
        }

    }
}