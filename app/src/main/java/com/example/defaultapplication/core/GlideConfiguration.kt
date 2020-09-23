package com.example.defaultapplication.core

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class GlideConfiguration : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        // set memory cache
        val calculator = MemorySizeCalculator.Builder(context)
                .setMemoryCacheScreens(2f)
                .build()
        builder.setMemoryCache(LruResourceCache(calculator.memoryCacheSize.toLong()))

        // set disk cache
        val cacheSize100MegaBytes = 1024 * 1024 * 100
        builder.setDiskCache(InternalCacheDiskCacheFactory(context, cacheSize100MegaBytes.toLong()))
    }
}