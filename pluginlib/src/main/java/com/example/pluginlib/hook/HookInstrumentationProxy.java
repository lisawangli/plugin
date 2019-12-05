package com.example.pluginlib.hook;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.example.pluginlib.ProxyActivity;

import java.lang.reflect.Method;
import java.util.List;

public class HookInstrumentationProxy extends Instrumentation {

    public PackageManager mPackManager;
    private Instrumentation mInstrmentation;
    private Context mContext;
    public static final String REAL_INTENT = "realIntent";
    public static final String TAG = HookInstrumentationProxy.class.getSimpleName();
    public HookInstrumentationProxy(Context context,PackageManager packageManager,Instrumentation instrumentation){
        mPackManager = packageManager;
        mContext = context;
        mInstrmentation = instrumentation;
    }

    /**
     * 从activity到AMS过程
     */
    public ActivityResult execStartActivity(Context who, IBinder contextThread, IBinder token, Activity target, Intent intent, int requestCode, Bundle options){
        List<ResolveInfo> resolveInfos = mPackManager.queryIntentActivities(intent,PackageManager.MATCH_ALL);
        Intent proxyIntent = intent;
        Log.i(TAG,"HookInstrumentationProxy execStartActivity");
        if (resolveInfos!=null&&resolveInfos.size()==0){
            proxyIntent = new Intent();
            proxyIntent.setComponent(new ComponentName(mContext,ProxyActivity.class));
            proxyIntent.putExtra(REAL_INTENT,intent);
            Log.i(TAG,"proxyIntent replace intent");
        }
        try {
            Class<?> instrumentationClass = Class.forName("android.app.Instrumentation");
            Method execStartActivityRecord = instrumentationClass.getDeclaredMethod("execStartActivity",Context.class,IBinder.class,IBinder.class,Activity.class,Intent.class,int.class,Bundle.class);
            execStartActivityRecord.setAccessible(true);
            ActivityResult activityResult = (ActivityResult) execStartActivityRecord.invoke(mInstrmentation,who,contextThread,token,target,proxyIntent,requestCode,options);
            Log.i(TAG,"instrmentation execStartActivityMethod");
            return activityResult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //activityThread 到handler启动activity过程
    public Activity newActivity(ClassLoader cl,String className,Intent intent) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        if (intent.getParcelableExtra(REAL_INTENT)!= null){
            intent = intent.getParcelableExtra(REAL_INTENT);
            className = intent.getComponent().getClassName();
        }
        return (Activity) cl.loadClass(className).newInstance();
    }
}
