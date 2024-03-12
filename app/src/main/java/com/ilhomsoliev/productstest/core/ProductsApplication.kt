package com.ilhomsoliev.productstest.core

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import com.ilhomsoliev.productstest.data.ApiClient
import com.ilhomsoliev.productstest.data.datasource.ProductRemoteDataSource
import com.ilhomsoliev.productstest.data.datasource.ProductRemoteDataSourceImpl
import com.ilhomsoliev.productstest.data.repository.ProductRepositoryImpl
import com.ilhomsoliev.productstest.domain.repository.ProductRepository
import com.ilhomsoliev.productstest.domain.usecase.GetProductsByQueryUseCase
import com.ilhomsoliev.productstest.domain.usecase.GetProductsUseCase
import com.ilhomsoliev.productstest.presentation.home.HomeScreenViewModel
import com.ilhomsoliev.productstest.presentation.search.SearchScreenViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

class ProductsApplication : Application(), ImageLoaderFactory {
    override fun onCreate() {
        super.onCreate()
        initDI()
    }

    private fun initDI() {
        val viewModelModule = module(override = true) {
            viewModel { HomeScreenViewModel(get()) }
            viewModel { SearchScreenViewModel(get()) }
        }
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@ProductsApplication)
            modules(
                listOf(
                    module {
                        single { ApiClient() }
                        single<ProductRemoteDataSource> { ProductRemoteDataSourceImpl(get()) }
                        single<ProductRepository> { ProductRepositoryImpl(get()) }
                        single { GetProductsUseCase(get()) }
                        single { GetProductsByQueryUseCase(get()) }
                    },
                    viewModelModule
                )
            )
        }
    }

    override fun newImageLoader() = ImageLoader
        .Builder(this)
        .respectCacheHeaders(false)
        .diskCache {
            DiskCache.Builder()
                .directory(
                    this.cacheDir.resolve(
                        "image_cache"
                    )
                )
                .maxSizePercent(0.25)
                .build()
        }
        .memoryCache {
            MemoryCache.Builder(this)
                .maxSizePercent(0.25)
                .build()
        }
        .build()
}