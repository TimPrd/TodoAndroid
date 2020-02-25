package com.example.todotimprd.tasklist

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todotimprd.R
import kotlinx.android.synthetic.main.item_task.view.*

class TaskListAdapter(private val taskList: List<Task>) : RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {
    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(task: Task) {
            itemView.apply {
                task_title.text = task.title
                task_description.text = task.description
                deleteButton.setOnClickListener {
                    onDeleteClickListener?.invoke(task)
                }
                editButton.setOnClickListener {
                    onEditClickListener?.invoke(task)
                }

            }


        }
    }

    var onDeleteClickListener: ((Task) -> Unit)? = null
    var onEditClickListener: ((Task) -> Unit)? = null

    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return this.taskList.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(this.taskList[position])
    }




}
