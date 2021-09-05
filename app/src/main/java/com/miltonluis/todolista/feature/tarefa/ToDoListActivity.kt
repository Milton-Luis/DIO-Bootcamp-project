package com.miltonluis.todolista.feature.tarefa

import android.os.Bundle
import android.view.View
import com.miltonluis.todolista.R
import com.miltonluis.todolista.application.ToDoListApplication
import com.miltonluis.todolista.bases.BaseActivity
import com.miltonluis.todolista.feature.listatarefas.model.ToDoListVO
import kotlinx.android.synthetic.main.tarefa_activity.*
import kotlinx.android.synthetic.main.tarefa_activity.toolBar

class ToDoListActivity : BaseActivity() {
    
    private var toDoList_id: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tarefa_activity)
        setupToolBar(toolBar ,"Nova Tarefa", true)
        setupToDoList()
        btnSalvarTarefa.setOnClickListener { onClickNovaTarefa() }
    }
    
    private fun setupToDoList(){

        toDoList_id = intent.getIntExtra("index", -1)
        if (toDoList_id == -1){
            btnExcluirTarefa.visibility = View.GONE
            return
        }
    }

    private fun onClickNovaTarefa(){
        val titulo = etNovaTarefa.text.toString()
        val data = etDataTarefa.text.toString()
        val descricao = etDescricaoTarefa.text.toString()
        val toDoList = ToDoListVO(
            toDoList_id,
            titulo,
            data,
            descricao
        )
        progress.visibility = View.VISIBLE
        Thread(Runnable {
            Thread.sleep(1500)
            if(toDoList_id == -1) {
                ToDoListApplication.instance.helperDB?.nova_tarefa(toDoList)
            }else{
                ToDoListApplication.instance.helperDB?.atualizaTarefa(toDoList)
            }
            runOnUiThread {
                finish()
                progress.visibility = View.GONE
            }
        }).start()

    }

    fun onClickExcluirTarefa(view: View) {

        if(toDoList_id > -1){
            progress.visibility = View.VISIBLE
            Thread(Runnable {
                Thread.sleep(1500)
                ToDoListApplication.instance.helperDB?.deletarTarefa(toDoList_id)
                runOnUiThread {
                    finish()
                    progress.visibility = View.GONE
                }
            }).start()
        }
    }
}