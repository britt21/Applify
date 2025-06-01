package com.mobile.lauchly.repository

import android.content.Context
import com.mobile.lauchly.data.AppInfo

interface UserRepository {

    suspend fun getAllApps(context: Context): List<AppInfo>

}