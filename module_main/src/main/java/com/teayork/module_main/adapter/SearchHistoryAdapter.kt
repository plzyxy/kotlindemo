package com.teayork.module_main.adapter

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.teayork.common_base.base.recycleview.BaseRecyclerViewAdapter
import com.teayork.common_base.base.recycleview.BaseRecyclerViewHolder
import com.teayork.common_base.event.EventMap
import com.teayork.common_base.utils.RxBusTools
import com.teayork.module_main.R
import com.teayork.module_main.daobean.HotBean
import org.greenrobot.eventbus.EventBus

class SearchHistoryAdapter : BaseRecyclerViewAdapter<HotBean>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder<HotBean> {
        return ViewHolder(parent, R.layout.main_adapter_search_history_item)
    }

    class ViewHolder(parent: ViewGroup, layoutId: Int) : BaseRecyclerViewHolder<HotBean>(parent, layoutId){

        val tvName by lazy { itemView.findViewById<TextView>(R.id.tv_adapter_search_history) }
        val ivDelete by lazy { itemView.findViewById<ImageView>(R.id.iv_search_history_delete) }

        override fun onBindViewHolder(hotBean: HotBean?, position: Int) {

            hotBean?.run {
                tvName.text = name
            }
            ivDelete.setOnClickListener {
//                RxBusTools.getDefault().post(EventMap.SearchHistoryDeleteEvent(position))
                EventBus.getDefault().post(EventMap.SearchHistoryDeleteEvent(position))
            }

        }
    }


}