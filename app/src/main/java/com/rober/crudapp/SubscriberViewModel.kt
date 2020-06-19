package com.rober.crudapp

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rober.crudapp.db.Subscriber
import com.rober.crudapp.db.SubscriberRespository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SubscriberViewModel(private val repository: SubscriberRespository) : ViewModel(), Observable{

    val subscribers = repository.subscribers

    @Bindable
    val inputName = MutableLiveData<String>()
    @Bindable
    val inputEmail = MutableLiveData<String>()
    @Bindable
    val saveOrUpdateButton = MutableLiveData<String>()
    @Bindable
    val clearOrDeleteButton = MutableLiveData<String>()

    init {
        saveOrUpdateButton.value = "Save"
        clearOrDeleteButton.value = "Clear"
    }

    fun saveOrUpdate(){
        val name = inputName.value!!
        val email = inputEmail.value!!
        insertSubscriber(Subscriber(0, name, email))

        inputName.value = null
        inputEmail.value = null

    }

    fun clearOrDelete(){
        clearAll()
    }

    fun insertSubscriber(subscriber: Subscriber): Job = viewModelScope.launch {
            repository.insert(subscriber)
        }

    fun updateSubscriber(subscriber: Subscriber): Job = viewModelScope.launch {
        repository.update(subscriber)
        }

    fun deleteSubscriber(subscriber: Subscriber): Job = viewModelScope.launch {
        repository.delete(subscriber)
    }

    fun clearAll(): Job = viewModelScope.launch {
        repository.deleteAll()
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}