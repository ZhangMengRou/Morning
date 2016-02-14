package com.example.administrator.morning;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.morning.Calendar.CalendarActivity;
import com.example.administrator.morning.aboutuser.BasemsgToBmob;
import com.example.administrator.morning.com.example.administrator.util.NetworkUtil;
import com.example.administrator.morning.mainview.DragLayout;
import com.example.administrator.morning.mainview.DragLayout.DragListener;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.nineoldandroids.view.ViewHelper;
import com.squareup.okhttp.OkHttpClient;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import cn.bmob.v3.Bmob;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2016/1/25.
 */
public class MainActivity extends Activity{
    private DragLayout dl;
    private ListView lv;
    private TextView us;

    private TextView login_it;
    private ImageView iv_icon, iv_bottom,add;
    private List<HashMap<String, Object>> mData;

    private ListView listView = null;
    private SimpleAdapter adapter = null;
    private List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    private String[] title = {"#冻成狗了111","#冻成狗了2222222222","#冻成狗了","测试数据11111","测试数据2","测试数据3"};
    private String[] num ={"1","2","3","4","5","6"};
    private TextView login;
    private Effectstype effect;//对话框的飞入形式
    public OkHttpClient client = new OkHttpClient();//网络获取
    private CircleImageView ic;
    private CircleImageView icm;
    public String name_;
    private File file;
    private String QQ="915849243";
    public EditText qq_num;
    private NetworkInfo networkInfo;
    private ConnectivityManager manager;

    private SharedPreferences sharedPreference;
    private SharedPreferences.Editor editor;
    private Boolean is_login_is=false;
    private Button send;

    private String path ="/sdcard/Morning/pic/head.png";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);
        initDragLayout();
        initView();
        sharedPreference = PreferenceManager.getDefaultSharedPreferences(this);


        send=(Button)findViewById(R.id.send);


        Bmob.initialize(this, "5dcef2bf784ec223e5c86ae4e71d0172");

        listView = (ListView) findViewById(R.id.listView);
        for (int j = 0; j < title.length; j++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("title", title[j]);
            map.put("num", num[j]);
            this.list.add(map);
        }
        adapter = new SimpleAdapter(this, list, R.layout.mainlistview_adapter,
                new String[] { "title" ,"num"}, new int[] { R.id.title ,R.id.num});
        listView.setAdapter(adapter);
        boolean isRemember = sharedPreference.getBoolean("is_login", false);
        if (isRemember) {

            Message msg = new Message();
            msg.what = 1;
            usu_handler.sendMessage(msg);
        }
    }

    private void initDragLayout() {
        dl = (DragLayout) findViewById(R.id.dl);
        dl.setDragListener(new DragListener() {
            @Override
            public void onOpen() {
                lv.smoothScrollToPosition(new Random().nextInt(30));
            }

            @Override
            public void onClose() {
                // shake();
            }

            @Override
            public void onDrag(float percent) {
                ViewHelper.setAlpha(iv_icon, 1 - percent);
            }
        });
    }

    private void initView() {
        iv_icon = (ImageView) findViewById(R.id.iv_icon);
        iv_bottom = (ImageView) findViewById(R.id.iv_bottom);
        add=(ImageView) findViewById(R.id.add);
        us=(TextView) findViewById(R.id.us);
        login=(TextView)findViewById(R.id.text_login);
        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialogShow(arg0);

            }
        });


        lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(new ArrayAdapter<String>(MainActivity.this,
                R.layout.main_item_text, new String[] { " 我的打卡记录"," 我的抽奖序列号"," 我的中奖记录"," 退出登录" }));
        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {


                switch (position){
                    case 0:
                        // Intent intent0 = new Intent(ClockInRecord.this, ClockInRecord.class);
                        Intent intent0 = new Intent(MainActivity.this, CalendarActivity.class);
                        MainActivity.this.startActivity(intent0);
                       // Toast.makeText(MainActivity.this,""+position,Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        Intent intent1 = new Intent(MainActivity.this, SerialNumber.class);
                        MainActivity.this.startActivity(intent1);
                       // Toast.makeText(MainActivity.this,""+position,Toast.LENGTH_LONG).show();

                        break;
                    case 2:

                        Intent intent2 = new Intent(MainActivity.this, WinningRecord.class);
                        MainActivity.this.startActivity(intent2);
                       // Toast.makeText(MainActivity.this,""+position,Toast.LENGTH_LONG).show();

                        break;
                    case 3:
                        Toast.makeText(MainActivity.this,""+position,Toast.LENGTH_LONG).show();

                        //finish();
                        break;
                }
            }
        });
        iv_icon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dl.open();
            }
        });
    }

/*    private void shake() {
        iv_icon.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
    }*/


    public void dialogShow(View v){
        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(this);
        effect = Effectstype.Slideleft;
        final View view = View.inflate(v.getContext(), R.layout.custom_view, null);
        // Toast.makeText(v.getContext(), "i'm btn1", Toast.LENGTH_SHORT).show();
        dialogBuilder
                .withTitle("登录")                                  //.withTitle(null)  no title
                .withTitleColor("#FFFFFF")                                  //def
                .withDividerColor("#11000000")                              //def
                .withMessage("请输入QQ账号：")                     //.withMessage(null)  no Msg
                .withMessageColor("#FFFFFFFF")                              //def  | withMessageColor(int resid)
                .withDialogColor("#aec9c9")                               //def  | withDialogColor(int resid)                               //def
                .withIcon(R.drawable.trash)
                .isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
                //  .isCancelable(true)
                .withDuration(700)                                          //def
                .withEffect(effect)                                         //def Effectstype.Slidetop
                .withButton1Text("确定")                                      //def gone
                .withButton2Text("取消")                                  //def gone
                .setCustomView(view, v.getContext())         //.setCustomView(View or ResId,context)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //qq_num = (EditText) findViewById(R.id.write_qq_number);

                        manager = (ConnectivityManager) MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
                        networkInfo = manager.getActiveNetworkInfo();

                        if (networkInfo == null || !networkInfo.isAvailable()) {
                            Toast.makeText(MainActivity.this, "网络似乎出了点问题哦", Toast.LENGTH_SHORT).show();
                        } else {
                            try {

                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {

                                            qq_num = (EditText) view.findViewById(R.id.write_qq_number);
                                            if (qq_num == null) System.out.println("null");

                                            QQ = qq_num.getText().toString();
                                            System.out.println(QQ);

                                            //获取名称
                                            String name = new NetworkUtil().get_QQ_Name(QQ);
                                            //获取图片
                                            Bitmap header = new NetworkUtil().get_QQ_Header(QQ);
                                            Bundle bundle = new Bundle();
                                            bundle.putString("name", name);
                                            bundle.putParcelable("header", header);

                                            Message msg = new Message();
                                            msg.what = 0x1001;
                                            msg.setData(bundle);
                                            handler.sendMessage(msg);
                                            //保存图片
                                            saveBitmapFile(header, view);

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();


                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                        }
                        dialogBuilder.dismiss();
                    }

                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(), "取消", Toast.LENGTH_SHORT).show();
                        dialogBuilder.dismiss();
                    }
                })
                .show();
    }


    public void saveBitmapFile(Bitmap bitmap,View view){
        file=new File("/sdcard/" + "/Morning/pic/");//将要保存图片的路径
        try {
            file.mkdirs();
            File f=new File(file.getPath()+"/head.png");
            System.out.print("11111  "+f+"");
            // if(f.exists())
            f.createNewFile();
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            editor = sharedPreference.edit();
            editor.putBoolean("is_login", true);
            editor.putString("name",name_);
            editor.clear();
            editor.commit();
            new BasemsgToBmob().sendmsg(name_,QQ,view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x1001:
                    Bundle data = msg.getData();
                    name_=(String) data.get("name");
                    String name = (String) data.get("name");
                    Bitmap header = (Bitmap) data.get("header");
                    login=(TextView) findViewById(R.id.text_login);
                    login.setText(name);
                    ic=(CircleImageView) findViewById(R.id.iv_bottom);//不放这里会报空指针异常。。汪
                    ic.setImageBitmap(header);
                    icm=(CircleImageView) findViewById(R.id.iv_icon);
                    icm.setImageBitmap(header);
                    editor = sharedPreference.edit();
                    editor.putBoolean("is_login", true);
                    editor.putString("name", name);
                    editor.putString("qq", QQ);
                    editor.commit();
            }
        }
    };
    @SuppressLint("HandlerLeak")
    private Handler usu_handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    login=(TextView) findViewById(R.id.text_login);
                    name_=sharedPreference.getString("name","11111");
                    login.setText(name_);
                    Bitmap bitmap= BitmapFactory.decodeFile(path);
                    ic=(CircleImageView) findViewById(R.id.iv_bottom);
                    ic.setImageBitmap(bitmap);
                    icm=(CircleImageView) findViewById(R.id.iv_icon);
                    icm.setImageBitmap(bitmap);
                    break;
                case 2:
                    break;
            }
        }
    };
}

