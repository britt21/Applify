package com.mobile.lauchly.ui.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobile.lauchly.R
import com.mobile.lauchly.cache.DataManager
import com.mobile.lauchly.cache.STEP
import com.mobile.lauchly.data.AppInfo
import com.mobile.lauchly.databinding.ActivityHomeBinding
import com.mobile.lauchly.repository.UserRepositoryImpl
import com.mobile.lauchly.ui.adapters.AppsAdapter
import com.mobile.lauchly.ui.viewmodels.HomeViewModelFactory
import com.mobile.lauchly.ui.viewmodels.HomeviewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.collections.sortBy
import kotlin.let


class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding
    lateinit var dataManager: DataManager
    lateinit var homeviewModel: HomeviewModel

    private lateinit var appsRecyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var appsAdapter: AppsAdapter
    private var allApps = mutableListOf<AppInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        enableEdgeToEdge()

        setContentView(binding.root)

        val repository = UserRepositoryImpl()
        val factory = HomeViewModelFactory(repository)
        homeviewModel = ViewModelProvider(this, factory).get(HomeviewModel::class.java)



        appsRecyclerView = findViewById(R.id.appsRecyclerView)
        searchView = findViewById(R.id.searchView)

        setupRecyclerView()
        setupSearch()
        dataManager = DataManager(this)
    }





    private fun setupRecyclerView() {
        appsAdapter = AppsAdapter(allApps) { appInfo ->
            launchApp(appInfo.packageName)
        }
        appsRecyclerView.layoutManager = GridLayoutManager(this, 4)
        appsRecyclerView.adapter = appsAdapter
    }

    private fun loadApps() {
        allApps.clear()
        binding.probar.visibility = View.VISIBLE

        homeviewModel.allApps.observe(this) { apps ->
            allApps.clear()
            allApps.addAll(apps)
            appsAdapter.updateApps(allApps)
            binding.probar.visibility = View.GONE

            homeviewModel.allApps.removeObservers(this)
        }

        homeviewModel.loadApps(this)

    }


    private fun setupSearch() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = true

            override fun onQueryTextChange(newText: String?): Boolean {
                appsAdapter.filter(newText ?: "")
                return true
            }
        })
    }

    private fun launchApp(packageName: String) {
        val intent = packageManager.getLaunchIntentForPackage(packageName)
        intent?.let {
            startActivity(it)
        }
    }

    override fun onResume() {
        super.onResume()
       loadApps()
        dataManager.saveData(STEP, "4")

        Log.d("HomeActivity", "CALSTEP: onResume: ${dataManager.readData(STEP,"")}")
    }

    override fun onBackPressed() {
        super.onBackPressed()

    }
}
