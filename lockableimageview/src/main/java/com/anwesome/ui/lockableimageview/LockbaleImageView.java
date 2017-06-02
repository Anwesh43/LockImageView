package com.anwesome.ui.lockableimageview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by anweshmishra on 02/06/17.
 */

public class LockbaleImageView extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int w,h,time = 0;
    private Bitmap bitmap;
    private ImageLock imageLock;
    private AnimationHandler animationHandler;
    public LockbaleImageView(Context context, Bitmap bitmap) {
        super(context);
        this.bitmap = bitmap;
    }
    public void onDraw(Canvas canvas) {
        if(time == 0) {
            w = canvas.getWidth();
            h = canvas.getHeight();
            bitmap = Bitmap.createScaledBitmap(bitmap,w,2*h/3,true);
            imageLock = new ImageLock();
            animationHandler = new AnimationHandler();
        }
        paint.setStyle(Paint.Style.FILL);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap,0,0,paint);
        paint.setColor(Color.BLACK);
        canvas.drawRect(new RectF(0,2*h/3,w,h),paint);
        imageLock.draw(canvas);
        time++;
    }
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN && imageLock!=null && imageLock.handleTap(event.getX(),event.getY())) {
            animationHandler.start();
        }
        return true;
    }
    public void update(float factor) {
        if(imageLock != null) {
            imageLock.update(factor);
        }
        postInvalidate();
    }
    private class ImageLock {
        private float x,y,size,deg = 0;
        public ImageLock() {
            x = w/2;
            y = 5*h/6+h/15;
            size = w/10;
        }
        public void draw(Canvas canvas) {
            paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(w/60);
            float rectSize = 3*size/4;
            canvas.drawRoundRect(new RectF(x-rectSize,y-rectSize,x+rectSize,y+rectSize),w/30,w/30,paint);
            paint.setStrokeWidth(w/40);
            float r = size/2;
            canvas.save();
            canvas.translate(x-r,y-rectSize);
            canvas.rotate(deg);
            canvas.drawArc(new RectF(0,-1.25f*r,2*r,1.25f*r),180,180,false,paint);
            canvas.restore();
        }
        public void update(float factor) {
            deg = -60*factor;
        }
        public boolean handleTap(float x,float y) {
            return x>=this.x-size && x<=this.x+size && y>=this.y-5*size/3 && y<=this.y+size;
        }
    }
    private class AnimationHandler extends AnimatorListenerAdapter implements ValueAnimator.AnimatorUpdateListener{
        private ValueAnimator startAnim = ValueAnimator.ofFloat(0,1),endAnim = ValueAnimator.ofFloat(1,0);
        private int dir = 0;
        private boolean isAnimated = false;
        private AnimationHandler() {
            startAnim.setDuration(500);
            endAnim.setDuration(500);
            startAnim.addUpdateListener(this);
            endAnim.addUpdateListener(this);
            startAnim.addListener(this);
            endAnim.addListener(this);
        }
        public void start() {
            if(!isAnimated) {
                if(dir == 0) {
                    startAnim.start();
                }
                else {
                    endAnim.start();
                }
                isAnimated = true;
            }
        }
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            if(isAnimated) {
                update((float)valueAnimator.getAnimatedValue());
            }
        }
        public void onAnimationEnd(Animator animator) {
            if(isAnimated) {
                dir = dir == 0?1:0;
                isAnimated = false;
            }
        }
    }
}
