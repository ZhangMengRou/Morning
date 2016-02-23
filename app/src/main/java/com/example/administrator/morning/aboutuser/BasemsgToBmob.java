package com.example.administrator.morning.aboutuser;

import android.util.Log;
import android.view.View;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by Zmr on 2016/2/2.
 */
public class BasemsgToBmob {

    private String path = "/sdcard/Morning/pic/head.png";
    private String QQ_b;
    private String name_b;
    private View v_b;
    private String myid;


    public boolean sendmsg(String name, String QQ, View v) {
        name_b = name;
        QQ_b = QQ;
        v_b = v;
        BmobQuery<mUser> query = new BmobQuery<mUser>();
        query.addWhereEqualTo("qq_number", QQ_b);
        query.setLimit(1);
        //执行查询方法
        query.findObjects(v.getContext(), new FindListener<mUser>() {
            @Override
            public void onSuccess(List<mUser> object) {


                mUser user = object.get(0);
                String id = user.getObjectId();
                update(v_b, id);

                Log.i("---------", "-----YES " + id);
            }

            @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub
                upload(v_b);
                Log.i("---------", "----- " + msg);

            }
        });

        return true;
    }

    public void upload(final View v) {
        final BmobFile file = new BmobFile(new File(path));
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
                // String id=p.getObjectId();
                Log.i("---------", "-----YES 首次注册成功");
            }

            @Override
            public void onFailure(int arg0, String arg1) {
                // TODO Auto-generated method stub
                // toast("上传失败 "+arg1);
                Log.i("---------", "------error " + arg1);

            }
        });
    }

    public void update(final View v, String id) {

        myid = id;
        final BmobFile file = new BmobFile(new File(path));
        file.uploadblock(v_b.getContext(), new UploadFileListener() {
            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                mUser p = new mUser();
                p.setUser_name(name_b);

                p.setPic(file);

                p.update(v_b.getContext(), myid, new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        // TODO Auto-generated method stub

                        // String id=p.getObjectId();
                        Log.i("---------", "-----YES 更新成功");
                    }

                    @Override
                    public void onFailure(int arg0, String arg1) {
                        // TODO Auto-generated method stub
                        // toast("上传失败 "+arg1);
                        Log.i("---------", "------error " + arg1);

                    }
                });
                Log.i("---------", "-----YES ");
            }

            @Override
            public void onFailure(int arg0, String arg1) {
                // TODO Auto-generated method stub
                // toast("上传失败 "+arg1);
                Log.i("---------", "------error " + arg1);

            }
        });

    }

}
