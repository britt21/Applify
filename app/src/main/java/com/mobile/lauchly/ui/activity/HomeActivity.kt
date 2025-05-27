package com.mobile.lauchly.ui.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobile.lauchly.R
import com.mobile.lauchly.data.AppInfo
import com.mobile.lauchly.databinding.ActivityHomeBinding
import com.mobile.lauchly.ui.adapters.AppsAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.collections.sortBy
import kotlin.let


class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding

    private lateinit var appsRecyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var appsAdapter: AppsAdapter
    private var allApps = mutableListOf<AppInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        enableEdgeToEdge()

        setContentView(binding.root)


        appsRecyclerView = findViewById(R.id.appsRecyclerView)
        searchView = findViewById(R.id.searchView)

        setupRecyclerView()
        setupSearch()
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
        lifecycleScope.launch(Dispatchers.IO) {
            val intent = Intent(Intent.ACTION_MAIN, null).apply {
                addCategory(Intent.CATEGORY_LAUNCHER)
            }

            val activities = packageManager.queryIntentActivities(intent, PackageManager.GET_META_DATA)
            val tempList = mutableListOf<AppInfo>()

            for (resolveInfo in activities) {
                val appInfo = AppInfo(
                    label = resolveInfo.loadLabel(packageManager).toString(),
                    packageName = resolveInfo.activityInfo.packageName,
                    icon = resolveInfo.loadIcon(packageManager)
                )
                if (true) {
                    tempList.add(appInfo)
                }
            }

            tempList.sortBy { it.label }

            runOnUiThread {
                allApps.clear()
                allApps.addAll(tempList)
                appsAdapter.updateApps(allApps)
                binding.probar.visibility = View.GONE
            }
        }
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
    }
}
