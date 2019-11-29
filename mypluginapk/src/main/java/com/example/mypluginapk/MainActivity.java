package com.example.mypluginapk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.pluginlib.PluginActivity;
import com.example.pluginlib.PluginManager;
import com.example.pluginlib.ProxyActivity;

public class MainActivity extends PluginActivity {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.e("MainActivity","======onCreate====");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                startActivity(intent,OneActivity.class);
            }
        });
    }
}
