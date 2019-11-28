package com.example.mypluginapk;

import android.os.Bundle;

import com.example.pluginlib.PluginActivity;

public class OneActivity extends PluginActivity {

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_one);
    }
}
