package com.example.mypluginapk;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.pluginlib.PluginActivity;
import com.example.pluginlib.ProxyActivity;

public class MainActivity extends PluginActivity {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.e("MainActivity","======onCreate====");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
