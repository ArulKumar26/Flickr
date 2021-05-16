package com.flickr.ui.base

import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.LayoutRes
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.flickr.di.module.ViewModelFactory
import com.flickr.extensions.showToastMessage
import com.flickr.utils.NetworkUtils
import com.flickr.widget.CProgressDialog
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


abstract class BaseActivity<D : ViewDataBinding, M : ViewModel> : DaggerAppCompatActivity(), IBaseView {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private var cProgressDialog: CProgressDialog? = null

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<D>(this, getLayoutResId())
        val viewModel = ViewModelProvider(this, viewModelFactory).get(getViewModel())
        binding.setVariable(getBindVariable(), viewModel)
        onCreate(savedInstanceState, binding, viewModel)
        binding.executePendingBindings()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return true
    }

    override fun showMessage(message: String) = showToastMessage(message)

    override fun isNetworkConnected(): Boolean = NetworkUtils.isNetWorkAvailable(this)

    override fun showProgressBar() {
        if (!isFinishing) progressDialog().show()
    }

    override fun dismissProgressBar() {
        cProgressDialog?.isShowing()?.let {
            if (!isFinishing && cProgressDialog != null && it) cProgressDialog?.dismiss()
        }
    }

    private fun progressDialog(): CProgressDialog {
        if (cProgressDialog == null) {
            cProgressDialog = CProgressDialog(this)
        }
        return cProgressDialog!!
    }

    protected abstract fun onCreate(instance: Bundle?, binding: D, viewModel: M)

    protected abstract fun getBindVariable(): Int

    protected abstract fun getViewModel(): Class<M>

    @LayoutRes
    protected abstract fun getLayoutResId(): Int
}