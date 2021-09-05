package com.miltonluis.todolista.singleton

import com.miltonluis.todolista.feature.listatarefas.model.ToDoListVO

object ToDoListSingleton {
    var lista: MutableList<ToDoListVO> = mutableListOf()
}