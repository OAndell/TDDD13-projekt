package com.example.oscar.tddd13_projekt.InputFeedback;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * This class creates the layout of an InputFeedback component.
 * Inherits from LinearLayout
 * @author Oscar Andell
 * @see InputFeedback
 */
public class InputFeedbackLayout extends LinearLayout {
    private EditText textField;
    private TextView errorView;
    private TextView labelView;
    private ImageView statusImage;

    private int imageViewSize = 50;
    private int textFieldWidth = 600;
    private int errorViewTextSize = 11;

    /**
     * Create an InputFeedbackLayout. Used as a base for the InputFeedback component
     * @param context
     */
    public InputFeedbackLayout(Context context) {
        super(context);
        createLayout(context);
    }

    /**
     * Private helper function to create the layout of the InputFeedback component.
     */
    private void createLayout(Context context) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        this.setOrientation(LinearLayout.VERTICAL);
        this.setLayoutParams(params);
        LinearLayout verticalRow = new LinearLayout(context);
        verticalRow.setOrientation(LinearLayout.HORIZONTAL);
        labelView = new TextView(context);
        textField = new EditText(context);
        textField.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textField.getLayoutParams().width = textFieldWidth;
        errorView = new TextView(context);
        errorView.setTextSize(errorViewTextSize);
        errorView.setGravity(Gravity.CENTER);
        statusImage = new ImageView(context);
        LinearLayout.LayoutParams imageViewParams = new LinearLayout.LayoutParams(imageViewSize,imageViewSize);
        imageViewParams.gravity = Gravity.CENTER;
        statusImage.setLayoutParams(imageViewParams);
        verticalRow.addView(labelView);
        verticalRow.addView(textField);
        verticalRow.addView(statusImage);
        this.addView(verticalRow);
        this.addView(errorView);
    }

    /**
     * Sets the width of the editText textField.
     */
    public void setTextFieldWidth(int textFieldWidth){
        textField.getLayoutParams().width = textFieldWidth;
    }

    /**
     * Sets the textSize of the errorMessage text.
     * @param size sets the errorView Textsize to specified size
     */
    public void setErrorViewTextSize(int size){
        errorView.setTextSize(size);
    }

    /**
     * Function to get the statusImage ImageView to enable customization.
     * @return Return the ImageView that dispalays the status images of the InputFeedback object.
     */
    public ImageView getStatusImageView(){
        return statusImage;
    }

    /**
     * Function to get the textField editText to to enable further customization.
     * @return Return the textField editText in which input is entered.
     */
    public EditText getInputField(){
        return textField;
    }

    /**
     * Function to get the errorView textView to to enable further customization.
     * @return Return the TextView that displays the errorMessage in the InputFeedback.
     */
    public TextView getErrorView(){
        return errorView;
    }

    /**
     * Function to get the label textView to to enable further customization.
     * @return Return the TextView that displays the label in the InputFeedback.
     */
    public TextView getLabelView(){
        return labelView;
    }
}
