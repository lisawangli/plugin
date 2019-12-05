package com.example.pluginlib.resource;

import android.content.res.AssetManager;
import android.content.res.Resources;

public class MPluginResource {
    public Resources resources;
    public AssetManager assetManager;
    public Resources.Theme theme;

    public MPluginResource(Resources resources, AssetManager assetManager, Resources.Theme theme) {
        this.resources = resources;
        this.assetManager = assetManager;
        this.theme = theme;
    }
}
