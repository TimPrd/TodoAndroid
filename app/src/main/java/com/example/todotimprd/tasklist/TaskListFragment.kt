package com.example.todotimprd.tasklist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todotimprd.R
import com.example.todotimprd.network.Api
import com.example.todotimprd.network.TasksRepository
import com.example.todotimprd.task.TaskActivity
import kotlinx.android.synthetic.main.fragment_task_list.*
import kotlinx.android.synthetic.main.item_task.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.io.Serializable


class TaskListFragment : Fragment() {
    private val coroutineScope = MainScope()


    private val tasksRepository = TasksRepository()
    private val tasks = mutableListOf<Task>()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_task_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //view.findViewById<RecyclerView>(R.id.recycler_view)
        recycler_view.layoutManager = LinearLayoutManager(activity)
        recycler_view.adapter = TaskListAdapter(this.tasks)
        val adapter: TaskListAdapter = recycler_view.adapter as TaskListAdapter


        addButton.setOnClickListener{
            val intent = Intent(context, TaskActivity::class.java)
            startActivityForResult(intent, ADD_TASK_REQUEST_CODE)
        }

        adapter.onEditClickListener = {
            val intent = Intent(context, TaskActivity::class.java)
            intent.putExtra(EDIT_TASK, it as Serializable)
            startActivityForResult(intent, EDIT_TASK_REQUEST_CODE)
        }

        adapter.onDeleteClickListener = {
            lifecycleScope.launch {
                tasksRepository.delete(it)
            }
        }

        adapter.onArchiveClickListener = {
            lifecycleScope.launch {
                tasksRepository.archive(it)
            }
        }



        tasksRepository.taskList.observe(this, Observer {
            tasks.clear()
            tasks.addAll(it)
            adapter.notifyDataSetChanged()
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val task = data!!.getSerializableExtra(TaskActivity.TASK_KEY) as? Task

        /*ADD A TASK*/
        if (requestCode == ADD_TASK_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK && task != null) {
                coroutineScope.launch {
                    tasksRepository.create(task)
                }
            } else {
                Toast.makeText(context, getString(R.string.ERROR_CREATE_TASK), Toast.LENGTH_SHORT)
                    .show()
            }
        }
        /*EDIT A TASK*/
        if (requestCode == EDIT_TASK_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK && task != null) {
                coroutineScope.launch {
                    tasksRepository.updateTask(task)
                }
            }
            else {
                Toast.makeText(context, getString(R.string.ERROR_EDIT_TASK), Toast.LENGTH_SHORT)
                    .show()
            }
        }
        recycler_view.adapter?.notifyDataSetChanged()
    }


    override fun onResume() {
        super.onResume()
        coroutineScope.launch {
            val userInfo = Api.userService.getInfo().body()!!
            user_info.text = "${userInfo.firstName} ${userInfo.lastName}"
        }
        lifecycleScope.launch {
            tasksRepository.refresh()
        }
    }


    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }

    companion object {
        const val ADD_TASK_REQUEST_CODE = 1
        const val EDIT_TASK_REQUEST_CODE = 2
        const val EDIT_TASK = "EDIT_TASK"

    }

}

