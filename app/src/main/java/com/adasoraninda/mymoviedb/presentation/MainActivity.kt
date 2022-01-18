package com.adasoraninda.mymoviedb.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.adasoraninda.mymoviedb.R
import com.adasoraninda.mymoviedb.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        setSupportActionBar(binding?.toolbar)
        val topLevelDestinations = setOf(R.id.menu_movies, R.id.menu_watchlist, R.id.menu_about)
        appBarConfiguration = AppBarConfiguration(topLevelDestinations, binding?.drawerLayout)

        setupActionBarWithNavController(navController, appBarConfiguration)
        binding?.navigationView?.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            invalidateOptionsMenu()
        }

    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val menuSearch = menu?.findItem(R.id.action_search)
        menuSearch?.isVisible = navController.currentDestination?.id == R.id.menu_movies
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_search) {
            navController.navigate(R.id.nav_to_search_movies)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}