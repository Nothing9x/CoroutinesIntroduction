package com.toinx.corotinepractice

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.lifecycleScope
import com.toinx.corotinepractice.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)




        val numMax = 10000
        //Create too many coroutines
        try {
            val startTime = System.currentTimeMillis()
            for (i in 1..numMax) {
                GlobalScope.launch {
                    val a = 0
                }
            }

            Log.i(TAG, "Create $numMax coroutines in ${System.currentTimeMillis() - startTime}")
        } catch (oom: OutOfMemoryError) {
            Log.i(TAG, "Out of memory $oom")
        }

        //Create too many Thread
        try {
            val startTime = System.currentTimeMillis()
            for (i in 1..numMax) {
                thread {
                    val a = 0
                }
            }

            Log.i(TAG, "Create $numMax thread in ${System.currentTimeMillis() - startTime}")
        } catch (oom: OutOfMemoryError) {
            Log.i(TAG, "Out of memory $oom")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    companion object {
        const val TAG = "CoroutinesPractice"
    }
}

