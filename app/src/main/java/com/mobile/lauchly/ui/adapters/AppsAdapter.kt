package com.mobile.lauchly.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mobile.lauchly.R
import com.mobile.lauchly.data.AppInfo
import kotlin.collections.filter
import kotlin.collections.toList
import kotlin.text.contains
import kotlin.text.isEmpty
import kotlin.text.lowercase

class AppsAdapter(
    private var apps: List<AppInfo>,
    private val onAppClick: (AppInfo) -> Unit
) : RecyclerView.Adapter<AppsAdapter.ViewHolder>() {

    private var filteredApps = apps.toList()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardView: CardView = view.findViewById(R.id.cardView)
        val iconView: ImageView = view.findViewById(R.id.appIcon)
        val labelView: TextView = view.findViewById(R.id.appLabel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_app, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val app = filteredApps[position]
        holder.iconView.setImageDrawable(app.icon)
        holder.labelView.text = app.label
        holder.cardView.setOnClickListener { onAppClick(app) }
    }

    override fun getItemCount() = filteredApps.size

    fun filter(query: String) {
        filteredApps = if (query.isEmpty()) {
            apps.toList()
        } else {
            apps.filter { 
                it.label.lowercase().contains(query.lowercase()) 
            }
        }
        notifyDataSetChanged()
    }

    fun updateApps(newApps: List<AppInfo>) {
        this.apps = newApps
        this.filteredApps = newApps.toList()
        notifyDataSetChanged()
    }
} 
