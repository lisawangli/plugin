package com.example.plugin;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Utils {

    public static String copyAssetAndWrite(Context context,String fileName){
        File cacheDir = context.getCacheDir();
        if (!cacheDir.exists()){
            cacheDir.mkdirs();
        }
        File outFile = new File(cacheDir,fileName);
        if (!outFile.exists()){
            try {
                boolean res = outFile.createNewFile();
                if (res){
                    InputStream inputStream = context.getAssets().open(fileName);
                    FileOutputStream fos = new FileOutputStream(outFile);
                    byte[] buffer = new byte[inputStream.available()];
                    int byteCount;
                    while ((byteCount=inputStream.read(buffer))!=-1){
                        fos.write(buffer,0,byteCount);
                    }
                    fos.flush();
                    inputStream.close();
                    fos.close();
                    return outFile.getAbsolutePath();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            return outFile.getAbsolutePath();
        }
        return "";
    }
}
