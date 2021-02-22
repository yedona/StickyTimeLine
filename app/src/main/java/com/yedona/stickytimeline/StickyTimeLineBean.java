package com.yedona.stickytimeline;

import androidx.annotation.NonNull;

/**
 * @author: yedona
 * @date: 2021/2/22 15:13
 * @description
 */
public abstract class StickyTimeLineBean {


    //占据的宽度
    int spanSize = -1;
    //当前行的索引
    int spanIndex = -1;

    /**
     * @return 用来区别是否切换组的标志
     */
    abstract @NonNull
    String getGroupTag();


}
