package com.anwesome.ui.lockableimageview;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by anweshmishra on 02/06/17.
 */

public class Constants {
    public static final LruCache<Bitmap,Bitmap> stringBitmapMap = new LruCache<>(5);
}
