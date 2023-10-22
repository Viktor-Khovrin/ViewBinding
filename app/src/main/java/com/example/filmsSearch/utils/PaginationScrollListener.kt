package com.example.filmsSearch.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScrollListener (var layoutManager: LinearLayoutManager): RecyclerView.OnScrollListener(){
    abstract fun isLastPage(): Boolean
    abstract fun isLoading(): Boolean
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPoosition = layoutManager.findFirstVisibleItemPosition()
        if (!isLoading() && !isLastPage()){
            if (visibleItemCount + firstVisibleItemPoosition > totalItemCount && firstVisibleItemPoosition > 0){
                loadMoreItems()
            }
        }
    }

    abstract fun loadMoreItems()
}