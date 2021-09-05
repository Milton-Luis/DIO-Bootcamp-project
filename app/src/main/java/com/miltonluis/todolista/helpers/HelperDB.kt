package com.miltonluis.todolista.helpers

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.miltonluis.todolista.feature.listatarefas.model.ToDoListVO

class HelperDB (
    context: Context?
    ): SQLiteOpenHelper(context, DB_NAME, null, VERSION) {

    companion object{
        private val DB_NAME:String = "ToDoList.db"
        private val VERSION:Int = 1


        private val TABLE_NAME = "Tarefa"
        private val COLUMN_ID = "id"
        private val COLUMN_TITLE = "título"
        private val COLUMN_DATE = "data"
        private val COLUMN_DESCRIPTION = "descricao"
    }

    private fun create_db():String{


        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER NOT NULL," +
                "$COLUMN_TITLE TEXT NOT NULL," +
                "$COLUMN_DATE TEXT NOT NULL," +
                "$COLUMN_DESCRIPTION TEXT NOT NULL," +
                "" +
                "PRIMARY KEY($COLUMN_ID AUTOINCREMENT)" +
                ")"
        return CREATE_TABLE

    }

    private fun drop_table():String{
        val DROP_TABLE ="DROP TABLE IF EXISTS $TABLE_NAME"
        return DROP_TABLE
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(create_db())
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion != newVersion){
            db?.execSQL(drop_table())
        }
    }

    fun nova_tarefa(tarefa: ToDoListVO){
        val db = writableDatabase?: return

        var content = ContentValues()
        content.put(COLUMN_TITLE, tarefa.titulo)
        content.put(COLUMN_DATE, tarefa.data)
        content.put(COLUMN_DESCRIPTION, tarefa.descricao)

        db.insert(TABLE_NAME, null, content)

        db.close()

    }

    fun buscaTarefa(busca: String, isBuscaPorID: Boolean = false) : List<ToDoListVO>{
//        salvarContato(ContatosVO(0, "TESTE3", "TESTE3"))
        val db = readableDatabase?: return mutableListOf()
        var lista = mutableListOf<ToDoListVO>()

        var where: String? = null
        var args: Array<String> = arrayOf()

        if (isBuscaPorID){
            where = "$COLUMN_ID = ?"
            args = arrayOf("$busca")
        }else{
            where = "$COLUMN_TITLE LIKE ?"
            args = arrayOf("%$busca%")
        }

        var cursor = db.query(TABLE_NAME, null, where, args, null, null, null)

        if (cursor == null){
            db.close()
            return mutableListOf()
        }

        while(cursor.moveToNext()){
            var contato = ToDoListVO(
                cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)),
                cursor.getString(cursor.getColumnIndex(COLUMN_DATE)),
                cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION))
            )
            lista.add(contato)
        }
        return lista
    }

    fun atualizaTarefa(tarefa: ToDoListVO){
        val db = writableDatabase?: return
        var content = ContentValues()
        val where = "id = ?"
        val args = arrayOf("${tarefa.id}")
        content.put(COLUMN_TITLE, tarefa.titulo)
        content.put(COLUMN_DATE, tarefa.data)
        content.put(COLUMN_DESCRIPTION, tarefa.descricao)
        db.update(TABLE_NAME, content,where, args)
        db.close()
    }

    fun deletarTarefa(id: Int){
        val db = writableDatabase?: return
        val where = "id = ?"
        val arg = arrayOf("$id")
        db.delete(TABLE_NAME, where, arg)
        db.close()
    }
}


