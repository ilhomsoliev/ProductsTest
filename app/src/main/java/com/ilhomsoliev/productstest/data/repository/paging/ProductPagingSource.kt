package com.ilhomsoliev.productstest.data.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import coil.network.HttpException
import com.ilhomsoliev.productstest.data.datasource.MovieRemoteDataSource
import com.ilhomsoliev.productstest.data.model.network.mapper.mapFromListModel
import com.ilhomsoliev.productstest.domain.model.Product
import java.io.IOException

const val NETWORK_PAGE_SIZE = 20
private const val INITIAL_LOAD_SIZE = 5

class MoviePagingSource(
    private val remoteDataSource: MovieRemoteDataSource,
    private val query: String = ""
) : PagingSource<Int, Product>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        return try {
            val position = params.key ?: INITIAL_LOAD_SIZE
            val offset =
                if (params.key != null) ((position - 1) * NETWORK_PAGE_SIZE) + 1 else INITIAL_LOAD_SIZE

            val products = remoteDataSource.getProducts(
                offset = offset,
                limit = params.loadSize,
                query = query
            ) ?: return LoadResult.Error(Throwable("Null Problem"))

            val nextKey = if (products.products.isEmpty()) {
                null
            } else {
                position + (params.loadSize / NETWORK_PAGE_SIZE)
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
        return state.anchorPosition
    }

}