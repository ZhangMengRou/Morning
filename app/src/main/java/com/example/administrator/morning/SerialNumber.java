package com.example.administrator.morning;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/1/25.
 */
public class SerialNumber extends Activity {

    private ImageView back = null;

    private ListView listView = null;
    private SimpleAdapter adapter = null;
    private List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    private String[] week = {"一","二","三","四","五","六"};
    private String[] num ={"000001","000002","000003","000004","000005","000006"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.serial_number);

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();
            }
        });
        listView = (ListView) findViewById(R.id.serial_number_listview);
        for (int j = 0; j < week.length; j++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("week", week[j]);
            map.put("num", num[j]);
            this.list.add(map);
        }
        Collections.reverse(list);
        adapter = new SimpleAdapter(this, list, R.layout.serial_number_listview_adapter,
                new String[] { "week" ,"num"}, new int[] { R.id.week,R.id.serial_number_num});
        listView.setAdapter(adapter);

    }
}
