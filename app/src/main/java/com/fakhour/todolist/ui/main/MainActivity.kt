package com.fakhour.todolist.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.fakhour.todolist.R
import com.fakhour.todolist.utils.Network
import com.fakhour.todolist.utils.NetworkConnection
import com.google.android.material.snackbar.Snackbar
import java.util.*

class MainActivity : AppCompatActivity(), UsersFragment.Callbacks {

    private lateinit var coordinatorLayout: CoordinatorLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_activity)





        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, UsersFragment.newInstance())
                .commitNow()
        }
        coordinatorLayout = findViewById(R.id.coordinator_layout)

        val networkConnection = NetworkConnection(applicationContext)
        networkConnection.observe(this, androidx.lifecycle.Observer { isConnected ->
            if (!isConnected) {
                val snackbar = Snackbar.make(
                    coordinatorLayout,
                    "Vérifier votre connexion",
                    Snackbar.LENGTH_INDEFINITE
                )
                snackbar.show()
            } else {
                val snackbar = Snackbar.make(
                    coordinatorLayout,
                    "Connexion rétablie",
                    Snackbar.LENGTH_LONG
                )
                snackbar.show()
            }

        })


    }

    override fun onUserSelected(userId: Int, username: String) {
        val fragment = TaskFragment.newInstance(userId, username)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()

    }
}