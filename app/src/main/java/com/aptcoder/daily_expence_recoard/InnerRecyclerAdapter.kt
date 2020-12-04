package com.aptcoder.daily_expence_recoard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class InnerRecyclerAdapter(val data:ArrayList<DataItemClass>): RecyclerView.Adapter<InnerRecyclerAdapter.InnerViewHolder>() {

    class InnerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productName:TextView=itemView.findViewById(R.id.productName)
        val productProce:TextView=itemView.findViewById(R.id.productPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
      return InnerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.single_item,parent,false))
    }

    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
        holder.productName.text=data[position].productName
        holder.productProce.text=data[position].price
    }

    override fun getItemCount(): Int {
        return data.size


    }
}