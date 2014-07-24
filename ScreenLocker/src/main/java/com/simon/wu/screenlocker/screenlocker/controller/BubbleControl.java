package com.simon.wu.screenlocker.screenlocker.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;

import com.simon.wu.screenlocker.screenlocker.R;
import com.simon.wu.screenlocker.screenlocker.entity.Bubble;
import com.simon.wu.screenlocker.screenlocker.utils.Utils;

import java.util.Random;

/**
 * Created by Simon.Wu on 2014/7/24.
 */
public class BubbleControl {
    private Bitmap[] mBitmap;
    private Bubble[] mBubbles;
    private int mBubblesNum;
    private Context mContext;
    private int mHeight;
    private boolean mIsLive;
    private Random mRandom;
    private int mWidth;

    public BubbleControl(Context paramContext) {
        this.mContext = paramContext;
        this.mBubblesNum = 15;
        this.mWidth = Utils.getGoalWidth(100, paramContext);
        this.mHeight = Utils.getGoalHeight(100, paramContext);
        this.mRandom = new Random();
        getPictures();
        initBubbles();
    }

    private void getPictures() {
        this.mBitmap = new Bitmap[5];
        if (this.mContext == null) return;
        BitmapDrawable localBitmapDrawable1 = (BitmapDrawable) this.mContext.getResources().getDrawable(R.drawable.snow_s);
        this.mBitmap[0] = localBitmapDrawable1.getBitmap();
        BitmapDrawable localBitmapDrawable2 = (BitmapDrawable) this.mContext.getResources().getDrawable(R.drawable.snow_m);
        this.mBitmap[1] = localBitmapDrawable2.getBitmap();
        BitmapDrawable localBitmapDrawable3 = (BitmapDrawable) this.mContext.getResources().getDrawable(R.drawable.snow_b);
        this.mBitmap[2] = localBitmapDrawable3.getBitmap();
        BitmapDrawable localBitmapDrawable4 = (BitmapDrawable) this.mContext.getResources().getDrawable(R.drawable.snow_a);
        this.mBitmap[3] = localBitmapDrawable4.getBitmap();
        BitmapDrawable localBitmapDrawable5 = (BitmapDrawable) this.mContext.getResources().getDrawable(R.drawable.snow_c);
        this.mBitmap[4] = localBitmapDrawable5.getBitmap();
    }


    public void drawBubbles(Canvas paramCanvas, Paint paramPaint) {
        if (!this.mIsLive) ;
        moveBubbles();
        for (int i = 0; i < this.mBubblesNum; i++)
            if (this.mBubbles[i].isLive)
                paramCanvas.drawBitmap(this.mBitmap[this.mBubbles[i].getBitmapIndex()], this.mBubbles[i].getX(), this.mHeight - this.mBubbles[i].getY(), paramPaint);
    }

    public void initBubbles() {
        this.mBubbles = new Bubble[this.mBubblesNum];
        for (int i = 0; i < this.mBubblesNum; i++) {
            this.mBubbles[i] = new Bubble();
            int j = 1 + this.mRandom.nextInt(this.mBitmap.length);
            int k = 1 + this.mRandom.nextInt(j);
            int m = this.mRandom.nextInt(k);
            this.mBubbles[i].setBitmapIndex(m);
            this.mBubbles[i].setX_Speed((1 + this.mRandom.nextInt(m + 2)) / 2 * (0xFFFFFFFF ^ this.mRandom.nextInt(1)));
            this.mBubbles[i].setY_Speed(this.mRandom.nextInt(m + 2) + 2 * (m + 1));
            this.mBubbles[i].setX(this.mRandom.nextInt(this.mWidth));
            this.mBubbles[i].setY(this.mRandom.nextInt(4 * this.mHeight / (3 + (3 - this.mBubbles[i].getBitmapIndex()))));
            this.mBubbles[i].isLive = this.mRandom.nextBoolean();
        }
    }

    public void moveBubbles() {
        for (int i = 0; i < this.mBubblesNum; i++) {
            if (!this.mBubbles[i].isLive) {
                int j = 1 + this.mRandom.nextInt(this.mBitmap.length);
                int k = 1 + this.mRandom.nextInt(j);
                int m = this.mRandom.nextInt(k);
                this.mBubbles[i].setBitmapIndex(m);
                this.mBubbles[i].setX_Speed((1 + this.mRandom.nextInt(m + 2)) / 2 * (0xFFFFFFFF ^ this.mRandom.nextInt(1)));
                this.mBubbles[i].setY_Speed(this.mRandom.nextInt(m + 2) + 2 * (m + 1));
                this.mBubbles[i].setX(this.mRandom.nextInt(this.mWidth));
                this.mBubbles[i].setY(-this.mBubbles[i].getRadius());
                this.mBubbles[i].isLive = true;
            } else {
                if ((this.mBubbles[i].getX() >= -this.mBubbles[i].getRadius()) && (this.mBubbles[i].getX() <= this.mWidth + this.mBubbles[i].getRadius()) && (this.mBubbles[i].getY() <= 4 * this.mHeight / (3 + (3 - this.mBubbles[i].getBitmapIndex())) + this.mBubbles[i].getRadius())) {
                    this.mBubbles[i].setX(this.mBubbles[i].getX() + this.mBubbles[i].getX_Speed());
                    this.mBubbles[i].setY(this.mBubbles[i].getY() + this.mBubbles[i].getY_Speed());
                } else {
                    this.mBubbles[i].isLive = false;
                }
            }
        }
    }

    public void setHeight(int paramInt) {
        this.mHeight = paramInt;
    }

    public void setWidth(int paramInt) {
        this.mWidth = paramInt;
    }
}
