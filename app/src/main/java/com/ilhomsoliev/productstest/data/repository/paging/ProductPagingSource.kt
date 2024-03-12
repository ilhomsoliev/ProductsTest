package com.ilhomsoliev.productstest.data.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import coil.network.HttpException
import com.ilhomsoliev.productstest.data.datasource.ProductRemoteDataSource
import com.ilhomsoliev.productstest.data.model.network.mapper.mapFromListModel
import com.ilhomsoliev.productstest.data.repository.INITIAL_LOAD_SIZE_OFFSET
import com.ilhomsoliev.productstest.data.repository.NETWORK_PAGE_SIZE_LIMIT
import com.ilhomsoliev.productstest.domain.model.Product
import java.io.IOException


class ProductPagingSource(
    private val remoteDataSource: ProductRemoteDataSource,
    private val category: String,
) : PagingSource<Int, Product>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        return try {
            val offset = params.key ?: INITIAL_LOAD_SIZE_OFFSET

            val products = remoteDataSource.getProducts(
                offset = offset,
                limit = NETWORK_PAGE_SIZE_LIMIT,
                category = category
            ) ?: return LoadResult.Error(Throwable("Null Problem"))

            val nextKey = if (products.products.isEmpty()) {
                null
            } else {
                offset + (NETWORK_PAGE_SIZE_LIMIT)
            }

            LoadResult.Page(
                data = products.products.mapFromListModel(),
                prevKey = null,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return null // We dont need in offset and limit type
    }

}