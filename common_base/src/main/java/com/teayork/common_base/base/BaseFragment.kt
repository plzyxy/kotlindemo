package com.teayork.common_base.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import android.widget.TextView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.teayork.common_base.R
import com.teayork.common_base.event.EventMap
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * author：pengzhixian on 2019-07-23 10:04
 * e-mail：759560522@qq.com
 */
abstract class BaseFragment : Fragment() {

    private var emptyView: ViewStub? = null
    private var rootView: View? = null
    private var unBinder: Unbinder? = null
    protected var mContext: Context? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.common_fragment_base, container, false)
        (rootView!!.findViewById<View>(R.id.fl_content) as ViewGroup).addView(
            layoutInflater.inflate(
                getLayoutId(),
                null
            )
        )
        unBinder = ButterKnife.bind(this, rootView!!)
        if (regEvent()) {
            EventBus.getDefault().register(this)
        }
        return rootView
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroy() {
        super.onDestroy()
        if (unBinder != null) {
            unBinder!!.unbind()
        }
        if (regEvent()) {
            EventBus.getDefault().unregister(this)
        }
    }


    //***************************************空页面方法*************************************
    protected fun showEmptyView() {
        showEmptyOrErrorView("暂无数据", R.drawable.bg_no_data)
    }


    protected fun showErrorView() {
        showEmptyOrErrorView("网络请求失败", R.drawable.bg_no_net)
    }

    fun showEmptyOrErrorView(text: String, img: Int) {
        emptyView = rootView!!.findViewById(R.id.vs_empty)

        if (emptyView != null) {
            emptyView!!.setVisibility(View.VISIBLE)
            rootView!!.findViewById<View>(R.id.iv_empty).setBackgroundResource(img)
            (rootView!!.findViewById<View>(R.id.tv_empty) as TextView).text = text
            rootView!!.findViewById<View>(R.id.ll_empty).setOnClickListener { onEmptyViewClick() }
        }
    }

    protected fun hideEmptyView() {
        if (emptyView != null) {
            emptyView!!.setVisibility(View.GONE)
        }
    }

    /**
     * 空页面被点击
     */
    protected fun onEmptyViewClick() {

    }

    //***************************************空页面方法*********************************


    /**
     * 给Fragment设置数据
     */
    fun setFragmentData(data: Any) {

    }

    /**
     * 子类接受事件 重写该方法
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onEventBus(event: EventMap.BaseEvent) {
    }

    /**
     * 需要接收事件 重新该方法 并返回true
     */
    open fun regEvent(): Boolean {
        return false
    }


    protected abstract fun getLayoutId(): Int

    protected abstract fun initView()
}