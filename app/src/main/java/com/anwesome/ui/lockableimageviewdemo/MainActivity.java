package com.anwesome.ui.lockableimageviewdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.anwesome.ui.lockableimageview.LockbaleImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.stp);
        LockbaleImageView lockbaleImageView = new LockbaleImageView(this,bitmap);
        addContentView(lockbaleImageView,new ViewGroup.LayoutParams(1080,1080));
    }
}
