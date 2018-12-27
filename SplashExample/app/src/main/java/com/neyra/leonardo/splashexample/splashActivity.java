package com.neyra.leonardo.splashexample;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class splashActivity extends Activity {

    private final int MY_PERMISSIONS_REQUEST = 1;
    private final int duration_Splash = 2500;
    private final String[] permissions = {Manifest.permission.READ_CALENDAR,
                                            Manifest.permission.READ_CONTACTS,
                                            Manifest.permission.CAMERA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        if (hasPermissions(this,permissions)){
            Toast.makeText(this, "Todos los permisos aceptados", Toast.LENGTH_LONG).show();
            setSplash();
        }
        else{
            requestPermissions();
        }
    }

    //Cuando se acepta o rechaza el permiso
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1 && grantResults.length > 0) {
            for (int i = 0; i < grantResults.length; i++){
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Acceso Denegado. Saliendo...", Toast.LENGTH_LONG).show();

                    finish();
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            }
            Toast.makeText(this, "OK, Aceptó todos los permisos", Toast.LENGTH_LONG).show();
            setSplash();
        }
    }

    //Ejecutar splash
    private void setSplash(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(splashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            };
        }, duration_Splash);
    }

    //Determinar si tiene todos los permisos
    public static boolean hasPermissions(Context context,String... permissions){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && context != null && permissions!=null) {
            for (String permision : permissions) {
                if (ContextCompat.checkSelfPermission(context, permision) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    //Pedir algun permiso
    public void requestPermissions(){
        List<String> P = new ArrayList<>();
        for (String perm:permissions){
            if (ContextCompat.checkSelfPermission(this,perm) != PackageManager.PERMISSION_GRANTED){
                P.add(perm);
            }
        }
        ActivityCompat.requestPermissions(this, P.toArray(new String[P.size()]),MY_PERMISSIONS_REQUEST);
    }

}

//http://mobiledevhub.com/2017/11/15/android-fundamentals-requesting-multiple-runtime-permissions/
//https://www.youtube.com/watch?v=iqFRdjYqGPo&list=LLrOVcgmrA8h2q-cSPuaVgTA
