package com.aptcoder.daily_expence_recoard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.aptcoder.daily_expence_recoard.databinding.ActivityMainBinding
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: MainRecyclerAdapter
    private lateinit var mainactivity:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainactivity=DataBindingUtil.setContentView(this,R.layout.activity_main)
        mainactivity.mainRecycler.layoutManager=LinearLayoutManager(this)
        val option:FirebaseRecyclerOptions<RecyclerData> = FirebaseRecyclerOptions.Builder<RecyclerData>().setQuery(
            FirebaseDatabase.getInstance().reference,RecyclerData::class.java).build()
        adapter= MainRecyclerAdapter(option,this)
        mainactivity.mainRecycler.adapter=adapter

       // addDummyData()

        mainactivity.addEntry.setOnClickListener {
            addEntry()
        }

    }

    private fun addEntry() {
        val itemname=mainactivity.itemName.text.trim().toString()
        val price=mainactivity.price.text.trim().toString()
        if(itemname.isEmpty()||price.isEmpty()){
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()

        }else{
          val date=SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
            val ref=FirebaseDatabase.getInstance().reference.child(date)
            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        //get and add
                        val oldData=snapshot.getValue(RecyclerData::class.java)!!


                        oldData.oneDayData?.add(DataItemClass(itemname,price))
                        ref.setValue(oldData)


                    }else{
                        val recyclerData=RecyclerData()
                        recyclerData.oneDayData=ArrayList()
                        recyclerData.oneDayData?.add(DataItemClass(itemname,price))
                        ref.setValue(recyclerData)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@MainActivity, "failed", Toast.LENGTH_SHORT).show()

                }

            })
        }
    }

//    private fun addDummyData() {
//        val recyclerData=RecyclerData()
//        recyclerData.oneDayData=ArrayList()
//        recyclerData.oneDayData?.add(DataItemClass("mango","10"))
//        recyclerData.oneDayData?.add(DataItemClass("asd","103"))
//        recyclerData.oneDayData?.add(DataItemClass("das","150"))
//        recyclerData.oneDayData?.add(DataItemClass("das","106"))
//        recyclerData.oneDayData?.add(DataItemClass("das","10"))
//        FirebaseDatabase.getInstance().reference.child("20-12-2020").setValue(recyclerData).addOnCompleteListener {
//            if(it.isSuccessful){
//                Toast.makeText(this, "done", Toast.LENGTH_SHORT).show()
//
//            }else{
//                Toast.makeText(this, "failes", Toast.LENGTH_SHORT).show()
//
//            }
//        }
//        FirebaseDatabase.getInstance().reference.child("21-12-2020").setValue(recyclerData).addOnCompleteListener {
//            if(it.isSuccessful){
//                Toast.makeText(this, "done", Toast.LENGTH_SHORT).show()
//
//            }else{
//                Toast.makeText(this, "failes", Toast.LENGTH_SHORT).show()
//
//            }
//        }
//        FirebaseDatabase.getInstance().reference.child("22-12-2020").setValue(recyclerData).addOnCompleteListener {
//            if(it.isSuccessful){
//                Toast.makeText(this, "done", Toast.LENGTH_SHORT).show()
//
//            }else{
//                Toast.makeText(this, "failes", Toast.LENGTH_SHORT).show()
//
//            }
//        }
//    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }
}