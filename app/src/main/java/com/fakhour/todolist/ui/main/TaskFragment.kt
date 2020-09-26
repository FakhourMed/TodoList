package com.fakhour.todolist.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fakhour.todolist.R
import com.fakhour.todolist.model.TaskModel
import com.fakhour.todolist.repository.Repository
import com.fakhour.todolist.ui.TaskViewModel
import com.fakhour.todolist.ui.TasksRecyclerViewAdapter
import com.fakhour.todolist.utils.Network
import com.fakhour.todolist.utils.TaskViewModelFactory
import com.google.android.material.snackbar.Snackbar


private const val ARG_USER_ID = "user_id"
private const val ARG_USERNAME = "username"



class TaskFragment : Fragment() {

    companion object {
        fun newInstance(userId: Int, username: String): TaskFragment {

            val args = Bundle().apply {
                putInt(ARG_USER_ID, userId)
                putString(ARG_USERNAME, username)
            }
            return TaskFragment().apply {
                arguments = args
            }

        }
    }



    private lateinit var viewModel: TaskViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var usernameTV: TextView




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view= inflater.inflate(R.layout.task_fragment, container, false)
        recyclerView = view.findViewById(R.id.tasks_recyclerview)
        usernameTV=view.findViewById(R.id.task_user_name)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val repository = Repository.get()
        val viewModelFactory = TaskViewModelFactory(repository)
        viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory).get(TaskViewModel::class.java)

        // Affichage du nom de l'utilisateur sur le haut de la page
        usernameTV.text=arguments?.getString(ARG_USERNAME)


        // ne lance la requete retrofit qu'en présence de connexion sinon utiliser la base de données
        if (Network.isNetworkConnected(requireContext())) {

        viewModel.getCustomTask(arguments!!.getInt(ARG_USER_ID))
        viewModel.myResponseCustomTask.observe(this, Observer { response ->
            if (response.isSuccessful) {
                Log.d("RESPONSE", "Data downloaded correctly!!")


            } else {
                Log.d("RESPONSE", response.errorBody().toString())

            }
        })
    }


    }

    override fun onStart() {
        super.onStart()
        // ne lance la requete retrofit qu'en présence de connexion sinon utiliser la base de données
        viewModel.getCustomTasksFromDb(arguments!!.getInt(ARG_USER_ID))
        viewModel.tasksListLiveData.observe(
            viewLifecycleOwner,
            Observer { tasks ->
                tasks?.let {

                    setUpTaskRecyclerView(tasks)

                }
            }
        )



    }

    private fun setUpTaskRecyclerView(tasks: List<TaskModel>) {
        val taskRecyclerViewAdapter = TasksRecyclerViewAdapter(requireContext(), tasks)
        recyclerView.adapter = taskRecyclerViewAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

    }

}