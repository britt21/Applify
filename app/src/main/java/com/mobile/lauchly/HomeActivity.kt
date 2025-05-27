package com.mobile.lauchly

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.SearchView
import android.content.pm.ResolveInfo
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.mobile.lauchly.data.AppInfo
import com.mobile.lauchly.databinding.ActivityHomeBinding
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
        setContentView(binding.root)

        appsAdapter = AppsAdapter(allApps) { appInfo ->
            launchApp(appInfo.packageName)
        }
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
        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)

        val activities = packageManager.queryIntentActivities(intent, 0)
        allApps.clear()

        for (resolveInfo in activities) {
            val appInfo = AppInfo(
                label = resolveInfo.loadLabel(packageManager).toString(),
                packageName = resolveInfo.activityInfo.packageName,
                icon = resolveInfo.loadIcon(packageManager)
            )
            if (appInfo.packageName != packageName) {
                allApps.add(appInfo)
                appsRecyclerView.adapter?.notifyItemInserted(allApps.size - 1)
            }
        }

        allApps.sortBy { it.label }
        appsAdapter.updateApps(allApps)
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
