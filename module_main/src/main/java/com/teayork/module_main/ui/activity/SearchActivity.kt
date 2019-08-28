package com.teayork.module_main.ui.activity

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import com.teayork.common_base.base.BaseMvpActivity
import com.teayork.common_base.event.EventMap
import com.teayork.common_base.utils.LogUtils
import com.teayork.module_main.R
import com.teayork.module_main.adapter.SearchHistoryAdapter
import com.teayork.module_main.app.Main_App
import com.teayork.module_main.daobean.HotBean
import com.teayork.module_main.mvp.contact.search.SearchContract
import com.teayork.module_main.mvp.presenter.search.SearchPresenter
import kotlinx.android.synthetic.main.main_activity_main.*
import kotlinx.android.synthetic.main.main_activity_search.*
import magicasakura.widgets.TintTextView
import java.util.*

class SearchActivity : BaseMvpActivity<SearchPresenter>(), SearchContract.IView {

    private lateinit var mHistoryAdapter: SearchHistoryAdapter

    override fun getLayoutId(): Int {
        return R.layout.main_activity_search
    }


    override fun regEvent()=true

    override fun initView() {
        LogUtils.e("aaaaaaaaaa",222)
//        setStatusBarColor()
//        var  daoSession= Main_App.instance().getDaoSession()
//        var dao= daoSession.hotBeanDao
//      var  hot: HotBean=HotBean(1 as Long,null,"name","link",6,1)
//            dao.insert(hot)
        iv_search_back.setOnClickListener { finish() }

        et_search.setOnEditorActionListener { v, actionId, event ->

            if (actionId == EditorInfo.IME_ACTION_SEARCH){
                val hotBean = HotBean()
                hotBean.name = v.text.toString()
//                hotBean.id = Math.random().toLong()
                getPresenter().insertDao(hotBean)
                goSearch(v.text.toString())
            }

            false
        }
    }

    override fun initData() {
        getPresenter().getHotHttp()
        getPresenter().queryDao()
    }

    override fun registerPresenter() = SearchPresenter::class.java


    override fun getHotResult(data: MutableList<HotBean>) {
        for (hot in data){
            val item = LayoutInflater.from(this).inflate(R.layout.main_search_layout_hot_item,null) as TintTextView
            item.text = hot.name
            item.setOnClickListener {
                getPresenter().insertDao(hot)
                goSearch(hot.name)
            }
            flex.addView(item)
        }
    }

    fun goSearch(key: String){
        val intent = Intent(this@SearchActivity,SearchResultActivity::class.java)
        intent.putExtra("key",key)
        startActivity(intent)
        getPresenter().queryDao()
    }

    override fun onEventBus(event: EventMap.BaseEvent) {
        val hotBean = mHistoryAdapter.data[(event as EventMap.SearchHistoryDeleteEvent).position]
        getPresenter().delete(hotBean.id)




    }


    override fun insertDao(id: Long) {
        LogUtils.e(">>>>DB"+"插入数据库成功")
        getPresenter().queryDao()
    }

    override fun queryResult(hotBean: MutableList<HotBean>) {
        rv_search_history.layoutManager = LinearLayoutManager(this)
        mHistoryAdapter = SearchHistoryAdapter()
        mHistoryAdapter.addAll(hotBean)
        rv_search_history.adapter = mHistoryAdapter
    }


    override fun deleteResult() {
        LogUtils.e(">>>>DB"+"删除数据库成功")
        getPresenter().queryDao()
    }


}
