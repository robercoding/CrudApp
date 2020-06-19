package com.rober.crudapp.db

class SubscriberRespository(private val subscriberDAO: SubscriberDAO) {

    val  subscribers = subscriberDAO.getAllSubscribers()

    suspend fun insert(subscriber: Subscriber){
        subscriberDAO.insertSubscriber(subscriber)
    }

    suspend fun update(subscriber: Subscriber){
        subscriberDAO.updateSubscriber(subscriber)
    }

    suspend fun delete(subscriber: Subscriber){
        subscriberDAO.deleteSubscriber(subscriber)
    }

    suspend fun deleteAll(){
        subscriberDAO.deleteAll()
    }
}