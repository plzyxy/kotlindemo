package com.teayork.module_main.ui.fragment.me

import com.teayork.common_base.base.BaseFragment
import com.teayork.module_main.R
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager.OnPageChangeListener
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.teayork.module_main.widget.SimpleViewPagerIndicator
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.main_fragment_me.*

/**
 * author：pengzhixian on 2019-07-23 11:01
 * e-mail：759560522@qq.com
 */
class MeFragment : BaseFragment() {

    private val mTitles = arrayOf("简介me", "评价me", "相关me")
    private var mAdapter: FragmentPagerAdapter? = null
    private val mFragments = arrayOfNulls<MyCenterFragment>(mTitles.size)

    override fun getLayoutId(): Int {
        return R.layout.main_fragment_me
    }

    override fun initView() {


        initViews()
        initDatas()
        initEvents()


    }

    private fun initEvents() {
        id_stickynavlayout_viewpager.setOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageSelected(position: Int) {}

            override fun onPageScrolled(
                position: Int, positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                id_stickynavlayout_indicator.scroll(position, positionOffset)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        id_stickynavlayout_indicator.setOntabClickListener(SimpleViewPagerIndicator.OntabClickListener {
//            it.getTag()
            id_stickynavlayout_viewpager.setCurrentItem(it.getTag() as Int)



        })

    }

    private fun initDatas() {
        id_stickynavlayout_indicator!!.setTitles(mTitles)

        for (i in 0 until mTitles.size) {
            mFragments[i] = MyCenterFragment.newInstance(mTitles[i])
        }

        mAdapter = object : FragmentPagerAdapter(activity!!.supportFragmentManager) {
            override fun getCount(): Int {
                return mTitles.size
            }

            override fun getItem(position: Int): Fragment {
                return mFragments[position]!!
            }

        }

        id_stickynavlayout_viewpager!!.setAdapter(mAdapter)
        id_stickynavlayout_viewpager!!.setCurrentItem(0)


    }

    private fun initViews() {
        Glide.with(context!!)
            .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1565694046167&di=7cbf092482eb2f4970d79bac394e34d1&imgtype=0&src=http%3A%2F%2Fimg3.duitang.com%2Fuploads%2Fitem%2F201605%2F26%2F20160526190730_tfziZ.jpeg")
//            .apply(RequestOptions.bitmapTransform( BlurTransformation(5,2)))

            .into(myphoto)
        Glide.with(context!!)
            .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1565694046167&di=7cbf092482eb2f4970d79bac394e34d1&imgtype=0&src=http%3A%2F%2Fimg3.duitang.com%2Fuploads%2Fitem%2F201605%2F26%2F20160526190730_tfziZ.jpeg")
            .apply(RequestOptions.bitmapTransform( BlurTransformation(25,1)))

            .into(myphoto_bg)




        /*
		RelativeLayout ll = (RelativeLayout) findViewById(R.id.id_stickynavlayout_topview);
		TextView tv = new TextView(this);
		tv.setText("我的动态添加的");
		tv.setBackgroundColor(0x77ff0000);
		ll.addView(tv, new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT, 600));
		*/
    }
}