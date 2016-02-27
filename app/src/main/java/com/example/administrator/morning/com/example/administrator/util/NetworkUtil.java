package com.example.administrator.morning.com.example.administrator.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.morning.aboutuser.mUser;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;

/**
 * Created by EK on 2016/2/1.
 */
public class NetworkUtil {

    public static String ClassForName = "NetworkUtil";

    private OkHttpClient okHttpClient = new OkHttpClient();
    private String name = "null";
    private Bitmap header_bitmap = null;
    private String url = null;
    private String user_qq = "2308666855";
    private String user_id = null;
    private static int flag = 0;
    String content_ = null;

    private Handler handler_ = null;

    public NetworkUtil(Handler handler) {
        this.handler_ = handler;
    }

    public String get_QQ_Name(String QQ) {

        Request request = new Request.Builder()
                .url("http://users.qzone.qq.com/fcg-bin/cgi_get_portrait.fcg?uins=" + QQ)
                .get().build();
        Response response;
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                byte[] bytes = response.body().bytes();
                String body = new String(bytes, "GBK");
                String[] strs = body.split("\"");
                name = strs[5];
            } else {
                Log.e(ClassForName, "fetch qq name error");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(ClassForName, name);
        return name;
    }

    public Bitmap get_QQ_Header(String QQ) {

        Request request = new Request.Builder()
                .url("http://q2.qlogo.cn/headimg_dl?bs=" + QQ + "&dst_uin=" + QQ +
                        "&dst_uin=" + QQ + "&dst_uin=" + QQ +
                        "&spec=100&url_enc=0&referer=bu_interface&term_type=PC")
                // .url(" http://img2.5sing.kgimg.com/force/T1HwJ5BybT1RXrhCrK_100_100.jpg")
                .build();
        Response response;
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                InputStream is = response.body().byteStream();
                header_bitmap = BitmapFactory.decodeStream(is);
                Log.d(ClassForName, "success");
            } else {
                Log.e(ClassForName, "fetch QQ header error");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return header_bitmap;
    }

    public void get_user_ic(final Activity activity, String id, final ImageView im) {

        //下载图片
        BmobQuery<mUser> query = new BmobQuery<mUser>();
        query.getObject(activity, id, new GetListener<mUser>() {
            @Override
            public void onSuccess(mUser user) {
                BmobFile icon = user.getPic();
                icon.loadImage(activity, im);
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }

    /**
     * 根据URL获取Bitmap
     */
    public Boolean getHttpBitmap(String id, final View v, String content, final int num) {

        Log.d(ClassForName, "idid " + id);

        content_ = content;

        user_id = id;
        BmobQuery<mUser> query = new BmobQuery<mUser>();

        query.addWhereEqualTo("objectId", id);

        query.setLimit(1);

        query.findObjects(v.getContext(), new FindListener<mUser>() {
            @Override
            public void onSuccess(List<mUser> object) {

                user_qq = object.get(0).getQq_number();

                url = object.get(0).getPic().getUrl();
                Log.d(ClassForName, "bbb " + user_qq);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Looper.prepare();
                        try {
                            Bitmap bitmap1 = null;
                            URL myUrl;
                            url = "http://file.bmob.cn/" + url;
                            //Toast.makeText(MainActivity.this, url+"", Toast.LENGTH_SHORT).show();
                            myUrl = new URL(url);
                            HttpURLConnection conn = (HttpURLConnection) myUrl.openConnection();
                            conn.setConnectTimeout(5000);
                            conn.connect();
                            InputStream is = conn.getInputStream();
                            bitmap1 = BitmapFactory.decodeStream(is);
                            is.close();
                            Log.d(ClassForName, "aaa " + bitmap1);
                            saveBitmapFile(bitmap1, user_id, num);
                        } catch (MalformedURLException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        Looper.loop();
                    }
                }).start();
                //Log.d(ClassForName, "bbb "+url);

            }

            @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub
                Log.d(ClassForName, "nottts" + msg);

            }
        });
        /*while (flag!=1)
        {

            Log.d("waitttttt","11");

        }
        flag=0;*/
        return true;
    }


    public Bitmap getBitmap(String url) {
        Bitmap bitmap = null;
        URL myUrl;

        try {
            myUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) myUrl.openConnection();
            conn.setConnectTimeout(5000);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);

            is.close();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //返回bitmap
        return bitmap;
    }

    public void saveBitmapFile(Bitmap bitmap, String id, int num) {
        File file = new File("/sdcard/" + "/Morning/pic/users/" + id);//将要保存图片的路径
        try {
            file.mkdirs();
            File f = new File(file.getPath() + "/head.png");
            Log.d(ClassForName, "ff  " + f);
            // if(f.exists())
            f.createNewFile();
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            //flag=1;


            sendmsg(num);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendmsg(int num) {
        Bundle bundle = new Bundle();
        bundle.putString("user", user_id);
        bundle.putString("content", content_);

        Log.d("hanlernum", "onDataChange: UUU" + num);
        Log.d("1yes", "onDataChange: UUU" + user_id);
        final Message msg = new Message();
        msg.what = 4;
        msg.setData(bundle);
        double x = 0;
        if (num > 2) {
            x = 400 * Math.log(5 * num);
        } else {
            x = 300 * num;
        }
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {

                handler_.sendMessage(msg);
            }
        }, (int) x);
        //别打我。。汪汪汪。。下手轻点。。如果用户有100个。。就延迟了。。50秒。。望天。。
        //然而并没有那么多人用。。看我蠢真的眼神。。有什么别的推荐么TUT求拯救
        //然后我用对数小小的优化了一下。。汪汪汪

    }


}
