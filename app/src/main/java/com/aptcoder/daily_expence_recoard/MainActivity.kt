package com.aptcoder.daily_expence_recoard

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.EditText
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
        mainactivity.mainRecycler.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true)
        val option:FirebaseRecyclerOptions<RecyclerData> = FirebaseRecyclerOptions.Builder<RecyclerData>().setQuery(
            FirebaseDatabase.getInstance().reference,RecyclerData::class.java).build()
        adapter= MainRecyclerAdapter(option,this)
        mainactivity.mainRecycler.adapter=adapter


        mainactivity.addEntry.setOnClickListener {
            val date=SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
            addEntry(mainactivity.itemName.text.trim().toString(),mainactivity.price.text.toString(),date)
        }
        mainactivity.addEntry.setOnLongClickListener(){
                    val dialog=Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.input_dialog)
            val name=dialog.findViewById<EditText>(R.id.dialogProductName)
            val price=dialog.findViewById<EditText>(R.id.dialogPriceName)
            val date=dialog.findViewById<EditText>(R.id.diaogDate)
            val addBtn=dialog.findViewById<Button>(R.id.dialogAddRecoard)
            addBtn.setOnClickListener {
                addEntry(name.text.toString().trim(),price.text.toString().trim(),date.text.toString().trim())
                dialog.dismiss()
            }
            dialog.show()

            true
        }

    }

    private fun addEntry(itemname:String,price:String,date:String) {


        if(itemname.isEmpty()||price.isEmpty()){
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()

        }else{

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


    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }
}