package mplugindemo.shengyuan.com.mplugin_demo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mypluginapk.R;
import com.example.pluginlib.PluginActivity;

public class PluginApkDemoActivity extends PluginActivity{

    TextView tv;
    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_demo);
        tv = findViewById(R.id.clickme);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(getFromJNI()+" 数据");
            }
        });
    }

    static {
        System.loadLibrary("hello_jni");
    }

    public native String getFromJNI();
}
