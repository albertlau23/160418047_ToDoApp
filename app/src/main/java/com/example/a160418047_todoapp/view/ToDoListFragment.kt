package com.example.a160418047_todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a160418047_todoapp.R
import com.example.a160418047_todoapp.model.Todo
import com.example.a160418047_todoapp.viewmodel.ListTodoViewModel
import kotlinx.android.synthetic.main.fragment_to_do_list.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
class ToDoListFragment : Fragment() {

    private lateinit var viewModel:ListTodoViewModel
    private val todoListAdapter  = TodoListAdapter(arrayListOf(),{ item -> doClick(item) })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_to_do_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ListTodoViewModel::class.java)
        viewModel.refresh()
        recViewToDo.layoutManager = LinearLayoutManager(context)
        recViewToDo.adapter = todoListAdapter
        fabAddToDo.setOnClickListener {
            val action = ToDoListFragmentDirections.actionAddToDo()
            Navigation.findNavController(it).navigate(action)

        }
        observeViewModel()


    }
    fun doClick(item:Any) {
        viewModel.update(item as Todo)
    }

    fun observeViewModel() {
        viewModel.todoLd.observe(viewLifecycleOwner, Observer {
            todoListAdapter.updateTodoList(it)
            with(txtEmpty){
                visibility=if(it.isEmpty()) View.VISIBLE else View.GONE
            }
        })
    }



}