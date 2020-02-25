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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todotimprd.R
import com.example.todotimprd.task.TaskActivity
import kotlinx.android.synthetic.main.fragment_task_list.*
import java.io.Serializable


class TaskListFragment : Fragment() {

    private val taskList = mutableListOf(
        Task(id = "id_1", title = "Task 1", description = "description 1"),
        Task(id = "id_2", title = "Task 2"),
        Task(id = "id_3", title = "Task 3")
    )

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
        recycler_view.adapter = TaskListAdapter(taskList)
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
            this.taskList.remove(it)
            adapter.notifyDataSetChanged()
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val task = data!!.getSerializableExtra(TaskActivity.TASK_KEY) as Task
        /*ADD A TASK*/
        if (requestCode == ADD_TASK_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK)
                this.taskList.add(task)
            else
                Toast.makeText(context, getString(R.string.ERROR_CREATE_TASK), Toast.LENGTH_SHORT).show()
        }
        /*EDIT A TASK*/
        if (requestCode == EDIT_TASK_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val index = this.taskList.indexOfFirst {
                    it.id == task.id
                }
                this.taskList[index] = task
            }
            else
                Toast.makeText(context, getString(R.string.ERROR_EDIT_TASK), Toast.LENGTH_SHORT).show()

        }
        recycler_view.adapter?.notifyDataSetChanged()
    }


    companion object {
        const val ADD_TASK_REQUEST_CODE = 1
        const val EDIT_TASK_REQUEST_CODE = 2
        const val EDIT_TASK = "EDIT_TASK"

    }

}

