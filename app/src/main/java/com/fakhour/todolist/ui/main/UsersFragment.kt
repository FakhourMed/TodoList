package com.fakhour.todolist.ui.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fakhour.todolist.R
import com.fakhour.todolist.ui.UsersViewModel
import com.fakhour.todolist.utils.UsersViewModelFactory
import com.fakhour.todolist.model.UserModel
import com.fakhour.todolist.repository.Repository
import com.fakhour.todolist.utils.Network


class UsersFragment : Fragment() {

    private var callbacks: Callbacks? = null
    private var adapter: UsersRecyclerViewAdapter? = UsersRecyclerViewAdapter(emptyList())
    private lateinit var viewModel: UsersViewModel
    private lateinit var recyclerView: RecyclerView


    interface Callbacks {
        fun onUserSelected(userId: Int, username: String)
    }

    companion object {
        fun newInstance() = UsersFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as? Callbacks

    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.users_fragment, container, false)
        recyclerView = view.findViewById(R.id.users_recyclerview)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val repository = Repository.get()
        val viewModelFactory = UsersViewModelFactory(repository)
        viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory).get(UsersViewModel::class.java)


        if (Network.isNetworkConnected(requireContext())) {

            viewModel.getUsers()


            viewModel.myResponseUsers.observe(this, Observer { response ->
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

        viewModel.UsersListLiveData.observe(
            viewLifecycleOwner,
            Observer { users ->
                users?.let {

                    setUpUsersRecyclerView(users)

                }
            }
        )

    }

    fun setUpUsersRecyclerView(users: List<UserModel>) {

        val userRecyclerViewAdapter = UsersRecyclerViewAdapter(users)
        recyclerView.adapter = userRecyclerViewAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

    }


    inner class UsersRecyclerViewAdapter(_usersList: List<UserModel>) :
        RecyclerView.Adapter<UserViewHolder>() {

        val users = _usersList

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false)
            return UserViewHolder(view)
        }

        override fun getItemCount(): Int {
            return users.size
        }

        override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
            val user = users[position]

            holder.bind(user)

        }

    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private lateinit var user: UserModel


        val nameTV: TextView = itemView.findViewById(R.id.name)
        val userNameTV: TextView = itemView.findViewById(R.id.username)
        val emailTV: TextView = itemView.findViewById(R.id.email)


        init {
            itemView.setOnClickListener(this)
        }

        fun bind(_user: UserModel) {
            this.user = _user

            nameTV.text = user.name
            userNameTV.text = user.username
            emailTV.text = user.email

        }

        override fun onClick(v: View) {

            callbacks?.onUserSelected(user.id, user.name)
        }
    }
}