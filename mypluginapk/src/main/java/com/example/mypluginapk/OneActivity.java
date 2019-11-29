package com.example.mypluginapk;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;

import com.example.pluginlib.PluginActivity;

public class OneActivity extends PluginActivity {

    ImageView imageView;
    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_one);
        imageView = findViewById(R.id.image);
        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                imageView.setBackground(getResources().getDrawable(R.mipmap.ic_launcher));
            }
        });

    }
}
