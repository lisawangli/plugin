package com.example.pluginlib;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;

public interface IProxy {
    public FragmentActivity getProxyActivity();

    public void startActivityForResult(Intent intent,
                                       String className, int requestCode);

}