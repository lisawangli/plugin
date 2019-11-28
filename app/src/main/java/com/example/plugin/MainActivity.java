package com.example.plugin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.example.pluginlib.PluginManager;
import com.example.pluginlib.ProxyActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,ProxyActivity.class);
                String otherApkMainActivityName = PluginManager.getInstance().getPluginApk().packageInfo.activities[0].name;
                Log.e("MainActivity","===otherApkMainActivityName==="+otherApkMainActivityName);
                intent.putExtra("className",otherApkMainActivityName);
                startActivity(intent);

            }
        });
        PluginManager.getInstance().init(this);
        findViewById(R.id.loader).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copy();
                Log.e("MainActivity","======"+apkPath);
                File file = new File(apkPath);
                Log.e("MainActivity","file==="+file.exists());
                PluginManager.getInstance().loadApk(apkPath);
            }
        });
    }

    String apkPath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/aa.apk";
    void copy(){
        //将assets的资源文件拷贝到android设备上
        try {
            InputStream inputStream=getAssets().open("aa.apk");
            File file = new File(apkPath);
            if (file.exists()) file.delete();

            FileOutputStream out=new FileOutputStream(file);
            byte[] buffer=new byte[1024];
            int len=-1;
            while ((len=inputStream.read(buffer))!=-1){
                out.write(buffer,0,len);
            }
            out.flush();//刷新缓存区
            inputStream.close();
            out.close();

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
