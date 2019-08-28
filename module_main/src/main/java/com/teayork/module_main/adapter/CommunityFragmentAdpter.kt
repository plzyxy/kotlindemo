package com.teayork.module_main.adapter

import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout
import com.teayork.common_base.base.recycleview.BaseRecyclerViewAdapter
import com.teayork.common_base.base.recycleview.BaseRecyclerViewHolder
import com.teayork.module_main.R
import com.teayork.module_main.mvp.model.Moment

/**
 * author：pengzhixian on 2019-08-15 14:21
 * e-mail：759560522@qq.com
 */
class CommunityFragmentAdpter:BaseRecyclerViewAdapter<Moment>() {


    private  var delegate:BGANinePhotoLayout.Delegate?=null

    interface CommunityViewOnClickListener{
      fun itemOnClick()
      fun headOnClick()
      fun imgOnClick()

    }


    fun setDelegate(delegate:BGANinePhotoLayout.Delegate){
        this.delegate=delegate

    }


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): CommunityViewHolder {
        return CommunityViewHolder(parent, R.layout.main_center_community_item_moment)

    }

  inner class CommunityViewHolder(parent :ViewGroup,layouId:Int) :BaseRecyclerViewHolder<Moment>(parent,layouId){

       val tv_item_moment_content by lazy { itemView.findViewById<TextView>(R.id.tv_item_moment_content) }
       val npl_item_moment_photos by lazy { itemView.findViewById<BGANinePhotoLayout>(R.id.npl_item_moment_photos) }


       override fun onBindViewHolder(moment: Moment?, position: Int) {

           if (moment!!.content.isEmpty()) {
               tv_item_moment_content.visibility=View.GONE
           } else {
               tv_item_moment_content.visibility=View.VISIBLE
               tv_item_moment_content.text= moment.content
               tv_item_moment_content.setOnClickListener {



               }
           }

           npl_item_moment_photos.setData(moment!!.photos)
          if(delegate!=null) npl_item_moment_photos.setDelegate(delegate)






       }


   }


}