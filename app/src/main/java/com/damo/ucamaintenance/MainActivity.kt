package com.damo.ucamaintenance

import android.os.Bundle
import android.view.Menu
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.damo.ucamaintenance.databinding.ActivityMainBinding
import androidx.recyclerview.widget.GridLayoutManager
import com.damo.ucamaintenance.adapter.PcAdapter
import com.damo.ucamaintenance.entities.PcEntity
import java.util.concurrent.LinkedBlockingQueue

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var mAdapter: PcAdapter
    private lateinit var mGridLayout: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.floatingActionButton.setOnClickListener {
            launchEditFragment()
        }
        setupRecyclerView()

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun launchEditFragment() {
        // Crear instancia del fragmento
        val fragment = EditPcFragment()

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        // Activity donde se lanzará el fragmento
        fragmentTransaction.replace(R.id.drawer_layout, fragment)
        // Evitar que se salga de la app al pulsar retroceso
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

        // Ocultar floating action button
        hideFab(true)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    private fun setupRecyclerView() {
        mAdapter = PcAdapter(mutableListOf(), this)
        mGridLayout = GridLayoutManager(this, 2)
        getPcs()

        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = mGridLayout
            adapter = mAdapter
        }
    }

    private fun getPcs() {
        val queue = LinkedBlockingQueue<MutableList<PcEntity>>()
        Thread {
            val pcs = PcApplication.database.pcDao().getAllPcs()
            queue.add(pcs)
        }.start()

        mAdapter.setPcs(queue.take())
    }

     fun onDeletePc(pcEntity: PcEntity) {
        val queue = LinkedBlockingQueue<PcEntity>()
        Thread {
            PcApplication.database.pcDao().deletePc(pcEntity)
            queue.add(pcEntity)
        }.start()

        mAdapter.delete(queue.take())
    }

     fun onClick(pcEntity: PcEntity) {
        TODO("Not yet implemented")
    }

     fun addPc(pcEntity: PcEntity) {
        mAdapter.add(pcEntity)
    }

     fun updatePc(pcEntity: PcEntity) {
        TODO("Not yet implemented")
    }

     private fun hideFab(isVisible: Boolean) {
        if (isVisible) binding.floatingActionButton.show() else binding.floatingActionButton.hide()
    }

}
