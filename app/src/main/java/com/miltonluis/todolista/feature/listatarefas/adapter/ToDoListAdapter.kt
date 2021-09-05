package com.miltonluis.todolista.feature.listatarefas.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.miltonluis.todolista.R
import com.miltonluis.todolista.feature.listatarefas.model.ToDoListVO
import kotlinx.android.synthetic.main.search_activity.view.*

class ToDoListAdapter(
    private val context: Context,
    private val lista: List<ToDoListVO>,
    private val onClick: ((Int) -> Unit)
) : RecyclerView.Adapter<toDoListViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): toDoListViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.search_activity, parent, false)
        return toDoListViewHolder(view)
    }

    override fun onBindViewHolder(holder: toDoListViewHolder, position: Int) {
        val tarefa = lista[position]
        with(holder.itemView){
            tvItemTarefa.text = tarefa.titulo
            tvItemData.text = tarefa.data
            llItem.setOnClickListener { onClick(tarefa.id) }
        }
    }

    override fun getItemCount(): Int = lista.size

}
class toDoListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)