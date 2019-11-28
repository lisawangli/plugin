package com.example.pluginlib;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class PluginManager {

    private static final PluginManager instance = new PluginManager();

    public static PluginManager getInstance(){
        return instance;
    }

    private Context mContext;
    public void init(Context context){
        this.mContext = context.getApplicationContext();
    }

    PluginApk mPluginApk;
    PackageInfo packageInfo;

    public PluginApk getPluginApk(){
        return mPluginApk;
    }

    public PackageInfo getPackageInfo(){
        return packageInfo;
    }

    public void loadApk(String apkPath){
         packageInfo = mContext.getPackageManager().getPackageArchiveInfo(apkPath,PackageManager.GET_ACTIVITIES|PackageManager.GET_SERVICES);
        if (packageInfo==null)
            return;

        DexClassLoader classLoader = createDexClassLoader(apkPath);
        AssetManager am = createAssertManager(apkPath);
        Resources resources = createResource(am);
        mPluginApk = new PluginApk(packageInfo,resources,classLoader);
    }

    private DexClassLoader createDexClassLoader(String apkPath) {
        File file = mContext.getDir("dex",Context.MODE_PRIVATE);

        DexClassLoader dexClassLoader = new DexClassLoader(apkPath,file.getPath(),null,mContext.getClassLoader());

        return dexClassLoader;
    }

    private AssetManager createAssertManager(String apkPath){
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method method = AssetManager.class.getDeclaredMethod("addAssetPath",String.class);
            method.invoke(assetManager,apkPath);
            return  assetManager;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Resources createResource(AssetManager am){
        Resources res = mContext.getResources();
        return new Resources(am,res.getDisplayMetrics(),res.getConfiguration());
    }

}
