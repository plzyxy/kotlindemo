package com.teayork.module_main.ui.fragment.center

import android.content.Context
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout
import com.bumptech.glide.Glide
import com.teayork.common_base.base.BaseBWebViewActivity
import com.teayork.common_base.base.BaseMvpFragment
import com.teayork.common_base.base.recycleview.XRecyclerView
import com.teayork.common_base.widget.view.PageLayout
import com.teayork.common_base.widget.view.SWImageView
import com.teayork.module_main.R
import com.teayork.module_main.bean.GankBean
import com.teayork.module_main.bean.ResultBean
import com.teayork.module_main.mvp.contact.center.CenterTestContact
import com.teayork.module_main.mvp.presenter.center.CenterTestPresenter
import kotlinx.android.synthetic.main.main_fragment_center_test.*


/**
 * author：pengzhixian on 2019-08-09 10:07
 * e-mail：759560522@qq.com
 */
class CenterTestFragment : BaseMvpFragment<CenterTestPresenter>(), CenterTestContact.IView,
    SwipeRefreshLayout.OnRefreshListener {

    private var cate: String = "Android"
    private var mPage: Int = 1
    private val mDatas = ArrayList<ResultBean>()
    private var mAdapter: SimpleAdapter? = null


    override fun onRefresh() {
        xrv_centertest.apply {
            reset()
            refresh_centertest.isRefreshing = true
            loadData(1)
        }


    }


    override fun initData() {
        loadData(1)
    }

    override fun getLayoutId() = R.layout.main_fragment_center_test

    override fun registerPresenter() = CenterTestPresenter::class.java

    override fun setData(list: GankBean) {
        xrv_centertest.refreshComplete()
        if (refresh_centertest.isRefreshing) {
            refresh_centertest.isRefreshing = false
        }
        mAdapter!!.addAll(list.results)



        mPageLayout.hide()
    }


    override fun setFail() {
        refresh_centertest.apply {
            isRefreshing = false
            isEnabled = true
        }
        xrv_centertest.refreshComplete()
        mPageLayout.showError()
        mPageLayout.setOnRetryListener(object : PageLayout.OnRetryClickListener {
            override fun onRetry() {
                loadData(1)
            }
        })


    }

    override fun initView() {

        initPageLayout(xrv_centertest)


        xrv_centertest.setLayoutManager(LinearLayoutManager(activity))


        xrv_centertest.run {

            setPullRefreshEnabled(false)
            setLoadingMoreEnabled(true)
            setLoadingListener(object : XRecyclerView.LoadingListener {
                override fun onLoadMore() {
                    mPage++
                    loadData(mPage)
                }

                override fun onRefresh() {
                }

            })


        }
        refresh_centertest.setOnRefreshListener(this)
        mAdapter = SimpleAdapter(activity!!, mDatas)
        if (arguments?.getString(TITLE)!!.isNotEmpty()) {
            cate = arguments?.getString(TITLE)!!
            if (cate.equals("Android")) {
                mAdapter!!.setType(2)

            }

//            LogUtils.e("AAAA=>" + arguments?.getString(TITLE))
        }
        // mTextView = (TextView) view.findViewById(R.id.id_info);
        // mTextView.setText(mTitle);
//        for (i in 0..49) {
////            mDatas.add("我是条目" + " -> " + i)
//        }


        xrv_centertest.setAdapter(mAdapter)

//        val layoutBanner = layoutInflater.inflate(R.layout.main_home_header_banner, null)
//      var text=  TextView(context)
//        text.text="aaaaaaaaaaaa"
//        xrv_centertest.addHeaderView(layoutBanner)


    }

    fun loadData(page: Int) {

        getPresenter().getData(cate, page)

    }


    class SimpleAdapter(private val context: Context, private val data: ArrayList<ResultBean>) :
        RecyclerView.Adapter<SimpleAdapter.SimpleViewHolder>() {

        private var type: Int = 1

        override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
            holder?.textView?.text = data[position].desc
            holder?.textView_date?.text = data[position].publishedAt
            holder?.textVie_authorw?.text = data[position].who

            holder.viewGroup.setOnClickListener {
                if (type == 1) {

                } else {
                    BaseBWebViewActivity.loadUrl(context, data[position].url, data[position].desc)

                }


            }
            if (type == 1) {
                holder?.imageview.visibility = View.VISIBLE
                Glide.with(context).load(data[position].url).into(holder?.imageview)
            } else {
                holder?.imageview.visibility = View.GONE
                if (data[position].images!=null&&data[position].images!!.size!! > 0) {
                    holder?.npl_item_moment_photos.setData(data[position].images as java.util.ArrayList<String>?)
                }
//                LogUtils.e("img","=>"+data[position].images?.get(0))
//                Glide.with(context).load(data[position].images?.get(0)).into(holder?.imageview)
            }
//            holder?.imageview?.text = data[position]
        }

        open fun setType(type: Int) {
            this.type = type

        }


        override fun getItemCount(): Int = data.size

        override fun getItemViewType(position: Int): Int {
            return super.getItemViewType(position)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder = SimpleViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.main_home_hot_item_recycle, parent, false)
        )

        fun addAll(results: MutableList<ResultBean>) {
            data.addAll(results)
            notifyDataSetChanged()

        }

        inner class SimpleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            var viewGroup = view
            var textView: TextView = view.findViewById(R.id.home_hot_item_text)
            var imageview: SWImageView = view.findViewById(R.id.home_hot_item_image)
            var textView_date: TextView = view.findViewById(R.id.home_hot_item_text_date)
            var textVie_authorw: TextView = view.findViewById(R.id.home_hot_item_text_author)
            var npl_item_moment_photos: BGANinePhotoLayout = view.findViewById(R.id.test_npl_item_moment_photos)
        }
    }


    companion object {
        fun newInstance(title: String): CenterTestFragment {
            val mFragment = CenterTestFragment()
            val bundle = Bundle()
            bundle.putString(TITLE, title)
            mFragment.arguments = bundle
            return mFragment
        }
    }


}