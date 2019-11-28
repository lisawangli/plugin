package com.example.pluginlib;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PluginActivity extends Activity implements IPlugin {

    private Activity mProxyActivity;
    private int mFrom = IPlugin.FROM_EXTERNAL;
    @Override
    public void attach(Activity proxyActivity) {
        mProxyActivity = proxyActivity;
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        if (saveInstanceState!=null){
            mFrom = saveInstanceState.getInt("FROM");
        }
        if (mFrom==FROM_INTERNAL){
            super.onCreate(saveInstanceState);
            mProxyActivity = this;
        }
    }

    @Override
    public void onStart() {
        if (mFrom==FROM_INTERNAL){
            super.onStart();
        }
    }

    @Override
    public void onRestart() {
        if (mFrom==FROM_INTERNAL){
            super.onRestart();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mFrom==FROM_INTERNAL){
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

    @Override
    public void onResume() {
        if (mFrom==FROM_INTERNAL){
            super.onResume();
        }
    }

    @Override
    public void onPause() {
        if (mFrom==FROM_INTERNAL){
            super.onPause();
        }
    }

    @Override
    public void onStop() {
        if (mFrom==FROM_INTERNAL){
            super.onStop();
        }
    }

    @Override
    public void setContentView(int layoutID) {
        if (mFrom==FROM_INTERNAL) {
            super.setContentView(layoutID);
        }else {
            mProxyActivity.setContentView(layoutID);
        }
    }

    @Override
    public void onDestory() {
        if (mFrom==FROM_INTERNAL){
            super.onDestroy();
        }
    }
}
