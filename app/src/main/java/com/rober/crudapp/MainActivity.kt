package com.rober.crudapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.rober.crudapp.databinding.ActivityMainBinding
import com.rober.crudapp.db.SubscriberDAO
import com.rober.crudapp.db.SubscriberDatabase
import com.rober.crudapp.db.SubscriberRespository

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var subscriberViewModel: SubscriberViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val subscriberDao = SubscriberDatabase.getInstance(application).subscriberDAO
        val repository = SubscriberRespository(subscriberDao)
        val subscriberViewModelFactory = SubscriberViewModelFactory(repository)
        subscriberViewModel = ViewModelProvider(this, subscriberViewModelFactory).get(SubscriberViewModel::class.java)

        binding.myViewModel = subscriberViewModel
        binding.lifecycleOwner = this

        displaySubscribersList()

    }

    private fun displaySubscribersList(){
        subscriberViewModel.subscribers.observe(this, Observer {list ->
            Log.i("MainActivity", list.toString())
        })
    }
}