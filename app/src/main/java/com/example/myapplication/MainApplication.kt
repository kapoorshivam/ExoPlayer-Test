package com.example.myapplication

import android.app.Application
import android.util.Log
import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import java.io.File

class MainApplication : Application() {

    // Required for caching in ExoPlayer
    companion object {
        lateinit var simpleCache: SimpleCache
        private lateinit var leastRecentlyUsedCacheEvictor: LeastRecentlyUsedCacheEvictor
        private lateinit var standaloneDatabaseProvider: StandaloneDatabaseProvider
        private const val exoCacheSize: Long = 10 * 1024 * 1024 // Setting cache size to be ~ 20 MB
    }
    override fun onCreate() {
        super.onCreate()
        Log.i("OnCreate", "Created")
        // Initialize the cache
        leastRecentlyUsedCacheEvictor = LeastRecentlyUsedCacheEvictor(exoCacheSize)
        standaloneDatabaseProvider = StandaloneDatabaseProvider(this)
        simpleCache = SimpleCache(
            File(this.cacheDir, "media"),
            leastRecentlyUsedCacheEvictor,
            standaloneDatabaseProvider
        )
        Log.i("OnCreate", "init")
    }
}