package com.example.fruitfaldev.todoapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.fruitfaldev.todoapp.data.Task
import com.example.fruitfaldev.todoapp.data.localdb.TaskRepository
import com.example.fruitfaldev.todoapp.navigator.OnFragmentChangeListener
import com.example.fruitfaldev.todoapp.tasks.ui.TasksFragment

class MainActivity : AppCompatActivity(),OnFragmentChangeListener{
    lateinit var repository:TaskRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpActionBar()
        repository = TaskRepository(applicationContext!!)
        onFragmentReplace(TasksFragment.newInstance(),"Task Fragment")
    }

    override fun onFragmentAdd(fragment: Fragment?, TAG: String?) {
        val existingFragment = supportFragmentManager.findFragmentByTag(TAG)
        if (existingFragment == null) {
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, fragment!!).addToBackStack(TAG).commit()
        } else {
            supportFragmentManager.beginTransaction().show(fragment!!).commit()
        }
        supportFragmentManager.executePendingTransactions()

    }

    override fun onFragmentReplace(fragment: Fragment?, TAG: String?) {
        val existingFragment = supportFragmentManager.findFragmentByTag(TAG)
        if (existingFragment == null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment!!).addToBackStack(TAG).commit()
        } else {
            supportFragmentManager.beginTransaction().show(fragment!!).commit()
        }
        supportFragmentManager.executePendingTransactions()
    }

    override fun onFragmentRemove(fragment: Fragment?, TAG: String?) {
        supportFragmentManager.executePendingTransactions()
        val backCount = supportFragmentManager.backStackEntryCount
        for (i in 0 until backCount) {
            if (supportFragmentManager.getBackStackEntryAt(i).name.equals(TAG, ignoreCase = true)) {
                println(supportFragmentManager.getBackStackEntryAt(i).name)
                supportFragmentManager.popBackStack(TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }
        }
    }

    override fun onBackPressed() {
        for (entry in 0 until supportFragmentManager.backStackEntryCount) {
            Log.i("BackStackEntry", "Found fragment: " + supportFragmentManager.getBackStackEntryAt(entry).name)
        }
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        }else{
            finish()
        }
    }

    fun setUpActionBar(){
        supportActionBar!!.setDisplayShowTitleEnabled(true)

    }

    fun setHomeIcon(resourceId:Int){
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(resourceId)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    fun disableHomeIcon(){
        supportActionBar!!.setHomeButtonEnabled(false)
        supportActionBar!!.setDisplayShowHomeEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)

    }

    fun setTitle(title:String){
        supportActionBar!!.title = title
    }

}
