package com.example.todotimprd.task

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todotimprd.R
import com.example.todotimprd.tasklist.Task
import kotlinx.android.synthetic.main.activity_task.*
import java.util.*

class TaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        save_task.setOnClickListener {
            Task(id = UUID.randomUUID().toString(), title = "New Task !")
            putExta
        }
    }
}
