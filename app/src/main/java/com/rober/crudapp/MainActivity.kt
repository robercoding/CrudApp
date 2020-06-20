package com.rober.crudapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rober.crudapp.adapters.SubscriberAdapter
import com.rober.crudapp.databinding.ActivityMainBinding
import com.rober.crudapp.db.Subscriber
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
        initRecyclerView()

        subscriberViewModel.message.observe(this@MainActivity, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun displaySubscribersList(){
        subscriberViewModel.subscribers.observe(this, Observer {list ->
            binding.subscriberRecyclerView.adapter =
                SubscriberAdapter(list, {selectedItem: Subscriber->setOnItemClickListener(selectedItem)})
        })
    }

    private fun initRecyclerView(){
        binding.subscriberRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        displaySubscribersList()
    }

    private fun setOnItemClickListener(subscriber: Subscriber){
        //Toast.makeText(this, "Selected name is ${subscriber.name}", Toast.LENGTH_SHORT).show()
        subscriberViewModel.initUpdateAndDelete(subscriber)
    }
}