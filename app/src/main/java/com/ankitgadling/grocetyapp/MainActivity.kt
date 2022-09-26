package com.ankitgadling.grocetyapp

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() , GroceryRVAdapter.GroceryItemClickInterface{
    lateinit var itemRV:RecyclerView
    lateinit var addFAB:FloatingActionButton
    lateinit var list: List<GroceryItems>
    lateinit var groceryRVAdapter: GroceryRVAdapter
    lateinit var groceryViewModal:GroceryViewModal



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        itemRV =findViewById(R.id.idRVItems)
        addFAB=findViewById(R.id.idFABAdd)
        list=ArrayList<GroceryItems>()
        groceryRVAdapter= GroceryRVAdapter(list,this)
        itemRV.layoutManager=LinearLayoutManager(this)
        itemRV.adapter=groceryRVAdapter
        val groceryRepository=GroceryRepository(GroceryDatabase(this))
        val factory=GroceryViewModalFactory(groceryRepository)
        groceryViewModal=ViewModelProvider(this,factory).get(GroceryViewModal::class.java)
        groceryViewModal.getAllGroceryItems().observe(this, Observer {
            groceryRVAdapter.list=it
            groceryRVAdapter.notifyDataSetChanged()
        })
        addFAB.setOnClickListener{
            openDialog()

        }

    }
    fun openDialog(){
        val dialog=Dialog(this)
        dialog.setContentView(R.layout.grocery_add_dialog)
        val cancelBtn=dialog.findViewById<Button>(R.id.idBtnCancel)
        val addBtn=dialog.findViewById<Button>(R.id.idBtnAdd)
        val itmEdt=dialog.findViewById<EditText>(R.id.idEdtItemName)
        val itmPriceEdt=dialog.findViewById<EditText>(R.id.idEdtItemPrice)
        val itmQuantityEdt=dialog.findViewById<EditText>(R.id.idEdtItemQuantity)

        cancelBtn.setOnClickListener{
            dialog.dismiss()
        }
        addBtn.setOnClickListener {
            val itemName:String=itmEdt.text.toString()
            val itemPrice:String=itmPriceEdt.text.toString()
            val itemQuantity:String=itmQuantityEdt.text.toString()
            val qtl :Int =itemQuantity.toInt()
            val pr:Int =itemPrice.toInt()
            if (itemName.isNotEmpty() && itemPrice.isNotEmpty() &&itemQuantity.isNotEmpty()){
                val items=GroceryItems(itemName,qtl,pr)
                groceryViewModal.insert(items)
                Toast.makeText(applicationContext,"Item Inserted..",Toast.LENGTH_SHORT).show()
                groceryRVAdapter.notifyDataSetChanged()
                dialog.dismiss()
            }else{
                Toast.makeText(applicationContext,"Enter all The Data..",Toast.LENGTH_SHORT).show()
            }
        }
        dialog.show()

    }

    override fun onItemClick(groceryItems: GroceryItems) {
        groceryViewModal.delete(groceryItems)
        groceryRVAdapter.notifyDataSetChanged()
        Toast.makeText(applicationContext,"Item Deleted...",Toast.LENGTH_SHORT).show()
    }
}