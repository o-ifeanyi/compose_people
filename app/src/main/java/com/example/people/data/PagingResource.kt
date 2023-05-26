package com.example.people.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.people.model.PaginatedData

class PagingResource<T: Any>(
    private val onLoad: suspend (page: Int) -> PaginatedData<T>?
): PagingSource<Int, T>() {
    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.minus(1) ?: page?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val page = params.key ?: 1
            val res = onLoad(page)

            if (res == null) {
                LoadResult.Error(Exception())
            } else {
                LoadResult.Page(
                    data = res.data,
                    nextKey = if (res.page == res.totalPages) null else res.page + 1,
                    prevKey = null,
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}