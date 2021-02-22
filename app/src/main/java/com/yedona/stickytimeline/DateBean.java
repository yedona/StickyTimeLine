package com.yedona.stickytimeline;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: yedona
 * @date: 2021/2/22 15:53
 * @description
 */
public class DateBean extends StickyTimeLineBean {

    public String date;

    public boolean isSelect;


    @NonNull
    @Override
    String getGroupTag() {
        return date;
    }


    public static Map<String, List<DateBean>> formatList2Map(List<DateBean> list) {

        LinkedHashMap<String, List<DateBean>> map = new LinkedHashMap<>();
        for (int i = 0; i < list.size(); i++) {
            DateBean bean = list.get(i);
            if (map.containsKey(bean.date)) {
                map.get(bean.date).add(bean);
            } else {
                ArrayList<DateBean> photoBeans = new ArrayList<>();
                photoBeans.add(bean);
                map.put(bean.date, photoBeans);
            }
        }
        return map;
    }


    public static ArrayList<DateBean> getList() {


        ArrayList<DateBean> list = new ArrayList<>();

        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < i; j++) {
                DateBean e = new DateBean();
                e.date = "2020-02-" + (i > 9 ? i : "0" + i);
                list.add(e);

            }
        }


        return list;

    }


}
