package com.example.pluginlib;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

public class PluginActivity extends FragmentActivity implements IPlugin {

    private Activity mProxyActivity;
    private int mFrom = IPlugin.FROM_EXTERNAL;
    @Override
    public void attach(IProxy proxyActivity) {
        mProxy = proxyActivity;
        mProxyActivity = proxyActivity.getProxyActivity();
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        if (saveInstanceState!=null){
            Log.e("PluginActivity","=====onCreate====");
            mFrom = saveInstanceState.getInt("FROM");
        }
        if (mFrom==FROM_INTERNAL){
            super.onCreate(saveInstanceState);
            mProxyActivity = this;
        }
    }


    public Resources getResources(){
        if (mFrom==FROM_INTERNAL) {
            return super.getResources();
        } else{
            return mProxyActivity.getResources();
        }
    }

    private IProxy mProxy;

    public void startActivity(Intent intent, Class<?> clazz) {
        startActivity(intent, clazz.getName());
    }

    public void startActivity(Intent intent, String className) {
        startActivityForResult(intent, className, -1);
    }


    public void startActivityForResult(Intent bundle, String className,
                                       int requestCode) {
        mProxy.startActivityForResult(bundle,className,requestCode);
    }


    @Override
    public void onStart() {
        if (mFrom==FROM_INTERNAL){
            super.onStart();
        }
    }

    @Override
    public void onRestart() {
        if (mFrom==FROM_INTERNAL){
            super.onRestart();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mFrom==FROM_INTERNAL){
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

    @Override
    public void onResume() {
        if (mFrom==FROM_INTERNAL){
            super.onResume();
        }
    }

    @Override
    public void onPause() {
        if (mFrom==FROM_INTERNAL){
            super.onPause();
        }
    }

    @Override
    public void onStop() {
        if (mFrom==FROM_INTERNAL){
            super.onStop();
        }
    }

    @Override
    public <T extends View> T findViewById(int id) {
        if (mFrom==FROM_INTERNAL) {
            return super.findViewById(id);
        } else{
            return mProxyActivity.findViewById(id);
        }
    }

    @Override
    public void setContentView(int layoutID) {
        if (mFrom==FROM_INTERNAL) {
            super.setContentView(layoutID);
        }else {
            mProxyActivity.setContentView(layoutID);
        }
    }

    @Override
    public void onDestory() {
        if (mFrom==FROM_INTERNAL){
            super.onDestroy();
        }
    }
}
