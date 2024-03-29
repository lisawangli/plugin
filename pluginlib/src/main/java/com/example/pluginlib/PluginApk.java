package com.example.pluginlib;

import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;

import dalvik.system.DexClassLoader;

public class PluginApk {

    public PackageInfo packageInfo;
    public Resources mResources;
    public AssetManager mAssetManager;
    public DexClassLoader mClassLoader;
    public ClassLoader mSoClassLoader;

    public PluginApk(PackageInfo packageInfo,Resources resources,DexClassLoader dexClassLoader,ClassLoader mSoClassLoader){
        this.packageInfo = packageInfo;
        this.mResources = resources;
        mAssetManager = mResources.getAssets();
        this.mClassLoader = dexClassLoader;
        this.mSoClassLoader = mSoClassLoader;
    }
}
