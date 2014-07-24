package com.simon.wu.screenlocker.screenlocker.entity;

/**
 * Created by Simon.Wu on 2014/7/24.
 */
public class Bubble {
    public static final int[] color = new int[10];
    public int _bitmap_index = 0;
    public int _color = 0;
    public int _r = 0;
    public int _x = -1;
    public int _y = -1;
    public boolean isLive = false;
    public int x_speed = 0;
    public int y_speed = 0;

    static {
        color[0] = -12303292;
        color[1] = -7829368;
        color[2] = -3355444;
        color[3] = -1;
        color[4] = -65536;
        color[5] = -16711936;
        color[6] = -16776961;
        color[7] = -256;
        color[8] = -16711681;
        color[9] = -65281;
    }

    public int getBitmapIndex() {
        return this._bitmap_index;
    }

    public int getColor() {
        return this._color;
    }

    public int getRadius() {
        return this._r;
    }

    public int getX() {
        return this._x;
    }

    public int getX_Speed() {
        return this.x_speed;
    }

    public int getY() {
        return this._y;
    }

    public int getY_Speed() {
        return this.y_speed;
    }

    public void setBitmapIndex(int paramInt) {
        this._bitmap_index = paramInt;
    }

    public void setColor(int paramInt) {
        this._color = paramInt;
    }

    public void setRadius(int paramInt) {
        if (paramInt < 1) ;
        for (this._r = 1; ; this._r = paramInt)
            return;
    }

    public void setX(int paramInt) {
        this._x = paramInt;
    }

    public void setX_Speed(int paramInt) {
        this.x_speed = paramInt;
    }

    public void setY(int paramInt) {
        this._y = paramInt;
    }

    public void setY_Speed(int paramInt) {
        this.y_speed = paramInt;
    }
}
