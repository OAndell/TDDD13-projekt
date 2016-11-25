package com.example.oscar.tddd13_projekt;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is meant to give the user feedback when entering text into a textfield.
 * An InputFeedback object contains a textView label, an editText which the user can enter text,
 * an imageView that display a status image and a textView which displays error messages.
 * The users input is validated by the abstract InputValidator class, which can be customized to
 * suit the developers need.
 * InputFeedback inherits from InputFeedbackLayout which handles the layout of the object.
 * There is also support for adding a StrengthMeter to the layout.
 * @author Oscar Andell
 * @see InputValidator
 * @see InputFeedbackLayout
 * @see StrengthMeter
 */
public class InputFeedback extends InputFeedbackLayout{

    private String regex;
    private String errorMessage;
    private String label;
    private EditText textField;
    private TextView errorView;
    private TextView labelView;
    private ImageView statusImage;
    private InputValidator inputValidator;
    private StrengthMeter strengthMeter;

    private int imgResInvalidInput = R.drawable.cross;
    private int imgResValidInput = R.drawable.check;

    private static String REGULAR_EXPRESSION_EMAIL = ".*@.*\\..{2,6}";
    private static String REGULAR_EXPRESSION_PASSWORD = "^(?=.*\\d)(?=.*[a-zA-Z]).{4,8}$";
    private static String REGULAR_EXPRESSION_NUMBER = "\\d*";
    private static String REGULAR_EXPRESSION_DEAFULT =".*";

    private static String ERROR_MESSAGE_DEAFULT= "Input not valid";
    private static String ERROR_MESSAGE_EMAIL = "Not a valid Email adress";
    private static String ERROR_MESSAGE_PASSWORD = "Password: 4-8 charactres and contain both letters and numbers";
    private static String ERROR_MESSAGE_NUMBERS = "Input must contain only digits";

    public static int TYPE_EMAIL = 0;
    public static int TYPE_PASSWORD = 1;
    public static int TYPE_NUMBER = 2;
    public static int TYPE_DEFAULT = 3;
    private static int TYPE_CUSTOM = 4;


    public InputFeedback(Context context, String label, int type){
        super(context);
        setup(label, type);
    }

    public InputFeedback(Context context, String label, String errorMessage, String regex){
        super(context);
        this.errorMessage = errorMessage;
        this.regex = regex;
        setup(label, TYPE_CUSTOM);
    }

    public InputFeedback(Context context, String label, String errorMessage, InputValidator inputValidator){
        super(context);
        this.errorMessage = errorMessage;
        this.inputValidator = inputValidator;
        setup(label, TYPE_CUSTOM);
    }


    public InputFeedback(Context context, String label){
        super(context);
        setup(label, TYPE_DEFAULT);
    }

    private void setup(String label, int type){
        errorView = super.getErrorView();
        textField = super.getInputField();
        labelView = super.getLabelView();
        statusImage = super.getStatusImageView();
        labelView.setText(label);
        setupType(textField, type);
        addListeners();
        if(inputValidator == null) {
            inputValidator = new InputValidator() { //adds default isValid algorithm.
                @Override
                boolean isValid(Editable s, String regex) {
                    Pattern p = Pattern.compile(regex);
                    Matcher m = p.matcher(s.toString());
                    return m.matches();
                }
            };
        }
    }

    private void addListeners(){
        textField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if(!inputValidator.isValid(s, regex)){
                    errorView.setText(errorMessage);
                    statusImage.setBackgroundResource(imgResInvalidInput);
                }
                else{
                    errorView.setText("");
                    statusImage.setBackgroundResource(imgResValidInput);
                }
                if(strengthMeter != null){
                    strengthMeter.update(s); //Update the strengthMeter if one has been added.
                }
            }
        });
    }

    private void setupType(EditText e, int type){
        if(type == TYPE_EMAIL){
            errorMessage = ERROR_MESSAGE_EMAIL;
            regex = REGULAR_EXPRESSION_EMAIL;
            e.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        }
        else if(type == TYPE_PASSWORD){
            errorMessage = ERROR_MESSAGE_PASSWORD;
            regex = REGULAR_EXPRESSION_PASSWORD;
            e.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            e.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        else if(type == TYPE_NUMBER){
            errorMessage = ERROR_MESSAGE_NUMBERS;
            regex = REGULAR_EXPRESSION_NUMBER;
            e.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        else if(type == TYPE_DEFAULT){
            errorMessage = ERROR_MESSAGE_DEAFULT;
            regex = REGULAR_EXPRESSION_DEAFULT;
        }
    }

    /**
     * Add a strengthMeter to the InputFeedback object
     * @param strengthMeter
     */
    public void addStrengthMeter(StrengthMeter strengthMeter){
        if(this.strengthMeter == null){ //Add a new strengthMeter to the layout
            this.strengthMeter = strengthMeter;
            this.addView(strengthMeter);
        }
        else{
            this.strengthMeter = strengthMeter; //Overwrite strengthMeter with new strengthMeter
        }

    }

    /**
     * Sets the algoritm that determine if the input is valid.
     * @param inputValidator
     */
    public void setValidation(InputValidator inputValidator){
        this.inputValidator = inputValidator;
    }

    public void setRegularExpression(String regex){
        this.regex = regex;
    }

    public void setErrorMessage(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String getLabel(){
        return label;
    }

    public void setLabel(String label){
        this.label = label;
    }

    public String getErrorMessage(){
        return errorMessage;
    }

    public String getRegualarExpression(){
        return regex;
    }

    public void setImageResourceValidInput(int imageResource){
        imgResValidInput = imageResource;
    }

    public void setImageResourceInvalidInput(int imageResource){
        imgResInvalidInput = imageResource;
    }

    /**
     * Returns the text in the inputField. Does not check if the input is valid or not.
     * @return Returns the text entered into the textfield.
     */
    public Editable returnInput(){
        return textField.getText();



    }

    /**
     * Function that returns the text in the textField. Only returns valid input or null
     * @return Function that return the input in the textField if it is valid. If the inout is invalid: return null.
     */
    public Editable returnValidInput(){
        if(inputValidator.isValid(textField.getText(), regex)){
            return textField.getText();
        }
        return null;
    }

}
