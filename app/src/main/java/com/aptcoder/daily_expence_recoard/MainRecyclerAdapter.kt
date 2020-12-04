package com.aptcoder.daily_expence_recoard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class MainRecyclerAdapter(options: FirebaseRecyclerOptions<RecyclerData>,val context: Context) :
    FirebaseRecyclerAdapter<RecyclerData, MainRecyclerAdapter.MainRecyclerViewHolder>(
        options
    ) {

    class MainRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemRecycler: RecyclerView = itemView.findViewById(R.id.itemRecycler)

        val date: TextView = itemView.findViewById(R.id.recoardDate)
        val total: TextView = itemView.findViewById(R.id.total)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainRecyclerViewHolder {
       return MainRecyclerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_recycler,parent,false))
    }

    override fun onBindViewHolder(
        holder: MainRecyclerViewHolder,
        position: Int,
        model:RecyclerData
    ) {

        holder.itemRecycler.layoutManager=LinearLayoutManager(context)
        holder.itemRecycler.adapter=InnerRecyclerAdapter(model.oneDayData!!)
        holder.date.text=this.snapshots.getSnapshot(position).key
        holder.total.text=getTotal(model.oneDayData!!)
    }

    private fun getTotal(model: ArrayList<DataItemClass>): CharSequence? {
        var sum=0.0
       for (data in model){
           sum+=data.price.toDouble()
       }
        return sum.toString()
    }


}