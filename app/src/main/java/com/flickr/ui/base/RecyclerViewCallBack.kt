package com.flickr.ui.base

interface RecyclerViewCallBack {
    fun onBindData(dataBinding: Any, model: Any, position: Int)

    fun onItemClick(model: Any, position: Int)
}