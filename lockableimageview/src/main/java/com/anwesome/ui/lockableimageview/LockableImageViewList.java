package com.anwesome.ui.lockableimageview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

/**
 * Created by anweshmishra on 02/06/17.
 */

public class LockableImageViewList {
    private Activity activity;
    private boolean isShown = false;
    private ScrollView scrollView;
    private ListLayout listLayout;
    public LockableImageViewList(Activity activity) {
        this.activity = activity;
        scrollView = new ScrollView(activity);
        listLayout = new ListLayout(activity);
        scrollView.addView(listLayout,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }
    public void show() {
        if(!isShown) {
            activity.setContentView(scrollView);
            isShown = true;
        }
    }
    public void addImage(Bitmap bitmap) {
        if(!isShown) {
            listLayout.addImage(bitmap);
        }
    }
    private class ListLayout extends ViewGroup{
        private int w,h;
        public ListLayout(Context context) {
            super(context);
            initDimension(context);
        }
        public void initDimension(Context context) {
            DisplayManager displayManager = (DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE);
            Display display = displayManager.getDisplay(0);
            if(display != null) {
                Point size = new Point();
                display.getRealSize(size);
                w = size.x;
                h = size.y;
            }
        }
        public void addImage(Bitmap bitmap) {
            LockbaleImageView lockbaleImageView = new LockbaleImageView(getContext(),bitmap);
            addView(lockbaleImageView,new LayoutParams(w,w));
            requestLayout();
        }
        public void onMeasure(int wspec,int hspec) {
            int hmax = h/30;
            for(int i=0;i<getChildCount();i++) {
                View child = getChildAt(i);
                measureChild(child,wspec,hspec);
                hmax += (child.getMeasuredHeight()+h/30);
            }
            setMeasuredDimension(w,hmax);
        }
        public void onLayout(boolean reloaded,int a,int b,int wa,int ha) {
            int x = 0,y = h/30;
            for(int i=0;i<getChildCount();i++) {
                View child = getChildAt(i);
                child.layout(x,y,x+child.getMeasuredWidth(),y+getMeasuredHeight());
                y += (child.getMeasuredHeight()+h/30);
            }
        }
    }
}
