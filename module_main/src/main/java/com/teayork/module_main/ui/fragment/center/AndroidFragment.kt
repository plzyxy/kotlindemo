package com.teayork.module_main.ui.fragment.center

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import com.teayork.common_base.base.BaseMvpFragment
import com.teayork.common_base.base.recycleview.XRecyclerView
import com.teayork.common_base.event.EventMap
import com.teayork.common_base.utils.ViewHelper
import com.teayork.common_base.widget.view.PageLayout
import com.teayork.module_main.R
import com.teayork.module_main.adapter.AndroidAdapter
import com.teayork.module_main.bean.ArticleBean
import com.teayork.module_main.mvp.contact.center.AndroidContact
import com.teayork.module_main.mvp.presenter.center.AndroidPresenter
import kotlinx.android.synthetic.main.main_fragment_android.*

class AndroidFragment : BaseMvpFragment<AndroidPresenter>(), AndroidContact.IView,
    SwipeRefreshLayout.OnRefreshListener {




    private var mPage: Int = 0
    private lateinit var mAdapter: AndroidAdapter

//    override fun isHasBus(): Boolean {
//        return true
//    }

    override fun initView() {

       if( arguments?.getString(TITLE)!!.isNotEmpty()){

       }
        initPageLayout(xrv_android)
        ViewHelper.setRefreshLayout(context, true, refresh_android, this)
        initXrv()
    }


    override fun getLayoutId(): Int {
        return R.layout.main_fragment_android
    }

    override fun initData() {
        loadData(mPage)
    }

    private fun initXrv() {
        xrv_android.run {
            mAdapter = AndroidAdapter()
            layoutManager = LinearLayoutManager(context)
            setPullRefreshEnabled(false)
            clearHeader()
            adapter = mAdapter
            setLoadingListener(object : XRecyclerView.LoadingListener {
                override fun onLoadMore() {
                    loadData(mPage)
                }

                override fun onRefresh() {
                }

            })
        }

    }

    override fun setFail() {
        refresh_android.apply {
            isRefreshing = false
            isEnabled = true
        }
        xrv_android.refreshComplete()
        mPageLayout.showError()
        mPageLayout.setOnRetryListener(object : PageLayout.OnRetryClickListener {
            override fun onRetry() {
                loadData(mPage)
            }
        })
    }

    fun loadData(page: Int) {
        getPresenter().getArticle(page)
    }

    override fun onRefresh() {
        xrv_android.apply {
            reset()
            refresh_android.isRefreshing = true
            mPage = 0
            loadData(mPage)
        }
    }

    override fun registerPresenter() = AndroidPresenter::class.java

    override fun setArticle(articleBean: ArticleBean) {
        mPage = articleBean.curPage
        if (mPage < 2) {
            xrv_android.scrollToPosition(0)
            mAdapter.clear()
        }
        mAdapter.addAll(articleBean.datas)
        mAdapter.notifyDataSetChanged()
        if (articleBean.size < 20) {
            xrv_android.noMoreLoading()
        }
        xrv_android.refreshComplete()
        if (refresh_android.isRefreshing) {
            refresh_android.isRefreshing = false
        }
        mPageLayout.hide()
    }

    override fun cancelCollectResult(id: Int) {
//        UserControl.getCurrentUser()!!.collectIds.remove(id.toString())
//        activity?.let { ToastUtils.showSuccess(it, "取消收藏") }
    }

    override fun collectResult(id: Int) {
//        UserControl.getCurrentUser()!!.collectIds.add(id.toString())
//        activity?.let { ToastUtils.showSuccess(it, "收藏成功") }
    }

    override fun onEvent(it: EventMap.BaseEvent) {
        when (it) {
            is EventMap.ToUpEvent -> if (isVisible) {
                xrv_android.smoothScrollToPosition(0)
            }
            is EventMap.HomeRefreshEvent -> onRefresh()
            is EventMap.ChangeThemeEvent -> ViewHelper.changeRefreshColor(refresh_android, context)
            is EventMap.CollectEvent -> if (it.flag == EventMap.CollectEvent.COLLECT) {
                getPresenter().collectHttp(it.id)
            } else {
                getPresenter().cancelCollectHttp(it.id)
            }
            is EventMap.WifiImgEvent -> mAdapter.notifyDataSetChanged()
        }
    }

    companion object {
        fun newInstance(title: String): AndroidFragment {
            val mFragment = AndroidFragment()
            val bundle = Bundle()
            bundle.putString(TITLE, title)
            mFragment.arguments = bundle
            return mFragment
        }
    }


}