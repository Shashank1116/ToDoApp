package com.example.fruitfaldev.todoapp.tasks.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fruitfaldev.todoapp.BaseFragment
import com.example.fruitfaldev.todoapp.MainActivity
import com.example.fruitfaldev.todoapp.R

import com.example.fruitfaldev.todoapp.data.Task
import com.example.fruitfaldev.todoapp.data.TasksDataSource
import com.example.fruitfaldev.todoapp.data.localdb.AppExecutors
import com.example.fruitfaldev.todoapp.data.localdb.TaskDatabase
import com.example.fruitfaldev.todoapp.data.localdb.TasksDAO
import com.example.fruitfaldev.todoapp.databinding.FragmentTasksBinding
import com.example.fruitfaldev.todoapp.navigator.OnClickListener
import com.example.fruitfaldev.todoapp.tasksDetails.ui.NewTaskFragment
import com.example.fruitfaldev.todoapp.tasksDetails.ui.TaskDetailFragment

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class TasksFragment : BaseFragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding:FragmentTasksBinding
    private lateinit var listener:OnClickListener
    private lateinit var adapter:TasksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTasksBinding.inflate(inflater,container,false)
        setUpListAdapter()
        (activity as MainActivity).setTitle("Tasks")
        (activity as MainActivity).disableHomeIcon()
        binding.fab.setOnClickListener{
            onFragmentChangeListener!!.onFragmentReplace(NewTaskFragment.newInstance(),"Add New Task")
        }
        loadTasks()
        setHasOptionsMenu(true)
        return binding.root
    }

    private fun setUpListAdapter() {
        configureListView(binding.tasksListView)
        listener = object : OnClickListener {
            override fun onClick(id: String) {
                onFragmentChangeListener!!.onFragmentReplace(TaskDetailFragment.newInstance(id), "Task Details")
            }

        }
        adapter = TasksAdapter(context!!, ArrayList(0),listener)
        binding.tasksListView.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item!!.itemId == android.R.id.home){
            onFragmentChangeListener!!.onFragmentRemove(this,"Task Fragment")
        }
        return super.onOptionsItemSelected(item)
    }

    fun configureListView(recyclerView: RecyclerView){
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator!!.changeDuration = 0
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    fun loadTasks(){
        (activity as MainActivity).repository.getTasks(object :TasksDataSource.LoadTasksCallback{
            override fun onTasksLoaded(tasks: List<Task>) {
                adapter.refreshTasks(ArrayList(tasks))
            }

            override fun onDataNotAvailable() {
                Toast.makeText(context!!,"No Data available",Toast.LENGTH_LONG).show()
            }

        })
    }



    override fun onDetach() {
        super.onDetach()
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            TasksFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
