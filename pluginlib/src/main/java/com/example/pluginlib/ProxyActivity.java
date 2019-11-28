package com.example.pluginlib;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

/**
 * 代理activity,管理插件activity的生命周期
 */
public class ProxyActivity extends FragmentActivity implements IProxy {

    private String mClassName;
    private PluginApk mPluginApk;
    private IPlugin mIplugin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClassName = getIntent().getStringExtra("className");
        Log.e("ProxyActivity","mClassName:"+mClassName);
        mPluginApk = PluginManager.getInstance().getPluginApk();
        launchPluginActivity();
    }

    private void launchPluginActivity() {
        if (mPluginApk==null){
            throw new NullPointerException("空指针异常");
        }
        try {
            Class<?> clazz = mPluginApk.mClassLoader.loadClass(mClassName);
            Object object = clazz.newInstance();
            if (object instanceof IPlugin){
                mIplugin = (IPlugin) object;
                mIplugin.attach(this);
                Bundle bundle = new Bundle();
                bundle.putInt("FROM",IPlugin.FROM_EXTERNAL);
                mIplugin.onCreate(bundle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Resources getResources() {
        return mPluginApk!=null?mPluginApk.mResources:super.getResources();
    }

    public AssetManager getAssets(){
        return mPluginApk!=null?mPluginApk.mAssetManager:super.getAssets();
    }

    @Override
    public ClassLoader getClassLoader() {
        return mPluginApk!=null?mPluginApk.mClassLoader:super.getClassLoader();
    }

    @Override
    public FragmentActivity getProxyActivity() {
        return this;
    }

    @Override
    public void startActivityForResult(Intent bundle, String className, int requestCode) {
        Intent intent = new Intent(this, ProxyActivity.class);

        if (bundle != null) {
            intent.putExtras(bundle);
        }

//        intent.putExtra(EXTRA_APK, mApkPath);
        intent.putExtra("className", className);

        startActivityForResult(intent, requestCode);
    }

//    @Override
//    public FragmentActivity getProxyActivity() {
//        return this;
//    }
//
//    @Override
//    public void startActivityForResult(Intent bundle, String className, int requestCode) {
//        Intent intent = new Intent(this, ProxyActivity.class);
//
//        if (bundle != null) {
//            intent.putExtras(bundle);
//        }
//
////        intent.putExtra(EXTRA_APK, mApkPath);
//        intent.putExtra("className", className);
//
//        startActivityForResult(intent, requestCode);
//
//    }
}
