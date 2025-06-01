package com.mobile.lauchly.repository

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import com.mobile.lauchly.data.AppInfo

class UserRepositoryImpl: UserRepository {



    override suspend fun getAllApps(context: Context): List<AppInfo> {
        val intent = Intent(Intent.ACTION_MAIN, null).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }
        val activities = context.packageManager.queryIntentActivities(intent, PackageManager.GET_META_DATA)
        return activities.map { resolveInfo ->
            AppInfo(
                label = resolveInfo.loadLabel(context.packageManager).toString(),
                packageName = resolveInfo.activityInfo.packageName,
                icon = resolveInfo.loadIcon(context.packageManager)
            )
        }.sortedBy { it.label }

    }
}