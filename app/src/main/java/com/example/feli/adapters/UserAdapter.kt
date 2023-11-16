package com.example.feli.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.feli.R
import com.example.feli.Persona

class UserAdapter (var datos:List<Persona>, var Onclick : (Int) -> Unit, var context: Context): RecyclerView.Adapter<UserAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemName: TextView = itemView.findViewById(R.id.NameTxt)
        var itemPhoto:ImageView = itemView.findViewById(R.id.MainImageView)
        var itemCard:CardView = itemView.findViewById(R.id.CardView)
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {

        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.user_base, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        var persona = datos[i]
        viewHolder.itemName.text = persona.Nombre
        Glide.with(context).load(persona.url).centerInside().circleCrop().into(viewHolder.itemPhoto)
        viewHolder.itemCard.setOnClickListener {
            Onclick(i)
        }

    }
    override fun getItemCount(): Int {
        return datos.size
    }
}