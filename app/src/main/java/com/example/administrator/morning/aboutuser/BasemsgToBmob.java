package com.example.administrator.morning.aboutuser;

import android.util.Log;
import android.view.View;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by Zmr on 2016/2/2.
 */
public class BasemsgToBmob {

    private String path ="/sdcard/Morning/pic/head.png";
    private String QQ_b;
    private String name_b;
    private View v_b;

    public boolean sendmsg(String name, String QQ, View v)
    {
        name_b=name;
        QQ_b=QQ;
        v_b=v;
        upload(v_b);
        return true;
    }
    public void upload(View v){
        final BmobFile file=new BmobFile(new File(path));
        file.uploadblock(v_b.getContext(), new UploadFileListener() {
            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                mUser p = new mUser();
                p.setUser_name(name_b);
                p.setQq_number(QQ_b);
              //  BmobFile file=new BmobFile(new File(path));
                p.setPic(file);
                p.save(v_b.getContext());
            }
            @Override
            public void onFailure(int arg0, String arg1) {
                // TODO Auto-generated method stub
               // toast("上传失败 "+arg1);
                Log.i("---------", "------error "+arg1);
            }
        });
    }

}
