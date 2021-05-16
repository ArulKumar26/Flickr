package com.flickr.ui.main

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.flickr.BR
import com.flickr.R
import com.flickr.data.model.PhotoData
import com.flickr.data.network.ResourceState
import com.flickr.data.network.ServerErrors
import com.flickr.databinding.ActivityMainBinding
import com.flickr.databinding.LayoutPhotoItemBinding
import com.flickr.extensions.isNullOrEmpty
import com.flickr.extensions.showErrorMessage
import com.flickr.ui.base.*
import com.flickr.utils.binding.setImageUrl


class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), RecyclerViewCallBack,
    View.OnClickListener {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: RecyclerViewAdapter

    override fun onCreate(
        instance: Bundle?,
        binding: ActivityMainBinding,
        viewModel: MainViewModel
    ) {
        mainBinding = binding
        mainViewModel = viewModel
        mainBinding.listener = this

        initViews()
        initObserver()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_search -> {
                when {
                    isNullOrEmpty(
                        mainViewModel.searchText.get()?.trim()
                    ) -> showMessage(
                        getString(R.string.txt_search_error)
                    )
                    else -> {
                        resetPagination()
                        mainViewModel.searchPhotos()
                    }
                }
            }
        }
    }

    override fun getBindVariable(): Int = BR.viewModel

    override fun getViewModel(): Class<MainViewModel> = MainViewModel::class.java

    override fun getLayoutResId(): Int = R.layout.activity_main

    override fun onBindData(dataBinding: Any, model: Any, position: Int) {
        dataBinding as LayoutPhotoItemBinding
        model as PhotoData
        val imageUrl =
            "https://farm${model.farm}.static.flickr.com/${model.server}/${model.id}_${model.secret}.jpg"
        setImageUrl(dataBinding.ivPhoto, imageUrl, dataBinding.progressBar)
        dataBinding.executePendingBindings()
    }

    override fun onItemClick(model: Any, position: Int) {

    }

    private fun initViews() {
        adapter = RecyclerViewAdapter(R.layout.layout_photo_item, this)
        mainBinding.rvPhotos.adapter = adapter
        mainBinding.rvPhotos.layoutManager = GridLayoutManager(this, 3)
        val itemDecoration = GridSpacingItemDecoration(3, 20, true)
        mainBinding.rvPhotos.addItemDecoration(itemDecoration)
        mainBinding.rvPhotos.addOnScrollListener(endlessRecyclerOnScrollListener)
    }

    private fun initObserver() {
        mainViewModel.photoResponse.observe(this, { result ->
            when (result.status) {
                ResourceState.Status.SUCCESS -> {
                    dismissProgressBar()
                    val apiData = result.response?.photos
                    if (endlessRecyclerOnScrollListener.startPage == 1) {
                        if (apiData?.photo?.isEmpty()!!) mainViewModel.noData.set(
                            true
                        ) else mainViewModel.noData.set(
                            false
                        )
                    }
                    apiData?.photo?.let {
                        adapter.addItems(it)
                        adapter.notifyDataSetChanged()
                    }
                }
                ResourceState.Status.ERROR -> {
                    dismissProgressBar()
                    val customException = result?.customException
                    if (customException?.apiError != null) {
                        customException.apiError?.message?.let {
                            showErrorMessage(this, it)
                        }
                    } else {
                        if (isNullOrEmpty(customException?.error)) {
                            customException?.code?.let {
                                ServerErrors.getErrorMsg(this, it)
                            }?.let { showMessage(it) }
                        } else {
                            customException?.error?.let {
                                showErrorMessage(this, it)
                            }
                        }
                    }
                }
                ResourceState.Status.LOADING -> showProgressBar()
            }
        })
    }

    private val endlessRecyclerOnScrollListener by lazy {
        object : EndlessRecyclerOnScrollListener() {
            override fun onLoadMore(currentPage: Int) {
                mainViewModel.page.set(currentPage)
                mainViewModel.searchPhotos()
            }
        }
    }

    private fun resetPagination() {
        endlessRecyclerOnScrollListener.reset()
        adapter.clearItems()
    }
}