package com.example.pluginlib.resource;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Build;

import java.lang.reflect.Method;

public class MPluginResourceManager {
    private static MPluginResourceManager INSTANCE;
    private AssetManager mAssetManager;
    private Resources mResource;
    private Resources.Theme mTheme;
    private ActivityInfo mActivityInfo;
    private PackageInfo packageInfo;

    public static MPluginResourceManager getInstance(){
        if (INSTANCE==null){
            synchronized (MPluginResourceManager.class){
                if (INSTANCE==null){
                    INSTANCE = new MPluginResourceManager();
                }
            }
        }
        return INSTANCE;
    }

    public MPluginResource loadResources(String dexPath, Context context){
        initializeActivityInfo(dexPath,context);
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath",String.class);
            addAssetPath.invoke(assetManager,dexPath);
            mAssetManager = assetManager;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Resources superRes = context.getResources();
        superRes.getDisplayMetrics();
        superRes.getConfiguration();
        if(mActivityInfo.theme>0){
            context.setTheme(mActivityInfo.theme);
        }
        Resources.Theme superTheme = context.getTheme();
        mResource = new Resources(mAssetManager,superRes.getDisplayMetrics(),superRes.getConfiguration());
        mTheme = mResource.newTheme();
        mTheme.setTo(superTheme);
        mTheme.applyStyle(mActivityInfo.theme,true);
        return new MPluginResource(mResource,mAssetManager,mTheme);
    }

    private void initializeActivityInfo(String dexPath,Context context){
        packageInfo = context.getPackageManager().getPackageArchiveInfo(dexPath,PackageManager.GET_ACTIVITIES|PackageManager.GET_SERVICES);
        if (packageInfo.activities!=null&&packageInfo.activities.length>0){
            int defaultTheme = packageInfo.applicationInfo.theme;
            for (ActivityInfo a:packageInfo.activities){
                mActivityInfo = a;
                if (mActivityInfo.theme==0){
                    if (defaultTheme!=0){
                        mActivityInfo.theme = defaultTheme;
                    }else {
                        if (Build.VERSION.SDK_INT>=14){
                            mActivityInfo.theme = android.R.style.Theme_DeviceDefault;
                        }else{
                            mActivityInfo.theme = android.R.style.Theme;
                        }
                    }
                }
            }
        }
    }
}
