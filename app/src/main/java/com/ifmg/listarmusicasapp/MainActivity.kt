package com.ifmg.listarmusicasapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.view.View
import android.view.View.OnClickListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ifmg.listarmusicasapp.databinding.ActivityMainBinding
import com.ifmg.listarmusicasapp.modelo.Musica
import com.ifmg.listarmusicasapp.view.MusicaListAdapter
import org.json.JSONObject
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class MainActivity : AppCompatActivity(), OnClickListener {

    lateinit var binding : ActivityMainBinding
    lateinit var dowloader:Runnable
    lateinit var adapter: MusicaListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        registrarEventos()
    }

    private fun registrarEventos() {
        binding.buscarBtn.setOnClickListener(this)
    }

    override fun onClick(botao: View) {
        //avalio o id do botao "pressionado"
        when(botao.id){
            binding.buscarBtn.id -> carregaMusicas()
        }
    }

    private fun carregaMusicas() {
        var textoBusca = binding.buscaMusicaTxt.text.toString()
        carregaArquivo(textoBusca)
    }

    fun carregaArquivo(textoBusca:String){

        //geralmente o download de arquivos é feita de services
        val politicaAct = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(politicaAct)

        //executando o dowload do arquivo me segundo plano
        dowloader = Runnable {
            //buscando os dados no serviço do iTunes
            val url = URL("https://itunes.apple.com/search?term=${textoBusca}")
            val req:HttpsURLConnection = url.openConnection() as HttpsURLConnection
            var buffer = InputStreamReader(req.inputStream)

            val linhas: List<String> = buffer.readLines()

            val jsonText = linhas.joinToString("\n")
            val jsonObject = JSONObject(jsonText)
            val resultsArray = jsonObject.getJSONArray("results")

            var gson = Gson()
            val musicListType = object : TypeToken<List<Musica>>() {}.type
            val musicList: MutableList<Musica> = gson.fromJson(resultsArray.toString(), musicListType)

            listarMusicas(musicList)
        }

        dowloader.run()

    }

    fun listarMusicas(musicas:MutableList<Musica>){
        adapter = MusicaListAdapter(musicas)

        binding.musicaList.layoutManager = LinearLayoutManager(baseContext)
        binding.musicaList.adapter = adapter
    }




}