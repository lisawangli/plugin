package com.example.pluginlib;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

/**
 * 代理activity,管理插件activity的生命周期
 */
public class ProxyActivity extends Activity {

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
}
