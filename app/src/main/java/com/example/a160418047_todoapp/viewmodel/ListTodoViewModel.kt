package com.example.a160418047_todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.example.a160418047_todoapp.model.Todo
import com.example.a160418047_todoapp.model.TodoDatabase
import com.example.a160418047_todoapp.util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ListTodoViewModel(application: Application) : AndroidViewModel(application),
    CoroutineScope {
    val todoLd = MutableLiveData<List<Todo>>()
    val todoLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    private var job = Job()
    fun refresh() {
        loadingLD.value = true
        todoLoadErrorLD.value = false
        launch {
            val db = Room.databaseBuilder(
                getApplication(),
                TodoDatabase::class.java, "newtododb"
            ).build()
            todoLd.value = db.todoDao().selectAllTodo()
        }
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main


    fun clearTask(todo: Todo) {
        launch {
//            val db = Room.databaseBuilder(
//                getApplication(),
//                TodoDatabase::class.java, "newtododb"
//            ).build()
            val db = buildDb(getApplication())
            db.todoDao().deleteTodo(todo)

            todoLd.value = db.todoDao().selectAllTodo()
        }
    }
    fun update(todo: Todo) {
        launch {
            val db = buildDb(getApplication())
            if(todo.is_done==0)
            db.todoDao().isdone(1, todo.uuid)else db.todoDao().isdone(0,todo.uuid)
            todoLd.value = db.todoDao().selectAllTodo()
        }
    }


}
