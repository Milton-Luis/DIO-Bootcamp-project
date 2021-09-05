package com.miltonluis.todolista.feature.listatarefas

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.miltonluis.todolista.R
import com.miltonluis.todolista.application.ToDoListApplication
import java.lang.Exception
import com.miltonluis.todolista.bases.BaseActivity
import com.miltonluis.todolista.feature.listatarefas.adapter.ToDoListAdapter
import com.miltonluis.todolista.feature.tarefa.ToDoListActivity
import com.miltonluis.todolista.feature.listatarefas.model.ToDoListVO
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private var adapter:ToDoListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupToolBar(toolbar, "Lista de Tarefas", false)
        setupListView()
        setupOnClicks()
    }


    private fun setupOnClicks(){
        fBtn_adicionar.setOnClickListener { onClickAdd() }
        ivPesquisar.setOnClickListener { onClickBuscar() }
    }

    private fun setupListView(){
        recyclerView.layoutManager = LinearLayoutManager(this)

    }


    override fun onResume() {
        super.onResume()
        onClickBuscar()
    }

    private fun onClickAdd(){
        val intent = Intent(this,ToDoListActivity::class.java)
        startActivity(intent)
    }

    private fun onClickItemRecyclerView(index: Int){
        val intent = Intent(this,ToDoListActivity::class.java)
        intent.putExtra("index", index)
        startActivity(intent)
    }

    private fun onClickBuscar(){
        val busca = etBuscar.text.toString()
        progress.visibility = View.VISIBLE
        Thread(Runnable {
            Thread.sleep(1500) // opcional, usar apenas quando quiser dar um efeito visual
            var listaFiltrada: List<ToDoListVO> = mutableListOf()

            try {
                listaFiltrada = ToDoListApplication.instance.helperDB?.buscaTarefa(busca)?: mutableListOf()

            }catch (ex: Exception){
                ex.printStackTrace()
            } // usar sempre um try catch par auma chamada de banco

            runOnUiThread {
                adapter = ToDoListAdapter(this,listaFiltrada) {onClickItemRecyclerView(it)}
                recyclerView.adapter = adapter
                progress.visibility = View.GONE
                Toast.makeText(this,"Buscando por $busca", Toast.LENGTH_SHORT).show()
            }
        }).start()

    }

}