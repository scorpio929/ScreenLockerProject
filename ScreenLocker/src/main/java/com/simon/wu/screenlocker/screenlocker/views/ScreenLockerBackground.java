package com.simon.wu.screenlocker.screenlocker.views;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.simon.wu.screenlocker.screenlocker.R;
import com.simon.wu.screenlocker.screenlocker.controller.BubbleControl;
import com.simon.wu.screenlocker.screenlocker.tools.PowerScreenManager;

/**
 * TODO: document your custom view class.
 */
public class ScreenLockerBackground extends RelativeLayout {
    private Bitmap mBackground;
    private String mBackgroundName;
    private BubbleControl mBubbleControl;
    private Context mContext;
    private final Runnable mDrawRun = new Runnable() {
        public void run() {
            ScreenLockerBackground.this.postInvalidate();
        }
    };
    private final Handler mHandler = new Handler();
    private BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {
        public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent) {
            String str = paramAnonymousIntent.getAction();
            if (str.equals("android.intent.action.SCREEN_ON"))
                ScreenLockerBackground.this.invalidate();
            if (str.equals("android.intent.action.SCREEN_OFF"))
                ScreenLockerBackground.this.mHandler.removeCallbacks(ScreenLockerBackground.this.mDrawRun);
        }
    };
    private Paint mPaint;

    public ScreenLockerBackground(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        this.mContext = paramContext;
        setWillNotDraw(false);
        this.mPaint = new Paint();
        this.mPaint.setColor(-1);
        // getSettingsFromPref();
        this.mBackground = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
        this.mBubbleControl = new BubbleControl(this.mContext);
    }

    /*private void getSettingsFromPref() {
        if (this.mContext == null) return;
        this.mBackgroundName = this.mContext.getSharedPreferences("user_pref", 0).getString("screensaver_bg_index", "default.jpg");
        if (PhotoSelectActivity.mFlagStart) {
            this.mBackground = BitmapUtils.getBitmapFromAssets(this.mContext, "pictures/" + this.mBackgroundName);
            Log.i("setBackground", "setBackgroundgetInt" + this.mBackgroundName);
        } else {
            this.mBackground = BitmapUtils.GetBitmapFromFile(Environment.getExternalStorageDirectory() + "/screensaver/temp.png");
            if (this.mBackground == null)
                this.mBackground = BitmapUtils.getBitmapFromAssets(this.mContext, "pictures/" + this.mBackgroundName);
            Log.i("setBackground", "setBackgroundgetInt" + this.mBackgroundName);
        }

    }*/

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        IntentFilter localIntentFilter = new IntentFilter();
        localIntentFilter.addAction("android.intent.action.SCREEN_ON");
        localIntentFilter.addAction("android.intent.action.SCREEN_OFF");
        getContext().registerReceiver(this.mIntentReceiver, localIntentFilter, null, this.mHandler);
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getContext().unregisterReceiver(this.mIntentReceiver);
    }

    protected void onDraw(Canvas paramCanvas) {
        super.onDraw(paramCanvas);
        if (paramCanvas != null) {
            paramCanvas.drawBitmap(this.mBackground, new Rect(0, 0, this.mBackground.getWidth(), this.mBackground.getHeight()), new Rect(0, 0, getWidth(), getHeight()), this.mPaint);
            this.mBubbleControl.drawBubbles(paramCanvas, this.mPaint);
        }
        this.mHandler.removeCallbacks(this.mDrawRun);
        this.mHandler.postDelayed(this.mDrawRun, 40L);
        if (!PowerScreenManager.getInstance(getContext()).getScreenStatus())
            this.mHandler.removeCallbacks(this.mDrawRun);
    }

}
