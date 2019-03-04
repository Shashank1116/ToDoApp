package com.example.fruitfaldev.todoapp.tasksDetails.ui

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.fruitfaldev.todoapp.BaseFragment
import com.example.fruitfaldev.todoapp.MainActivity
import com.example.fruitfaldev.todoapp.R
import com.example.fruitfaldev.todoapp.data.Task
import com.example.fruitfaldev.todoapp.data.TasksDataSource
import com.example.fruitfaldev.todoapp.data.localdb.AppExecutors
import com.example.fruitfaldev.todoapp.data.localdb.TaskDatabase
import com.example.fruitfaldev.todoapp.databinding.FragmentTaskDetailBinding

private const val TASK_ID = "TASK ID"


class TaskDetailFragment : BaseFragment() {
    private var taskId: String? = null
    private lateinit var binding: FragmentTaskDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            taskId = it.getString(TASK_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskDetailBinding.inflate(inflater,container,false)
        setUpActionBar()
        getTaskDetails(taskId)
        setUpActionListeners()
        setHasOptionsMenu(true)
        return binding.root
    }

    private fun setUpActionBar() {
        (activity as MainActivity).setTitle("Task Details")
        (activity as MainActivity).setHomeIcon(R.drawable.ic_arrow_back_white_24dp)
    }

    private fun setUpActionListeners() {
        binding.save.setOnClickListener {
            updateTask(binding.getTask()!!)
        }

        binding.delete.setOnClickListener {
            deleteTask(taskId!!)
        }
    }

    private fun exit() {
        onFragmentChangeListener!!.onFragmentRemove(this, "Task Details")
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item!!.itemId == android.R.id.home){
            onFragmentChangeListener!!.onFragmentRemove(this,"Task Details")
        }
        return super.onOptionsItemSelected(item)
    }

    fun onButtonPressed(uri: Uri) {
    }

    fun updateTask(task: Task){
        (activity as MainActivity).repository.updateTask(task)
        exit()
    }

    fun deleteTask(taskId:String){
        (activity as MainActivity).repository.deleteTask(taskId)
        exit()
    }

    private fun FragmentTaskDetailBinding.getTask():Task?{
        return if(this.subtitle.text.isNotEmpty() || this.title.text.isNotEmpty()){
            Task(this.title.text.toString(), this.subtitle.text.toString(),taskId!!)
        }else{
            null
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }
    fun FragmentTaskDetailBinding.bind(task:Task){
        this.title.setText(task.title)
        this.subtitle.setText(task.description)

    }

    override fun onDetach() {
        super.onDetach()
    }
    fun getTaskDetails(id:String?){
        (activity as MainActivity).repository.getTask(id!!,object :TasksDataSource.GetTaskCallback{
            override fun onTaskLoaded(task: Task) {
                binding.bind(task)
            }

            override fun onDataNotAvailable() {
                Toast.makeText(context!!,"Task not found",Toast.LENGTH_LONG).show()
            }
        })
    }



    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }


    companion object {

        @JvmStatic
        fun newInstance(taskId: String) =
            TaskDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(TASK_ID, taskId)
                }
            }
    }
}
