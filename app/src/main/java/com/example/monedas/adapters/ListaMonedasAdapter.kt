package com.example.monedas.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.monedas.R
import com.example.monedas.models.Moneda
import kotlinx.android.synthetic.main.card_model.view.*

abstract class ListaMonedasAdapter(): RecyclerView.Adapter<ListaMonedasAdapter.ViewHolder>() {

    private val data: ArrayList<Moneda>

    init {
        data = ArrayList()
    }

    abstract fun addListener(holder:ViewHolder, moneda: Moneda)

    fun adicionarMonedas(listaMonedas:ArrayList<Moneda>){
        data.addAll(listaMonedas)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view:View = LayoutInflater.from(parent.context).inflate(R.layout.card_model, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var moneda:Moneda = data.get(position)
        holder.nombreMoneda.setText(moneda.nombre)

        Glide.with(holder.logoMoneda.context)
                .load(moneda.logo)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.logoMoneda)

        addListener(holder, moneda)
    }

    inner class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        var logoMoneda:ImageView
        var nombreMoneda:TextView
        var contenedorMoneda:LinearLayout

        init {
            logoMoneda = itemView.logoMoneda
            nombreMoneda = itemView.nombreMoneda
            contenedorMoneda = itemView.contenedorMoneda
        }
    }

}