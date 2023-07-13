package com.example.bayrakquiz2

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OturumAdapter(private val mContext:Context,private var oturumlar:List<Oturum>):
RecyclerView.Adapter<OturumAdapter.CardTasarimTutucu>(){
    inner class CardTasarimTutucu(val tasarim : View):RecyclerView.ViewHolder(tasarim)
    {
        var oturumText:TextView
        var dogruText:TextView
        var yanlisText:TextView
        var bosText:TextView
        var basariOraniText:TextView
        init {
            oturumText =tasarim.findViewById(R.id.oturumText)
            dogruText = tasarim.findViewById(R.id.dogruText)
            yanlisText = tasarim.findViewById(R.id.yanlisText)
            bosText = tasarim.findViewById(R.id.bosText)
            basariOraniText = tasarim.findViewById(R.id.basariOraniText)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardTasarimTutucu {
        val tasarim =LayoutInflater.from(mContext).inflate(R.layout.oturum_card_tasarim,parent,false)
        return CardTasarimTutucu(tasarim)
    }

    override fun getItemCount(): Int {
        return oturumlar.size
    }

    override fun onBindViewHolder(holder: CardTasarimTutucu, position: Int) {
        val oturum =oturumlar.get(position)
        holder.oturumText.text = oturum.oturum_id.toString()
        holder.dogruText.text = oturum.dogru.toString()
        holder.yanlisText.text = oturum.yanlis.toString()
        holder.bosText.text = oturum.bos.toString()
        holder.basariOraniText.text = oturum.basari_orani.toString()
    }
}