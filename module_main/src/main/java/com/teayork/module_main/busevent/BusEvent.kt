package com.teayork.module_main.busevent

import com.teayork.common_base.event.EventMap
import com.teayork.module_main.daobean.CommunityBean

/**
 * author：pengzhixian on 2019-09-02 14:07
 * e-mail：759560522@qq.com
 */
object BusEvent {


    class PostDynamicEvent(val communityBean:CommunityBean) : EventMap.BaseEvent()



}