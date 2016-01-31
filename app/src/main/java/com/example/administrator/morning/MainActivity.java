package com.example.administrator.morning;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
    public String name;
    private File file;
    private String QQ="185761855";
    private Bitmap bitmap;
    private Thread GetIcon,GetName;
    public EditText qq_num;

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
            Intent intent0 = new Intent(this, ClockInRecord.class);
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
        qq_num=new EditText(this);
        NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(this);

        effect = Effectstype.Slideleft;
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
                .withDuration(700)                                          //def
                .withEffect(effect)                                         //def Effectstype.Slidetop
                .withButton1Text("确定")                                      //def gone
                .withButton2Text("取消")                                  //def gone
                .setCustomView(R.layout.custom_view, v.getContext())         //.setCustomView(View or ResId,context)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {

                                       // qq_num=(EditText)findViewById(R.id.write_qq_number);

                                       // QQ=qq_num.getText().toString();
                                      //  System.out.println(QQ);
                                        GetIcon = new GetIcon();
                                        GetIcon.start();

                                        GetName = new GetName();
                                        GetName.start();


                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();


                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                        //Toast.makeText(v.getContext(), "i'm btn1", Toast.LENGTH_SHORT).show();
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(), "i'm btn2", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }


    public void saveBitmapFile(Bitmap bitmap){
        file=new File("/morning/pic/01.jpg");//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //获得头像
    private class GetIcon extends Thread{

        @Override
        public void run() {
            // TODO Auto-generated method stub
            try {

                //返回头像
                Request request = new Request.Builder()
                        .url("http://q2.qlogo.cn/headimg_dl?bs="+QQ+"&dst_uin=" +QQ+
                                "&dst_uin="+QQ+"&dst_uin=" +QQ+
                                "&spec=100&url_enc=0&referer=bu_interface&term_type=PC")
                        // .url(" http://img2.5sing.kgimg.com/force/T1HwJ5BybT1RXrhCrK_100_100.jpg")
                        .build();
                Response response;
                try {
                    response = client.newCall(request).execute();

                    InputStream icon=response.body().byteStream();
                    bitmap= BitmapFactory.decodeStream(icon);
                    Message msg2 = new Message() ;
                    msg2.what = 1;
                    msg2.obj = bitmap;
                    handler.sendMessage(msg2);

                } catch (Exception e) {

                    Message msg3 = new Message() ;
                    msg3.what = 3;
                    handler.sendMessage(msg3);

                    e.printStackTrace();
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }
    //获得昵称
    private class GetName extends Thread{
        @Override
        public void run() {
            // TODO Auto-generated method stub
            try {
                Request request2 = new Request.Builder()
                        .url("http://users.qzone.qq.com/fcg-bin/cgi_get_portrait.fcg?uins="+QQ)
                        .get().build();
                Call call = client.newCall(request2);
                call.enqueue(new Callback() {

                    public void onResponse(Response response) throws IOException {
                        byte[] bbb = response.body().bytes();
                        //for(byte b : bbb) System.out.print(b + " ");
                        //   System.out.println();
                        String s = new String(bbb, "GBK");
                        // System.out.println(s);
                        String[] str = s.split("\"");
                        String it = str[5];
                        name = it;
                       // System.out.println(name);

                    }

                    public void onFailure(Request arg0, IOException arg1) {

                        Message msg3 = new Message();
                        msg3.what = 3;
                        handler.sendMessage(msg3);
                    }
                });
                Message msg2 = new Message();
                msg2.what = 2;
                msg2.obj = name;
                handler.sendMessage(msg2);

            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

    @SuppressLint("HandlerLeak")
    //处理获得信息的消息
    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
                case 1:
                    bitmap = (Bitmap) msg.obj;
                    if(bitmap != null){
                        Message msgg = new Message() ;
                        msgg.what = 4;
                        _handler.sendMessage(msgg);

                    }
                    break;
                case 2:
                    name = (String) msg.obj;
                    if(name != null){
                        Message msgg = new Message() ;
                        msgg.what = 4;
                        _handler.sendMessage(msgg);

                    }
                    break;
                default:
                    Toast.makeText(MainActivity.this, "解析错误", Toast.LENGTH_SHORT).show();
                    break;

            }
        }

    };
    //处理子线程结束后发出的消息
    @SuppressLint("HandlerLeak")
    private Handler _handler = new Handler(){


        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what){
                case 4:
                    login=(TextView) findViewById(R.id.text_login);
                    login.setText(name);
                    ic=(CircleImageView) findViewById(R.id.ic);//不放这里会报空指针异常。。汪
                    ic.setImageBitmap(bitmap);

                   /* mUser p0 = new mUser();
                    //      saveBitmapFile(bitmap);
                    //BmobFile file1=new BmobFile(new File("/morning/pic/01.jpg"));
                    //  p0.setPic(file1);
                    p0.setQq_number(QQ);
                    p0.setUser_name(name);
                    p0.save(MainActivity.this, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            // TODO Auto-generated method stub
                            System.out.println("succeed");
                            //toast("添加数据成功，返回objectId为："+p2.getObjectId());
                        }

                        @Override
                        public void onFailure(int code, String msg) {
                            // TODO Auto-generated method stub
                            System.out.println("fail " + msg);
                            //toast("创建数据失败：" + msg);
                        }
                    });*/
                    break;
            }
        }

    };
}
