package com.example.todotimprd.task

import android.app.Activity
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.todotimprd.R
import com.example.todotimprd.tasklist.Task
import com.example.todotimprd.tasklist.TaskListFragment.Companion.EDIT_TASK
import kotlinx.android.synthetic.main.activity_task.*
import java.io.Serializable
import java.time.Duration
import java.util.*

class TaskActivity : AppCompatActivity() {
    companion object {
        const val TASK_KEY = "100"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        val editedTask: Task? = intent.getSerializableExtra(EDIT_TASK) as? Task


        add_title.setText(editedTask?.title)
        add_description.setText(editedTask?.description)

        save_task.text =
            if (editedTask != null) getString(R.string.Edit_Task) else getString(R.string.Add_Task)

        save_task.setOnClickListener {
            if (add_title.text.isEmpty()) {
                Toast.makeText(this, getString(R.string.TITLE_EMPTY), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val task = Task(
                id = editedTask?.id ?: UUID.randomUUID().toString(),
                title = add_title.text.toString(),
                description = add_description.text.toString()
            )
            intent.putExtra(TASK_KEY, task as Serializable)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }


    override fun onBackPressed() {
        setResult(Activity.RESULT_CANCELED, intent)
        finish()
    }

}
