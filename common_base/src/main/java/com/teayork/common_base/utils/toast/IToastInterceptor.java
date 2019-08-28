package com.teayork.common_base.utils.toast;

import android.widget.Toast;

public interface IToastInterceptor {

    /**
     * 根据显示的文本决定是否拦截该 Toast
     */
    boolean intercept(Toast toast, CharSequence text);
}