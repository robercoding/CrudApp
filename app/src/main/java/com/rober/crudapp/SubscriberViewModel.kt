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
            val newRowId = repository.insert(subscriber)
            if(newRowId > -1){
                statusMessage.value = Event("Subscriber has been inserted")
            }else{
                statusMessage.value = Event("Error ocurred")
            }
        }

    fun updateSubscriber(subscriber: Subscriber): Job = viewModelScope.launch {
        val rowId = repository.update(subscriber)
        if(rowId > 0 ){
            statusMessage.value = Event("Subscriber has updated")
        }else{
            statusMessage.value = Event("Error ocurred")
        }
        inputName.value = null
        inputEmail.value = null
        isUpdateOrDelete = false
        saveOrUpdateButton.value = "Save"
        clearOrDeleteButton.value = "Clear All"
    }

    fun deleteSubscriber(subscriber: Subscriber): Job = viewModelScope.launch {
        val noOfRowDeleted = repository.delete(subscriber)
        if(noOfRowDeleted > 0 ){
            statusMessage.value = Event("$noOfRowDeleted subscriber row has been deleted succesfully")
        }else{
            statusMessage.value = Event("Error ocurred")
        }
        inputName.value = null
        inputEmail.value = null
        isUpdateOrDelete = false
        saveOrUpdateButton.value = "Save"
        clearOrDeleteButton.value = "Clear All"
    }

    fun clearAll(): Job = viewModelScope.launch {
        val noOfRowsDeleted = repository.deleteAll()
        if(noOfRowsDeleted > 0 ){
            statusMessage.value = Event("$noOfRowsDeleted subscribers rows has been deleted succesfully")
        }else{
            statusMessage.value = Event("Error ocurred")
        }
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}