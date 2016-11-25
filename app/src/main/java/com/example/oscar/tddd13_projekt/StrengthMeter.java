package com.example.oscar.tddd13_projekt;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by Oscar on 2016-11-23.
 */

public class StrengthMeter extends View{
    public StrengthMeter(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawLine(0,0,200,200,new Paint());

    }
}
