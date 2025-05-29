package com.users

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class UsersApplication : Application() {
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
    }
}
