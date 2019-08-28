package com.teayork.module_main.ui.activity

import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.teayork.common_base.base.BaseBWebViewActivity
import com.teayork.common_base.base.BaseMvpActivity
import com.teayork.common_base.base.recycleview.BaseRecyclerViewAdapter
import com.teayork.common_base.base.recycleview.BaseRecyclerViewHolder
import com.teayork.common_base.base.recycleview.XRecyclerView
import com.teayork.common_base.constans.Constant
import com.teayork.common_base.utils.LogUtils
import com.teayork.common_base.utils.SPUtils
import com.teayork.common_base.utils.ToastUtils
import com.teayork.common_base.utils.ViewHelper
import com.teayork.module_main.R
import com.teayork.module_main.adapter.AndroidAdapter
import com.teayork.module_main.bean.ArticleBean
import com.teayork.module_main.bean.ArticleDetailBean
import com.teayork.module_main.mvp.contact.search.SearchResultContact
import com.teayork.module_main.mvp.presenter.search.SearchResultPresenter
import com.teayork.module_main.ui.fragment.me.MyCenterFragment
import kotlinx.android.synthetic.main.main_activity_searchresult.*
import magicasakura.widgets.TintImageView
import magicasakura.widgets.TintTextView

/**
 * author：pengzhixian on 2019-08-27 12:18
 * e-mail：759560522@qq.com
 */
class SearchResultActivity : BaseMvpActivity<SearchResultPresenter>(), SearchResultContact.IView,
    SwipeRefreshLayout.OnRefreshListener {
    override fun onRefresh() {
        xrv_search_result.reset()
        srl_search_result.isRefreshing = true
        mPage = 1
        getPresenter().getSearchData(mPage, mKey)
    }


    private lateinit var mKey: String
    private var mPage: Int = 0
    private lateinit var mAdapter: AndroidAdapter


    override fun initData() {
        mKey = intent.getStringExtra("key")
//        tv_normal_title.text = mKey
        getPresenter().getSearchData(mPage, mKey)


    }

    override fun getLayoutId() = R.layout.main_activity_searchresult

    override fun initView() {
        LogUtils.e("initsearchview")
        ViewHelper.setRefreshLayout(this, true, srl_search_result, this)

        xrv_search_result.apply {
            mAdapter = AndroidAdapter()
            layoutManager = LinearLayoutManager(this@SearchResultActivity)
            setPullRefreshEnabled(false)
            clearHeader()
            adapter = mAdapter
            setLoadingListener(object : XRecyclerView.LoadingListener {
                override fun onLoadMore() {
                    getPresenter().getSearchData(mPage, mKey)
                }

                override fun onRefresh() {
                }

            })
        }
    }

    override fun registerPresenter() = SearchResultPresenter::class.java

    override fun setSearchData(data: ArticleBean) {
        mPage = data.curPage
        if (mPage < 2) {
            xrv_search_result.scrollToPosition(0)
            mAdapter.clear()
        }
        data.datas?.apply {
            mAdapter.addAll(data.datas)
            mAdapter.notifyDataSetChanged()
            if (data.datas.size < 20) {
                xrv_search_result.noMoreLoading()
            }
            xrv_search_result.refreshComplete()
            if (srl_search_result.isRefreshing) {
                srl_search_result.isRefreshing = false
            }
        }

    }

    override fun setFail() {
        ToastUtils.showError(this, resources.getString(R.string.pagelayout_error))


    }

}