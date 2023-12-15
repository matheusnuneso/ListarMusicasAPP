package com.ifmg.listarmusicasapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ifmg.listarmusicasapp.databinding.MusicaItemBinding
import com.ifmg.listarmusicasapp.modelo.Musica

class MusicaListAdapter(val musica:MutableList<Musica>?): RecyclerView.Adapter<MusicaHold>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicaHold {
        val item = MusicaItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MusicaHold(item)
    }

    override fun getItemCount(): Int {
        return if (musica != null) {musica.size} else {0}
    }

    override fun onBindViewHolder(holder: MusicaHold, position: Int) {
        holder.bind(musica!![position])
    }


}