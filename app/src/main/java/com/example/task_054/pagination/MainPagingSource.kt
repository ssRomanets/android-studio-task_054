package com.example.task_054.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.task_054.db.FilmItemDao
import com.example.task_054.models.FilmItem
import kotlinx.coroutines.delay

class MainPagingSource(
    private val dao: FilmItemDao
) : PagingSource<Int, FilmItem>(){
    override fun getRefreshKey(state: PagingState<Int, FilmItem>): Int? {
        return state.anchorPosition?.let {anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        } as Int?
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FilmItem> {
        val page = params.key ?: 0
        return try {
            val entities = dao.getPageList(params.loadSize, page*params.loadSize)
            if (page != 0) delay(1000)
            LoadResult.Page(
                data = entities,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (entities.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}