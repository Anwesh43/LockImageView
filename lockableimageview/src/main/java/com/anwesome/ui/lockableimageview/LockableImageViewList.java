package com.anwesome.ui.lockableimageview;

import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.ScrollView;

/**
 * Created by anweshmishra on 02/06/17.
 */

public class LockableImageViewList {
    private Activity activity;
    private boolean isShown = false;
    private ScrollView scrollView;
    public LockableImageViewList(Activity activity) {
        this.activity = activity;
        scrollView = new ScrollView(activity);
    }
    public void show() {
        if(!isShown) {
            activity.setContentView(scrollView);
            isShown = true;
        }
    }
    public void addImage(Bitmap bitmap) {
        if(!isShown) {

        }
    }
}
