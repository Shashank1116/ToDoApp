package com.example.fruitfaldev.todoapp.tasksDetails.ui

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.fruitfaldev.todoapp.BaseFragment
import com.example.fruitfaldev.todoapp.MainActivity

import com.example.fruitfaldev.todoapp.R
import com.example.fruitfaldev.todoapp.data.Task
import com.example.fruitfaldev.todoapp.data.localdb.AppExecutors
import com.example.fruitfaldev.todoapp.data.localdb.TaskDatabase
import com.example.fruitfaldev.todoapp.data.localdb.TasksDAO
import com.example.fruitfaldev.todoapp.databinding.FragmentNewTaskBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class NewTaskFragment : BaseFragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding:FragmentNewTaskBinding

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
        binding = FragmentNewTaskBinding.inflate(inflater,container,false)
        (activity as MainActivity).setHomeIcon(R.drawable.ic_arrow_back_white_24dp)
        setUpActionListeners()
        setHasOptionsMenu(true)
        return binding.root
    }

    private fun setUpActionListeners() {
        binding.save.setOnClickListener {
            createTask(binding.getTask())
        }
        binding.cancelAction.setOnClickListener {
            exit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item!!.itemId == android.R.id.home){
            exit()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun exit() {
        onFragmentChangeListener!!.onFragmentRemove(this, "Add New Task")
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onDetach() {
        super.onDetach()
    }

    fun createTask(task: Task?){
        if(task != null) {
            (activity as MainActivity).repository.createTask(task)
            exit()
        } else{
            Toast.makeText(context!!,"Empty title",Toast.LENGTH_LONG).show()
        }
    }

    fun FragmentNewTaskBinding.getTask():Task?{
        return if(this.title.text.isNotEmpty() || this.subtitle.text.isNotEmpty())
            Task(this.title.text.toString(),this.subtitle.text.toString())
        else
            null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            NewTaskFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
