package com.example.administrator.morning;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/1/25.
 */
public class WinningRecord extends Activity {

    private ListView listView = null;
    private SimpleAdapter adapter = null;
    private List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    private String[] week = {"一","二","三","四","五","六"};
    private String[] win ={"未中奖","未中奖","未中奖","未中奖","未中奖","未中奖"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.winning_record);

        listView = (ListView) findViewById(R.id.winning_record_listview);
        for (int j = 0; j < week.length; j++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("week", week[j]);
            map.put("win", win[j]);
            this.list.add(map);
        }
        adapter = new SimpleAdapter(this, list, R.layout.winning_record_listview_adapter,
                new String[] { "week" ,"win"}, new int[] { R.id.winning_record_week,R.id.winning_record_win});
        listView.setAdapter(adapter);

    }
}
