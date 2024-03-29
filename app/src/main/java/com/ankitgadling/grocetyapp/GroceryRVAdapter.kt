package com.ankitgadling.grocetyapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GroceryRVAdapter(
    var list: List<GroceryItems>,
    var groceryItemClickInterface: GroceryItemClickInterface
) : RecyclerView.Adapter<GroceryRVAdapter.GroceryViewHolder>() {



    inner class GroceryViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val nameTV=itemView.findViewById<TextView>(R.id.idTVItemName)
        val quantityTV=itemView.findViewById<TextView>(R.id.idTVQuantity)
        val rateTV=itemView.findViewById<TextView>(R.id.idTVRate)
        val ammountTV=itemView.findViewById<TextView>(R.id.idTVTotalAmt)
        val deleteTV=itemView.findViewById<ImageView>(R.id.idIVDelete)
    }


    interface GroceryItemClickInterface{
        fun onItemClick(groceryItems: GroceryItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryViewHolder {
       val view=LayoutInflater.from(parent.context).inflate(R.layout.grocery_rv_items,parent,false)
        return GroceryViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroceryViewHolder, position: Int) {
        holder.nameTV.text=list.get(position).itemName
        holder.quantityTV.text=list.get(position).itemQuantity.toString()
        holder.rateTV.text=list.get(position).itemPrice.toString()
        val itemTotal:Int=list.get(position).itemPrice*list.get(position).itemQuantity
        holder.ammountTV.text="Rs "+itemTotal.toString()
        holder.deleteTV.setOnClickListener {
            groceryItemClickInterface.onItemClick(list.get(position))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}