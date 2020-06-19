package com.rober.crudapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.rober.crudapp.R
import com.rober.crudapp.databinding.ListItemBinding
import com.rober.crudapp.db.Subscriber

class SubscriberAdapter(private val subscribersList: List<Subscriber>, private val clickListener: (Subscriber)->Unit) : RecyclerView.Adapter<SubscriberViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscriberViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : ListItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent, false)
        return SubscriberViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return subscribersList.size
    }

    override fun onBindViewHolder(holder: SubscriberViewHolder, position: Int) {
        holder.bind(subscribersList[position], clickListener)
    }


}

class SubscriberViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root){

    fun bind(subscriber: Subscriber,  clickListener: (Subscriber)->Unit){
        binding.listItemTextNameLayout.text = subscriber.name
        binding.listItemTextEmailLayout.text = subscriber.email
        binding.listItemLayout.setOnClickListener{
            clickListener(subscriber)
        }
    }
}