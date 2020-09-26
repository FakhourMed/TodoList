package com.fakhour.todolist.ui

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.fakhour.todolist.R
import com.fakhour.todolist.model.TaskModel
import com.fakhour.todolist.model.UserModel

class TasksRecyclerViewAdapter(_context: Context, _tasksList: List<TaskModel>) :
    RecyclerView.Adapter<TasksRecyclerViewAdapter.TaskViewHolder>() {

    val context = _context
    val tasks = _tasksList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tasks.size

    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]

        holder.bind(task)

    }


    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var task: TaskModel

        var cardView: CardView = itemView.findViewById(R.id.task_cardview)
        val titleTV: TextView = itemView.findViewById(R.id.task_title)
        val completedTV: TextView = itemView.findViewById(R.id.task_completed)


        fun bind(_task: TaskModel) {
            this.task = _task

            titleTV.text = task.title
            if (task.completed == "true") {
                completedTV.text = "Task Completed"

                cardView.setCardBackgroundColor(itemView.resources.getColor(R.color.colorTrue))

            }
            else{
                cardView.setCardBackgroundColor(itemView.resources.getColor(R.color.colorFalse))

                completedTV.text = "Incomplete Completed"


            }
        }


    }

}