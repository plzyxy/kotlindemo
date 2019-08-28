package com.teayork.module_main.ui.activity

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.teayork.common_base.base.BaseMvpActivity
import com.teayork.common_base.utils.LogUtils
import com.teayork.module_main.R
import com.teayork.module_main.bean.ArticleBean
import com.teayork.module_main.mvp.contact.search.SearchResultContact
import com.teayork.module_main.mvp.presenter.search.SearchResultPresenter
import kotlinx.android.synthetic.main.main_activity_searchresult.*

/**
 * author：pengzhixian on 2019-08-27 16:27
 * e-mail：759560522@qq.com
 */
class TestActivity : BaseMvpActivity<SearchResultPresenter>(), SearchResultContact.IView {
    override fun registerPresenter() = SearchResultPresenter::class.java

    override fun setSearchData(data: ArticleBean) {
    }

    override fun setFail() {
    }

    override fun initData() {
    }


    private val mDatas = ArrayList<String>()
    override fun initView() {
        LogUtils.e("aaaaaaa")
        xrv_search_result.layoutManager = LinearLayoutManager(this)
        xrv_search_result.setPullRefreshEnabled(false)
        for (i in 0..49) {
            mDatas.add("我是条目" + " -> " + i)
        }
        xrv_search_result.setAdapter(
            SimpleAdapter(
                this,
                mDatas
            )
        )


    }

    override fun getLayoutId() = R.layout.main_activity_searchresult


    class SimpleAdapter(private val context: Context, private val data: List<String>) :
        RecyclerView.Adapter<SimpleAdapter.SimpleViewHolder>() {
        override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
            holder?.textView?.text = data[position]
        }

        override fun getItemCount(): Int = data.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder = SimpleViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.main_home_hot_item_recycle, parent, false)
        )

        inner class SimpleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            var textView: TextView = view.findViewById(R.id.home_hot_item_text)
        }
    }
}