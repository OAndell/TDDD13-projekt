package com.example.oscar.tddd13_projekt;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Editable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by Oscar on 2016-11-23.
 */

public class StrengthMeter extends View{

    private Paint paint = new Paint();

    private StrengthMeterAlgorithm algorithm;

    private int StrengthMeterHeight = 75;
    private int textOffsetY = 35;
    private int currentLength;

    private int colorWeak = Color.RED;
    private int colorMedium = Color.YELLOW;
    private int colorGood = Color.GREEN;
    private int currentColor;

    private String currentString = "";

    public StrengthMeter(Context context, StrengthMeterAlgorithm strengthMeterAlgoritm){
        super(context);
        this.algorithm = strengthMeterAlgoritm;
        setUp();
    }

    public StrengthMeter(Context context) {
        super(context);
        setUp();
        algorithm = new StrengthMeterAlgorithm() {
            @Override
            public int calcStrength(Editable s) {
                int score = 0;
                if(s.toString().length() > 0){
                    score++;
                }
                if(s.toString().length() > 4){
                    score++;
                }
                if(s.toString().length() > 8){
                    score++;
                }
                if(s.toString().length() > 10){
                    score++;
                }
                return score;
            }
        };
    }

    /**
     * function that calls the StrengthMeterAlgorithm object that is assigned to this StrengthMeter.
     * Updates the length, text and color of the StrengthMeter depending on the score the StrengthMeterAlgorithm returns
     * @param s Editable that you want to rate.
     * @see StrengthMeterAlgorithm
     */
    public void update(Editable s){
        int score = algorithm.calcStrength(s);
        if(score == 0){
            currentLength = 0;
            currentString = "";
        }
        else if(score == 1){
            currentLength = this.getWidth()/3;
            currentColor = colorWeak;
            currentString = "Weak";
        }
        else if(score == 2){
            currentLength = this.getWidth()/2;
            currentColor = colorMedium;
            currentString = "Medium";
        }
        else if(score == 3){
            currentLength = (this.getWidth()*3)/4;
            currentColor = colorGood;
            currentString = "Strong";
        }
        else if(score >= 4){
            currentLength = this.getWidth();
            currentColor = colorGood;
            currentString = "Very Strong";
        }
        this.invalidate(); //Updates view on screen
    }


    private void setUp(){
        this.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, StrengthMeterHeight));
        paint.setStrokeWidth(StrengthMeterHeight);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(45);
        paint.setStyle(Paint.Style.FILL);
        currentLength = 0;
    }

    /**
     * Sets the algoritm with which calculate the strength of the StrengthMeter
     * @param algorithm
     */
    private void setAlgorithm(StrengthMeterAlgorithm algorithm){
        this.algorithm = algorithm;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(currentColor);
        canvas.drawLine(0,0, currentLength,0,paint);
        paint.setColor(Color.BLACK);
        canvas.drawText(currentString, this.getWidth()/2, textOffsetY, paint);
    }
}
