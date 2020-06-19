package com.rober.crudapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rober.crudapp.db.SubscriberRespository
import java.lang.IllegalArgumentException

class SubscriberViewModelFactory(val repository: SubscriberRespository): ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SubscriberViewModel::class.java)){
            return SubscriberViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }

}