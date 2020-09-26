package com.fakhour.todolist.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.ViewModelProvider
import com.fakhour.todolist.R
import com.fakhour.todolist.utils.NetworkConnection
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), UsersFragment.Callbacks {

    private lateinit var coordinatorLayout: CoordinatorLayout
    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_activity)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, UsersFragment.newInstance())
                .commitNow()
        }
        coordinatorLayout = findViewById(R.id.coordinator_layout)


        // Etat de connexion
        val networkConnection = NetworkConnection(applicationContext)
        observeNetworkConnection(networkConnection)


    }

    // Lancer TaskFragment selon L'utilisateur Choisi
    override fun onUserSelected(userId: Int, username: String) {
        val fragment = TaskFragment.newInstance(userId, username)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()

    }

    fun observeNetworkConnection(_networkConnection: NetworkConnection) {

        _networkConnection.observe(this, androidx.lifecycle.Observer { isConnected ->
            if (!isConnected) {

                viewModel.alreadyConnected++

                val snackbar = Snackbar.make(
                    coordinatorLayout,
                    "Vérifier votre connexion",
                    Snackbar.LENGTH_INDEFINITE
                )
                snackbar.show()
            } else {
                    // Pour ne pas afficher le snackbar dès le premier lancement
                if (viewModel.alreadyConnected != -1) {
                    val snackbar = Snackbar.make(
                        coordinatorLayout,
                        "Connexion rétablie",
                        Snackbar.LENGTH_LONG
                    )
                    snackbar.show()
                    viewModel.alreadyConnected = -1
                }
            }

        })

    }
}