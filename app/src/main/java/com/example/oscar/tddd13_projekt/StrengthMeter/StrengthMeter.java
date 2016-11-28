package com.example.oscar.tddd13_projekt.StrengthMeter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Editable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * The StrengthMeter class is a UI-component that gives the user feedback depending on which input
 * is given. The feedback is displayed like a colored bar that has four states: weak, medium, good
 * and very good, where every state has a color and a length. Text is also displayed on top of the
 * bar for extra clarity.
 * This component can be used for example in an application where the user sets up a new password.
 * The StrengthMeter can visually display feedback on the strength of the entered password.
 * The algorithm in which the strength is calculated can be customized with the abstract class
 * StrengthMeterAlgorithm.
 * StrengthMeter inherits from View
 * @author Oscar Andell
 * @see StrengthMeterAlgorithm
 */
public class StrengthMeter extends View{

    private Paint paint = new Paint();

    private StrengthMeterAlgorithm algorithm;

    private int StrengthMeterHeight = 75;
    private int textOffsetY = 35;
    private int currentLength;
    private int textSize = 45;

    private int colorWeak = Color.RED;
    private int colorMedium = Color.YELLOW;
    private int colorGood = Color.GREEN;
    private int currentColor;

    private String state0 = "";
    private String state1 = "Weak";
    private String state2 = "Medium";
    private String state3 = "Strong";
    private String state4 = "Very Strong";

    private String currentString = "";

    /**
     * Create a new StrengthMeter component with a custom StrengthMeterAlgorithm to calculate input
     * strength.
     * @param context
     * @param strengthMeterAlgoritm Specifies the algorithm that calculate input strength.
     */
    public StrengthMeter(Context context, StrengthMeterAlgorithm strengthMeterAlgoritm){
        super(context);
        this.algorithm = strengthMeterAlgoritm;
        setUp();
    }

    /**
     * Create a new StrengthMeter component. Uses the default strength algorithm to calculate input
     * strength.
     * @param context
     */
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
                if(s.toString().matches(".*\\d+.*")&& s.toString().length() > 4){ //contain a number
                    score++;
                }
                if(s.toString().matches(".*[A-Za-z].*")&& s.toString().length() > 4){ //contain a letter
                    score++;
                }
                if(s.toString().length() > 8){
                    score++;
                }
                return score;
            }
        };
    }

    /**
     * Function that calls the StrengthMeterAlgorithm object that is assigned to this StrengthMeter.
     * Updates the length, text and color of the StrengthMeter depending on the score the StrengthMeterAlgorithm returns
     * @param s Editable that you want to rate.
     * @see StrengthMeterAlgorithm
     */
    public void update(Editable s){
        int score = algorithm.calcStrength(s);
        if(score == 0){
            currentLength = 0;
            currentString = state0;
        }
        else if(score == 1){
            currentLength = this.getWidth()/3;
            currentColor = colorWeak;
            currentString = state1;
        }
        else if(score == 2){
            currentLength = this.getWidth()/2;
            currentColor = colorMedium;
            currentString = state2;
        }
        else if(score == 3){
            currentLength = (this.getWidth()*3)/4;
            currentColor = colorGood;
            currentString = state3;
        }
        else if(score >= 4){
            currentLength = this.getWidth();
            currentColor = colorGood;
            currentString = state4;
        }
        this.invalidate(); //Updates view on screen
    }

    /**
     * Private helper function to initialize the component.
     */
    private void setUp(){
        this.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, StrengthMeterHeight));
        paint.setStrokeWidth(StrengthMeterHeight);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(textSize);
        paint.setStyle(Paint.Style.FILL);
        currentLength = 0;

    }

    /**
     * Sets the algoritm with which calculate the strength of the StrengthMeter
     * @param algorithm
     * @see StrengthMeterAlgorithm
     */
    private void setAlgorithm(StrengthMeterAlgorithm algorithm){
        this.algorithm = algorithm;
    }

    /**
     * Draw the component onto the screen.
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(currentColor);
        canvas.drawLine(0,0, currentLength,0,paint);
        paint.setColor(Color.BLACK);
        canvas.drawText(currentString, this.getWidth()/2, textOffsetY, paint);
    }

    /**
     * Sets the text on top of the StrengthMeter for all five states.
     * @param state0
     * @param state1
     * @param state2
     * @param state3
     * @param state4
     */
    public void setText(String state0, String state1, String state2, String state3, String state4){
        this.state0 = state0;
        this.state1 = state1;
        this.state2 = state2;
        this.state3 = state3;
        this.state4 = state4;
    }


}
