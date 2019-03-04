package com.example.fruitfaldev.todoapp

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.fruitfaldev.todoapp.navigator.OnFragmentChangeListener
import java.text.SimpleDateFormat
import java.util.*

open class BaseFragment : Fragment() {

    protected var onFragmentChangeListener: OnFragmentChangeListener?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        onFragmentChangeListener = context as OnFragmentChangeListener?
    }

}