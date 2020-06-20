package com.rober.crudapp

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rober.crudapp.db.Subscriber
import com.rober.crudapp.db.SubscriberRespository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SubscriberViewModel(private val repository: SubscriberRespository) : ViewModel(), Observable{

    val subscribers = repository.subscribers
    private var isUpdateOrDelete = false
    private lateinit var subscriberToUpdateOrDelete: Subscriber

    private val statusMessage = MutableLiveData<Event<String>>()
    val message : LiveData<Event<String>>
            get() = statusMessage


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
        if(isUpdateOrDelete){
            subscriberToUpdateOrDelete.name = inputName.value!!
            subscriberToUpdateOrDelete.email = inputEmail.value!!
            updateSubscriber(subscriberToUpdateOrDelete)
        }else{
            val name = inputName.value!!
            val email = inputEmail.value!!
            insertSubscriber(Subscriber(0, name, email))
            inputName.value = null
            inputEmail.value = null
        }
    }

    fun clearOrDelete(){
        if(isUpdateOrDelete){
            deleteSubscriber(subscriberToUpdateOrDelete)
        }else{
            clearAll()
        }

    }

    fun initUpdateAndDelete(subscriber: Subscriber){
        inputName.value = subscriber.name
        inputEmail.value = subscriber.email
        isUpdateOrDelete = true
        subscriberToUpdateOrDelete = subscriber
        saveOrUpdateButton.value = "Update"
        clearOrDeleteButton.value = "Delete"
    }

    fun insertSubscriber(subscriber: Subscriber): Job = viewModelScope.launch {
            repository.insert(subscriber)
            statusMessage.value = Event("Subscriber has been inserted")
        }

    fun updateSubscriber(subscriber: Subscriber): Job = viewModelScope.launch {
        repository.update(subscriber)
        inputName.value = null
        inputEmail.value = null
        isUpdateOrDelete = false
        saveOrUpdateButton.value = "Save"
        clearOrDeleteButton.value = "Clear All"
        statusMessage.value = Event("Subscriber has been updated sucessfully")
        }

    fun deleteSubscriber(subscriber: Subscriber): Job = viewModelScope.launch {
        repository.delete(subscriber)
        inputName.value = null
        inputEmail.value = null
        isUpdateOrDelete = false
        saveOrUpdateButton.value = "Save"
        clearOrDeleteButton.value = "Clear All"
        statusMessage.value = Event("Subscriber has been deleted succesfully")
    }

    fun clearAll(): Job = viewModelScope.launch {
        repository.deleteAll()
        statusMessage.value = Event("All subscribers has been deleted succesfully")
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}