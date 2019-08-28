package com.teayork.module_main.ui.fragment.home

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.stx.xhb.xbanner.XBanner
import com.teayork.common_base.base.BaseBWebViewActivity
import com.teayork.common_base.base.BaseMvpFragment
import com.teayork.common_base.base.recycleview.XRecyclerView
import com.teayork.common_base.utils.LogUtils
import com.teayork.common_base.utils.ViewHelper
import com.teayork.common_base.utils.toast.ToastUtils
import com.teayork.common_base.widget.view.PageLayout
import com.teayork.common_base.widget.view.SWImageView
import com.teayork.module_main.R
import com.teayork.module_main.adapter.AndroidAdapter
import com.teayork.module_main.bean.ArticleBean
import com.teayork.module_main.bean.BannerBean
import com.teayork.module_main.mvp.contact.home.HomeContact
import com.teayork.module_main.mvp.presenter.home.HomePresenter
import com.teayork.module_main.ui.activity.SearchActivity
import com.teayork.module_main.ui.activity.SearchAllActivity
import kotlinx.android.synthetic.main.main_fragment_home.*


/**
 * author：pengzhixian on 2019-07-23 10:53
 * e-mail：759560522@qq.com
 */
 class HomeFragment : BaseMvpFragment<HomePresenter>(), HomeContact.IView, SwipeRefreshLayout.OnRefreshListener {
    override fun setData(data: ArticleBean) {
        setAdapter(data)
        mPageLayout.hide()


    }

    override fun initData() {
        LogUtils.e("initData", "initdata??")

    }

    private var bannerData = mutableListOf<BannerBean>()
    private lateinit var mAdapter: AndroidAdapter
    private var banner: XBanner? = null
    private var ll_activity_wrapper: LinearLayout? = null


    //    private var hotBean: HotBean? = null
    private var mPage: Int = 0
    private var mIndex: Int = 0


    override fun setBanner(mutableList: MutableList<BannerBean>) {

//        获取到banner
        LogUtils.e("eee", "setBanner")

        bannerData.addAll(mutableList)
//        if (arguments?.get("index") == 0) {
        initHeader()
        xrv_main_home.scrollToPosition(0)
        mAdapter.notifyDataSetChanged()


//        }
        getPresenter().getData(mPage, "最新")

        xrv_main_home.refreshComplete()
        if (srl_mian_home.isRefreshing) {
            srl_mian_home.isRefreshing = false
        }
        mPageLayout.hide()


    }


    override fun onRefresh() {
        xrv_main_home.reset()
        srl_mian_home.isRefreshing = true
        mPage = 0
        getPresenter().getData(mPage, "动画")


    }

    fun initDatas() {
        LogUtils.e("eee", "initdatas")
        getPresenter().getBannerHttp()
//         hotBean = arguments?.getSerializable("bean") as HotBean
        mIndex = 1


//        getPresenter().getBannerHttp()
    }

    override fun registerPresenter() = HomePresenter::class.java


    override fun setFail() {
        mPageLayout.showError()
        mPageLayout.setOnRetryListener(object : PageLayout.OnRetryClickListener {
            override fun onRetry() {
                getPresenter().getBannerHttp()
            }

        })
        srl_mian_home.apply {
            isRefreshing = false
            isEnabled = true
        }


    }


    override fun getLayoutId(): Int {

        return R.layout.main_fragment_home
    }

    override fun initView() {
        text_title.setOnClickListener { view ->
            ToastUtils.show("去搜索")

//            initHeader()

//
//            ARouter.getInstance()
//                .build("/module_transation/TransationActivity")
//                .navigation()
        }

        initPageLayout(xrv_main_home)
        initViews()
        ViewHelper.setRefreshLayout(context, true, srl_mian_home, this)
        initDatas()


    }

    fun initHeader() {
         val mTitles = arrayOf("福利", "App", "拓展资源","福利", "App", "拓展资源")

        try {
            val layoutBanner = layoutInflater.inflate(R.layout.main_home_header_banner, null).also {
                banner = it.findViewById<XBanner>(R.id.home_banner)

                ll_activity_wrapper=it.findViewById<LinearLayout>(R.id.ll_activity_wrapper)
            }
            val urlList = mutableListOf<String>()
            val contentList = mutableListOf<String>()
            for (bannerBean in bannerData) {
                urlList.add(bannerBean.imagePath)
                contentList.add(bannerBean.title)
            }

            ll_activity_wrapper!!.removeAllViews()
            for(str in mTitles){
                val viewAct=layoutInflater.inflate(R.layout.main_home_active_item,null).also {

                    Glide.with(context!!)
                        .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1565694046167&di=7cbf092482eb2f4970d79bac394e34d1&imgtype=0&src=http%3A%2F%2Fimg3.duitang.com%2Fuploads%2Fitem%2F201605%2F26%2F20160526190730_tfziZ.jpeg")
//                        .apply(RequestOptions.bitmapTransform( BlurTransformation(25,1)))
                        .into(it.findViewById<SWImageView>(R.id.simg_home_active_item))

                    it.findViewById<TextView>(R.id.txt_home_active_item).text=str
                }
                ll_activity_wrapper!!.addView(viewAct)

            }

            banner?.run {
                setData(R.layout.layout_banner_imageview, urlList, contentList)
                viewPager.pageMargin = 20
                loadImage { _, model, view, position ->
                    val iv = view.findViewById<SWImageView>(R.id.iv_banner_item)
                    Glide.with(context).load(model as String?).into(iv)
                }
                setOnItemClickListener { _, _, position ->
                    context?.let {
                        BaseBWebViewActivity.loadUrl(
                            it,
                            bannerData[position].url,
                            bannerData[position].title
                        )
                    }
                }
            }

            xrv_main_home.addHeaderView(layoutBanner)

        } catch (e: Exception) {
            LogUtils.e("error", e.message)

        }


//        home_banner

    }

    private fun setAdapter(data: ArticleBean) {
        mPage = data.curPage
        if (mPage < 2) {
            xrv_main_home.scrollToPosition(0)
            mAdapter.clear()
        }
        data.datas?.run {
            mAdapter.addAll(data.datas)
            mAdapter.notifyDataSetChanged()
            if (data.datas.size < 20) {
                xrv_main_home.noMoreLoading()
            }
            xrv_main_home.refreshComplete()
            if (srl_mian_home.isRefreshing) {
                srl_mian_home.isRefreshing = false
            }
        }
        mPageLayout.hide()
    }

    fun initViews() {
        LogUtils.e("eee", "initViews")
        search_home.setOnClickListener {
            context!!.startActivity(Intent(context, SearchActivity::class.java))



        }
//        xrv_main_home.run {
        mAdapter = AndroidAdapter()
        xrv_main_home.layoutManager = LinearLayoutManager(context)
        xrv_main_home.setPullRefreshEnabled(false)
        xrv_main_home.clearHeader()
        xrv_main_home.adapter = mAdapter
        xrv_main_home.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onLoadMore() {
                getPresenter().getData(mPage, "动画")
            }

            override fun onRefresh() {
            }

        })


//        }




    }
    companion object {
        fun newInstance(title: String): HomeFragment {
            val tabFragment = HomeFragment()
            val bundle = Bundle()
            bundle.putString(TITLE, title)
            tabFragment.arguments = bundle
            return tabFragment
        }
    }


}