package com.example.oscar.tddd13_projekt;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Oscar on 2016-11-23.
 */

public class InputFeedbackLayout extends LinearLayout {
    private EditText textField;
    private TextView errorView;
    private TextView labelView;
    private ImageView statusImage;

    private int textFieldWidth = 600;
    private int errorViewTextSize = 11;

    public InputFeedbackLayout(Context context) {
        super(context);
        createLayout(context);
    }

    private void createLayout(Context context) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        this.setOrientation(LinearLayout.VERTICAL);
        this.setLayoutParams(params);
        LinearLayout verticalRow = new LinearLayout(context);
        verticalRow.setOrientation(LinearLayout.HORIZONTAL);
        labelView = new TextView(context);
        labelView.setGravity(Gravity.RIGHT);
        textField = new EditText(context);
        textField.setId(View.generateViewId());//Save while rotating screen;
        textField.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textField.getLayoutParams().width = textFieldWidth;
        errorView = new TextView(context);
        errorView.setTextSize(errorViewTextSize);
        errorView.setGravity(Gravity.CENTER);
        statusImage = new ImageView(context);
        LinearLayout.LayoutParams imageVeiwParams = new LinearLayout.LayoutParams(50,50);
        imageVeiwParams.gravity = Gravity.CENTER;
        statusImage.setLayoutParams(imageVeiwParams);
        verticalRow.addView(labelView);
        verticalRow.addView(textField);
        verticalRow.addView(statusImage);
        this.addView(verticalRow);
        this.addView(errorView);
    }


    public void setTextFieldWidth(int textFieldWidth){
        textField.getLayoutParams().width = textFieldWidth;
    }

    public void setErrorViewTextSize(int size){
        errorView.setTextSize(size);
    }

    public ImageView getStatusImageView(){
        return statusImage;
    }

    public EditText getInputField(){
        return textField;
    }

    public TextView getErrorView(){
        return errorView;
    }

    public TextView getLabelView(){
        return labelView;
    }
}
