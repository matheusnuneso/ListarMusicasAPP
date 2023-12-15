package com.ifmg.listarmusicasapp.view

import androidx.recyclerview.widget.RecyclerView
import com.ifmg.listarmusicasapp.databinding.MusicaItemBinding
import com.ifmg.listarmusicasapp.modelo.Musica
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class MusicaHold(var binding: MusicaItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(musica: Musica){
        binding.trackNameTxt.text = tratarTamanho(musica.trackName, 22)
        binding.collectionNameTxt.text = tratarTamanho(musica.collectionName, 60)
        binding.releaseDateTxt.text = localDateToString(musica.releaseDate)
    }

    fun tratarTamanho(valor:String?, tamanho:Int): String{

        if (valor != null){
            if ((valor.length > tamanho)){
                var trackNameTratado = valor.substring(0, minOf(tamanho, valor.length))
                return "$trackNameTratado..."
            }

            return valor
        }

        return ""
    }

    fun localDateToString(valor:String):String{
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        try {
            val date = inputFormat.parse(valor)
            return outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return ""
    }

}