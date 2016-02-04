package com.example.administrator.morning;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.morning.Calendar.CalendarActivity;
import com.example.administrator.morning.aboutuser.BasemsgToBmob;
import com.example.administrator.morning.com.example.administrator.util.NetworkUtil;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.Bmob;
import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private ListView listView = null;
    private SimpleAdapter adapter = null;
    private List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    private String[] title = {"#冻成狗了","#冻成狗了","#冻成狗了","测试数据1","测试数据2","测试数据3"};
    private String[] num ={"1","2","3","4","5","6"};
    private TextView login;
    private Effectstype effect;//对话框的飞入形式
    public OkHttpClient client = new OkHttpClient();//网络获取
    private CircleImageView ic;
    public String name_;
    private File file;
    private String QQ="915849243";
    public EditText qq_num;
    private NetworkInfo networkInfo;
    private ConnectivityManager manager;

    private SharedPreferences sharedPreference;
    private SharedPreferences.Editor editor;
   // private Boolean is_login=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.clock_in_record) {
        //    Intent intent0 = new Intent(this, ClockInRecord.class);
            Intent intent0 = new Intent(this, CalendarActivity.class);
            this.startActivity(intent0);
        } else if (id == R.id.serial_number) {
            Intent intent1 = new Intent(this, SerialNumber.class);
            this.startActivity(intent1);

        } else if (id == R.id.winning_record) {
            Intent intent2 = new Intent(this, WinningRecord.class);
            this.startActivity(intent2);

         } else if (id == R.id.share_it) {


        }

       // login=(TextView) findViewById(R.id.text_login);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void myloginlistener(View v){

        dialogShow(v);
      //  Toast.makeText(v.getContext(), "i'm btn1", Toast.LENGTH_SHORT).show();

          }
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
            /*editor = sharedPreference.edit();
            editor.putBoolean("is_login", true);
            editor.clear();
            editor.commit();*/
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
                    ic=(CircleImageView) findViewById(R.id.ic);//不放这里会报空指针异常。。汪
                    ic.setImageBitmap(header);
            }
        }
    };



}
