package com.valkotova.testassignment

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.valkotova.testassignment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home,
//                R.id.navigation_favorites,
//                R.id.navigation_cart,
//                R.id.navigation_comment,
//                R.id.navigation_profile
//            )
//        )

        setupWithNavController(navView as NavigationBarView, navController)
        //setupActionBarWithNavController(navController, appBarConfiguration)
        //navView.setupWithNavController(navController)
        navView.setOnApplyWindowInsetsListener { v, insets ->
            insets
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id in listOf(
                    R.id.navigation_home,
                    R.id.navigation_favorites,
                    R.id.navigation_cart,
                    R.id.navigation_comment,
                    R.id.navigation_profile,
                    R.id.navigation_product
            )) {
                navView.visibility = View.VISIBLE
            }
            else
                navView.visibility = View.GONE
        }
    }
}