package com.example.carkharidlo

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.carkharidlo.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.profileFragment,
                R.id.latestCarzFragment,
                R.id.refurbishedCarzFragment,
                R.id.recommendedFragment2,
                R.id.cartFragments,
                R.id.sellCarFragment
            ),
            drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        updateDrawerHeader()
    }

    override fun onStart() {
        super.onStart()
        updateDrawerHeader()
    }

    override fun onResume() {
        super.onResume()
        updateDrawerHeader()
    }

    private fun updateDrawerHeader() {
        val prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE)

        val name = prefs.getString("name", "Guest User")
        val email = prefs.getString("email", "guest@example.com")
        val imagePath = prefs.getString("profileImagePath", null)

        val headerView = binding.navView.getHeaderView(0)

        val headerImage = headerView.findViewById<ImageView>(R.id.nav_profile_image)
        val headerName = headerView.findViewById<TextView>(R.id.nav_profile_name)
        val headerEmail = headerView.findViewById<TextView>(R.id.nav_profile_email)

        headerImage.clipToOutline = true
        headerImage.setImageDrawable(null)

        headerName.text = name
        headerEmail.text = email

        if (!imagePath.isNullOrEmpty()) {
            val file = File(imagePath)

            if (file.exists()) {
                headerImage.setImageURI(null)
                headerImage.setImageURI(Uri.fromFile(file))
            } else {
                headerImage.setImageResource(R.drawable.avaar)
            }
        } else {
            headerImage.setImageResource(R.drawable.avaar)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}