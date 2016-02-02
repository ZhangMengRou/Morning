package com.example.administrator.morning.com.example.administrator.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by EK on 2016/2/1.
 */
public class NetworkUtil {

    public static String ClassForName = "NetworkUtil";

    private OkHttpClient okHttpClient = new OkHttpClient();
    private String name = "null";
    private Bitmap header_bitmap = null;
;
    public String get_QQ_Name(String QQ) {

        Request request = new Request.Builder()
                .url("http://users.qzone.qq.com/fcg-bin/cgi_get_portrait.fcg?uins="+QQ)
                .get().build();
        Response response;
        try {
            response = okHttpClient.newCall(request).execute();
            if(response.isSuccessful()) {
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
                .url("http://q2.qlogo.cn/headimg_dl?bs="+ QQ +"&dst_uin=" + QQ +
                        "&dst_uin="+ QQ +"&dst_uin=" + QQ +
                        "&spec=100&url_enc=0&referer=bu_interface&term_type=PC")
                // .url(" http://img2.5sing.kgimg.com/force/T1HwJ5BybT1RXrhCrK_100_100.jpg")
                .build();
        Response response;
        try {
            response = okHttpClient.newCall(request).execute();
            if(response.isSuccessful()) {
                InputStream is= response.body().byteStream();
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

}
