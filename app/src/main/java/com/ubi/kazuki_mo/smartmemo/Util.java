package com.ubi.kazuki_mo.smartmemo;

import android.content.Context;
import android.widget.SimpleAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kazuki-mo on 16/08/05.
 */
public class Util {

    // ListView用のAdapterを作成して返す
    public SimpleAdapter getMemoAdapter(List<String> memoList, List<String> dateList, final Context context){
        final List<Map<String, String>> list = new ArrayList<>();
        for (int i=0; i<memoList.size(); i++) {
            Map<String, String> map = new HashMap<>();
            map.put("main", memoList.get(i));
            map.put("sub", dateList.get(i));
            list.add(map);
        }
        SimpleAdapter memoAdapter = new SimpleAdapter(
                context,
                list,
                android.R.layout.simple_list_item_2,
                new String[] {"main", "sub"},
                new int[] {android.R.id.text1, android.R.id.text2}
        );

        return memoAdapter;
    }

    // 現在日時をyyyy/MM/dd HH:mm:ss形式で取得する.
    public static String getNowDate(){
        final DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        final Date date = new Date(System.currentTimeMillis());
        return df.format(date);
    }


}
