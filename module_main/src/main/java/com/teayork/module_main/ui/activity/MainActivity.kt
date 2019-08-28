package com.teayork.module_main.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import android.widget.RadioGroup
import com.teayork.common_base.base.BaseActivity
import com.teayork.common_base.base.BaseApplication
import com.teayork.common_base.utils.toast.ToastUtils
import com.teayork.module_main.R
import com.teayork.module_main.ui.fragment.center.CenterFragment
import com.teayork.module_main.ui.fragment.home.HomeFragment
import com.teayork.module_main.ui.fragment.me.MeFragment
import com.teayork.module_main.ui.fragment.shopcar.ShopCarFragment
import kotlinx.android.synthetic.main.main_activity_main.*
import java.util.*


class MainActivity : BaseActivity() {

    private val mFragmentList = ArrayList<Fragment>()
    private var mExitTime: Long = 0


    override fun getLayoutId(): Int {
        return R.layout.main_activity_main
    }

    override fun initView() {


        initTab()


        changeFragment(HomeFragment::class.java.simpleName)
        rg_main.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            Log.e("aaa", "切换")
            when (checkedId) {
                R.id.rb_home -> {

                    tv_title.setText("首页")
                    changeFragment(HomeFragment::class.java!!.getName())
//                    ToastUtils.show("首页")
//                    Toast.makeText(this, "home", Toast.LENGTH_LONG).show()
                }
                R.id.rb_center -> {
                    tv_title.setText("资讯")
                    changeFragment(CenterFragment::class.java!!.getName())
//                    ToastUtils.show("center")
                }
                R.id.rb_shopcar -> {
                    tv_title.setText("购物车")
//                    ToastUtils.show("购物车")
                    changeFragment(ShopCarFragment::class.java!!.getName())
                }
                R.id.rb_user -> {
//                    Toast.makeText(this, "center", Toast.LENGTH_LONG).show()
                    changeFragment(MeFragment::class.java!!.getName())
//                    ToastUtils.show("个人中心")
                }


            }


        })

    }


    fun initTab() {

        //定义底部标签图片大小和位置
        val drawable_news = resources.getDrawable(R.drawable.main_tab_home)
        //当这个图片被绘制时，给他绑定一个矩形 ltrb规定这个矩形
        drawable_news.setBounds(0, 0, 50, 50)
        //设置图片在文字的哪个方向
        rb_home.setCompoundDrawables(null, drawable_news, null, null)


        //定义底部标签图片大小和位置
        val drawable_center = resources.getDrawable(R.drawable.main_tab_center)
        //当这个图片被绘制时，给他绑定一个矩形 ltrb规定这个矩形
        drawable_center.setBounds(0, 0, 50, 50)
        //设置图片在文字的哪个方向
        rb_center.setCompoundDrawables(null, drawable_center, null, null)


        //定义底部标签图片大小和位置
        val drawable_shopcar = resources.getDrawable(R.drawable.main_tab_shopcar)
        //当这个图片被绘制时，给他绑定一个矩形 ltrb规定这个矩形
        drawable_shopcar.setBounds(0, 0, 50, 50)
        //设置图片在文字的哪个方向
        rb_shopcar.setCompoundDrawables(null, drawable_shopcar, null, null)


        //定义底部标签图片大小和位置
        val drawable_me = resources.getDrawable(R.drawable.main_tab_me)
        //当这个图片被绘制时，给他绑定一个矩形 ltrb规定这个矩形
        drawable_me.setBounds(0, 0, 50, 50)
        //设置图片在文字的哪个方向
        rb_user.setCompoundDrawables(null, drawable_me, null, null)


    }


    /**
     * Fragment 发生改变
     *
     * @param tag Fragment 类名
     */
    fun changeFragment(tag: String) {
        hideFragment()
        val transaction = supportFragmentManager.beginTransaction()
        var fragment = supportFragmentManager.findFragmentByTag(tag)
        if (fragment != null) {
            transaction.show(fragment)
        } else {

            if (TextUtils.equals(tag, HomeFragment::class.java!!.getName())) {
                fragment = HomeFragment.newInstance("热门")

            } else if (TextUtils.equals(tag, CenterFragment::class.java!!.getName())) {
                fragment = CenterFragment()
            } else if (TextUtils.equals(tag, MeFragment::class.java!!.getName())) {
                fragment = MeFragment()
            } else if (TextUtils.equals(tag, ShopCarFragment::class.java!!.getName())) {
                fragment = ShopCarFragment()
            } else {
                fragment = HomeFragment.newInstance("热门")

            }
            mFragmentList.add(fragment!!)
            transaction.add(R.id.fl_context, fragment!!, fragment.javaClass.name)
        }
        transaction.commitAllowingStateLoss()
    }

    /**
     * 隐藏所有Fragment
     */
    private fun hideFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        for (f in mFragmentList) {
            transaction.hide(f)
        }
        transaction.commit()

    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - mExitTime > 2000) {
                ToastUtils.show("再次点击 退出app");
                mExitTime = System.currentTimeMillis()
            } else {
                BaseApplication.instance.exitApp()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}
