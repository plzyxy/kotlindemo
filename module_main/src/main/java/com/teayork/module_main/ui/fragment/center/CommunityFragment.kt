package com.teayork.module_main.ui.fragment.center

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout
import com.teayork.common_base.base.BaseMvpFragment
import com.teayork.common_base.base.recycleview.XRecyclerView
import com.teayork.common_base.utils.LogUtils
import com.teayork.module_main.R
import com.teayork.module_main.adapter.CommunityFragmentAdpter
import com.teayork.module_main.app.Main_App
import com.teayork.module_main.dao.CommunityBeanDao
import com.teayork.module_main.daobean.CommunityBean
import com.teayork.module_main.mvp.contact.center.CommunityContact
import com.teayork.module_main.mvp.model.Moment
import com.teayork.module_main.mvp.presenter.center.CommunityPresenter
import com.teayork.module_main.ui.activity.PostCommunityActivity
import kotlinx.android.synthetic.main.main_fragment_center_community.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * author：pengzhixian on 2019-08-15 11:51
 * e-mail：759560522@qq.com
 */
class CommunityFragment : BaseMvpFragment<CommunityPresenter>(), CommunityContact.IView {

    private var mPage: Int = 0
    private lateinit var mAdapter: CommunityFragmentAdpter


    override fun getLayoutId(): Int {
        return R.layout.main_fragment_center_community

    }

    override fun initView() {
      var  daoSession= Main_App.instance().getDaoSession()
      var dao= daoSession.communityBeanDao
        val moments = initDatas(dao)
        xrc_community.layoutManager = LinearLayoutManager(context)
        xrc_community.setPullRefreshEnabled(false)
        mAdapter = CommunityFragmentAdpter()
        mAdapter.addAll(moments)
        xrc_community.adapter = mAdapter
        xrc_community.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onLoadMore() {

//                getPresenter().getData(mPage)
                loadTestData()
            }

            override fun onRefresh() {
            }

        })
        txt_post_community.setOnClickListener {

            context?.let { it1 ->
                                    startActivity(Intent(it1,PostCommunityActivity::class.java))
//                ToastUtils.showToast(it1,"发布动态")
            }


        }
        txt_show_community.setOnClickListener {


          loadTestData()


        }


    }

    private fun loadTestData() {
        var  daoSession= Main_App.instance().getDaoSession()
        var dao= daoSession.communityBeanDao
        val moments_temp=java.util.ArrayList<Moment>()
        var list: List<CommunityBean> =dao.loadAll()
        for ((index,item )in list.withIndex()){
            LogUtils.e("index-> " ,item.toString())
            if(index>8){
                break
            }
            moments_temp.add(Moment(item.content, item.photos as java.util.ArrayList<String>?))

        }
        xrc_community.refreshComplete()
        if (refresh_community.isRefreshing) {
            refresh_community.isRefreshing = false
        }

        mAdapter.addAll(moments_temp)
        mAdapter.notifyDataSetChanged()
    }

    override fun initData() {}

    fun initDatas(dao: CommunityBeanDao): java.util.ArrayList<Moment> {

        val moments = java.util.ArrayList<Moment>()

        moments.add(
            Moment(
                "1张网络图片",
                ArrayList<String>(Arrays.asList<String>("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered1.png"))
            )
        )

        var list: List<CommunityBean> =dao.loadAll()
        for ((index,item )in list.withIndex()){
            LogUtils.e("index-> " ,item.toString())
            if(index>8){
                break
            }
            moments.add(Moment(item.content, item.photos as java.util.ArrayList<String>?))

        }

        return moments


    }

    override fun registerPresenter() = CommunityPresenter::class.java
    override fun setData(list: java.util.ArrayList<Moment>) {

        mAdapter.addAll(list)
        xrc_community.refreshComplete()
        if (refresh_community.isRefreshing) {
            refresh_community.isRefreshing = false
        }
        mAdapter.setDelegate(object : BGANinePhotoLayout.Delegate{
            override fun onClickNinePhotoItem(
                ninePhotoLayout: BGANinePhotoLayout?,
                view: View?,
                position: Int,
                model: String?,
                models: MutableList<String>?
            ) {


            }

        })
//        mPageLayout.hide()
        mAdapter.notifyDataSetChanged()

    }

    override fun setFail() {
        mAdapter.clear()
    }


    companion object {
        fun newInstance(title: String): CommunityFragment {
            val mFragment = CommunityFragment()
            val bundle = Bundle()
            bundle.putString(TITLE, title)
            mFragment.arguments = bundle
            return mFragment
        }
    }


}