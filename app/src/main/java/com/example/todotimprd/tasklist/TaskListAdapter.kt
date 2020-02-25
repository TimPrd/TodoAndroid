package com.example.todotimprd.tasklist

import android.graphics.Color
import android.util.Log
import android.view.*
import android.view.GestureDetector.OnDoubleTapListener
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.todotimprd.MainActivity
import com.example.todotimprd.R
import com.example.todotimprd.Utils.OnSwipeTouchListener
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
                setOnTouchListener(object : OnSwipeTouchListener() {
                    override fun onSwipeLeft() {
                        onDeleteClickListener?.invoke(task)
                    }
                    override fun onSwipeRight() {
                        onArchiveClickListener?.invoke(task)
                    }
                })
            }
        }
    }

    var onDeleteClickListener: ((Task) -> Unit)? = null
    var onEditClickListener: ((Task) -> Unit)? = null
    var onArchiveClickListener: ((Task) -> Unit)? = null


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
