package com.teayork.module_main.ui.fragment.center

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.widget.TextView
import com.teayork.common_base.base.BaseFragment
import com.teayork.module_main.R
import kotlinx.android.synthetic.main.mian_fragment_center.*

/**
 * author：pengzhixian on 2019-07-23 17:42
 * e-mail：759560522@qq.com
 */
class CenterFragment :BaseFragment() {


    override fun getLayoutId(): Int {
        return R.layout.mian_fragment_center


    }

    override fun initView() {

        var listFragment: MutableList<Pair<String, Fragment>> = mutableListOf(
            "朋友圈" to CommunityFragment(), "图文" to CenterTestFragment.newInstance("福利")
            , "Android" to CenterTestFragment.newInstance("Android"), "已结束" to AndroidFragment.newInstance("热门"), "已拒绝" to AndroidFragment.newInstance("最新")
        )
        mViewPager.offscreenPageLimit=6
        //关键在于先关联viewpager，后修改tab布局（注意在绑定了viewpager后tablayout的tab已经被设置了，所以做修改操作就好了）

//        var supportFragmentManager:FragmentManager
        mViewPager.adapter = TabPagerAdapter(fragmentManager!!, listFragment)

        mTabLayout.setupWithViewPager(mViewPager)
        mViewPager.currentItem = 0

        listFragment.forEachIndexed { i, pair ->
            //设置自定义显示小红点的布局
            var tab = mTabLayout.getTabAt(i)
            tab?.setCustomView(R.layout.main_item_tab_layout)
            var tvTabTitle = tab?.customView?.findViewById<TextView>(R.id.tvTabTitle)
            tvTabTitle?.text = pair.first
        }
    }


    class TabPagerAdapter<T : Fragment>(fm: FragmentManager, list: MutableList<Pair<String, T>>) :
        FragmentPagerAdapter(fm) {
        private var flist = list
        //private var fm = fm
        override fun getItem(p0: Int): Fragment {
            return flist[p0].second
        }

        override fun getCount() = flist.size

        //如果不需要自定义tab的话这里就关联的每个tab的文字，当然这里需要自定义list可以自行简化下
        //override fun getPageTitle(position: Int): CharSequence? {
        //    return flist[position].first
        //}

    }

}