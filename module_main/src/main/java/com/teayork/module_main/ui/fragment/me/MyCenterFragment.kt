package com.teayork.module_main.ui.fragment.me

import android.content.Context
import com.teayork.common_base.base.BaseMvpFragment
import com.teayork.module_main.mvp.contact.me.MyCenterContact
import com.teayork.module_main.mvp.presenter.me.MyCenterPresenter
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.teayork.common_base.utils.LogUtils
import com.teayork.common_base.utils.ToastUtils
import com.teayork.module_main.R
import kotlinx.android.synthetic.main.main_fragment_home_hot.*




/**
 * author：pengzhixian on 2019-07-29 14:28
 * e-mail：759560522@qq.com
 */
class MyCenterFragment :BaseMvpFragment<MyCenterPresenter>(), MyCenterContact.IView {
    override fun initData() {
    }


    var num=1
    private val mDatas = ArrayList<String>()

    override fun setData(str: ArrayList<String>) {


        context?.let { ToastUtils.showToast(it,"ssssssssss") }
        mPageLayout.hide()


    }

    override fun setFail() {
        main_fragment_homehot_recycleview.apply {
//            isRefreshing = false
            isEnabled = true
        }
//        xrv_android.refreshComplete()
//        mPageLayout.showError()
//        mPageLayout.setOnRetryListener(object : PageLayout.OnRetryClickListener {
//            override fun onRetry() {
//                loadData(1)
//            }
//        })


    }

    override fun initView() {

//        initPageLayout(main_fragment_home_viewgroup)

        main_fragment_homehot_recycleview.setLayoutManager(LinearLayoutManager(activity))
        // mTextView = (TextView) view.findViewById(R.id.id_info);
        // mTextView.setText(mTitle);

      if  (arguments?.getString(TITLE)!!.isNotEmpty()){

          LogUtils.e("AAAA=>"+arguments?.getString(TITLE))
      }
        main_fragment_homehot_recycleview.setPullRefreshEnabled(false)
        for (i in 0..49) {
            mDatas.add("我是条目" + " -> " + i)
        }
        main_fragment_homehot_recycleview.setAdapter(
            SimpleAdapter(
                activity!!,
                mDatas
            )
        )
        loadData(1)

    }

    class SimpleAdapter(private val context: Context, private val data: List<String>) : RecyclerView.Adapter<SimpleAdapter.SimpleViewHolder>() {
        override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
            holder?.textView?.text = data[position]
        }

        override fun getItemCount(): Int = data.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder
                = SimpleViewHolder(
            LayoutInflater.from(context)
            .inflate(R.layout.main_home_hot_item_recycle, parent, false))

        inner class SimpleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            var textView: TextView = view.findViewById(R.id.home_hot_item_text)
        }
    }

    override fun getLayoutId()= R.layout.main_fragment_home_hot

    override fun registerPresenter()= MyCenterPresenter::class.java



   fun loadData(page:Int){
       getPresenter().getData(page)

    }

  companion object {
      fun newInstance(title: String): MyCenterFragment {
          val tabFragment = MyCenterFragment()
          val bundle = Bundle()
          bundle.putString(TITLE, title)
          tabFragment.arguments = bundle
          return tabFragment
      }
  }





}