package com.yedona.stickytimeline;

import android.util.Log;

import androidx.recyclerview.widget.GridLayoutManager;

import java.util.List;
import java.util.Map;

/**
 * @author: yedona
 * @date: 2021/2/22 15:12
 * @description
 */
public class StickyTimeLineSpanSizeLookUp extends GridLayoutManager.SpanSizeLookup {


    List<DateBean> list;
    int spanCount = -1;

    private Map<String, List<DateBean>> map;


    public StickyTimeLineSpanSizeLookUp(List<DateBean> list, int spanCount) {
        this.list = list;
        this.spanCount = spanCount;

        map = DateBean.formatList2Map(list);

        for (int i = 0; i < list.size(); i++) {
            getSpanIndex(i, spanCount);
            getSpanSize(i);

        }

    }

    /**
     * 数据源发生变化时，需要重新计算位置
     */
    public void notifyDataSetChanged() {

        map = DateBean.formatList2Map(list);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).spanSize = -1;
            list.get(i).spanIndex = -1;
            getSpanIndex(i, spanCount);
            getSpanSize(i);
        }
    }


    @Override
    public int getSpanIndex(int position, int spanCount) {

        //计算当前行的索引
        DateBean bean = list.get(position);

        if (bean.spanIndex == -1) {
            if (position == 0) {
                bean.spanIndex = 0;
                Log.d("yedona", "getSpanIndex:position: " + position + ",spanIndex:" + bean.spanIndex + ",i:" + 0);
            } else {
                List<DateBean> list = map.get(bean.date);
                int i = list.indexOf(bean);
                bean.spanIndex = i % spanCount;
                Log.d("yedona", "getSpanIndex:position: " + position + ",spanIndex:" + bean.spanIndex + ",index:" + i + ",i % spanCount:" + i % spanCount);
            }

        }


        return bean.spanIndex;


    }

    @Override
    public int getSpanSize(int position) {

        DateBean bean = list.get(position);

        if (bean.spanSize == -1) {

            if (position + 1 < list.size()) {
                //判断当前和下一个的关系
                if (list.get(position + 1).date.equals(bean.date)) {
                    //同一个组的，返回正常
                    bean.spanSize = 1;

                } else {
                    //不是同一个组的
                    List<DateBean> list = map.get(bean.date);

                    assert list != null;
                    //获取当前item在当前组的位置
                    int i = list.indexOf(bean);
                    if (i == 0) {
                        //第一个 ，而且和下一个不是同一组的，占满
                        bean.spanSize = spanCount;

                    } else if (i < list.size()) {

                        int preSpanIndex = getSpanIndex(position - 1, spanCount);
                        if (preSpanIndex == spanCount - 1) {
                            //最后一个，如果上一个的是上一行的最后一个，那当前就是占满
                            bean.spanSize = spanCount;
                        } else {
                            //如果不是，剩下多少补多少
                            bean.spanSize = spanCount - 1 - preSpanIndex;
                        }
                    }

                }

            } else {
                //最后一个了，剩多少补多少
                int preSpanIndex = getSpanIndex(position - 1, spanCount);
                if (preSpanIndex == spanCount - 1) {
                    bean.spanSize = spanCount;
                } else {
                    bean.spanSize = spanCount - 1 - preSpanIndex;
                }
            }

        }
        return bean.spanSize;

    }


}
