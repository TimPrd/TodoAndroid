package com.example.todotimprd.tasklist
import java.io.Serializable

data class Task(val id: String, val title: String = "No title", val description: String = "No description"): Serializable