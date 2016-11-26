package com.example.oscar.tddd13_projekt;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Picture;
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
    private boolean validInput;

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

    /**
     * Create an InputFeedback of a certain type.
     * @param context
     * @param label The text to be displayed to the left of the editText which the user enters input.
     * @param type Integer that specifies the settings of the InputFeedback object. Found by calling
     *             "InputFeedback.TYPE_EMAIL"
     */
    public InputFeedback(Context context, String label, int type){
        super(context);
        setup(label, type);
    }

    /**
     * Create a custom InputFeedback with a custom, label, errorMessage and regex.
     * Uses the default validation function.
     * @param context
     * @param label The text to be displayed to the left of the editText which the user enters input.
     * @param errorMessage The text to be displayed under the editText when input is invalid.
     * @param regex Used to validate the user input using the default validation function.
     */
    public InputFeedback(Context context, String label, String errorMessage, String regex){
        super(context);
        this.errorMessage = errorMessage;
        this.regex = regex;
        setup(label, TYPE_CUSTOM);
    }

    /**
     * Create a custom InputFeedback with a custom, label, errorMessage and validation function.
     * @param context
     * @param label The text to be displayed to the left of the editText which the user enters input.
     * @param errorMessage The text to be displayed under the editText when input is invalid.
     * @param inputValidator Abstract class to with validation function
     */
    public InputFeedback(Context context, String label, String errorMessage, InputValidator inputValidator){
        super(context);
        this.errorMessage = errorMessage;
        this.inputValidator = inputValidator;
        setup(label, TYPE_CUSTOM);
    }

    /**
     * Create an InputFeedback with the default setting. Validation, regex and  errormessage is set
     * to default values.
     * @param context
     * @param label The text to be displayed to the left of the editText which the user enters input.
     */
    public InputFeedback(Context context, String label){
        super(context);
        setup(label, TYPE_DEFAULT);
    }

    /**
     * Private  helper function to initialize the object.
     */
    private void setup(String label, int type){
        errorView = super.getErrorView(); //Get the components from the superclass.
        textField = super.getInputField();
        labelView = super.getLabelView();
        statusImage = super.getStatusImageView();
        labelView.setText(label);
        setupType(textField, type); //Adds type specific settings
        addListeners();
        if(inputValidator == null) { //check if a custom InputValidator has been specified in the constuctor.
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

    /**
     * Adds the afterTextChangedListerner to the textField. This then calls the validation function.
     */
    private void addListeners(){
        textField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if(!inputValidator.isValid(s, regex)){ //call vaidation method in the inputValidator.
                    errorView.setText(errorMessage);
                    statusImage.setBackgroundResource(imgResInvalidInput); //Invalid Input
                    validInput = false;
                }
                else{
                    errorView.setText("");
                    statusImage.setBackgroundResource(imgResValidInput); //Valid Input
                    validInput = true;
                }
                if(strengthMeter != null){
                    strengthMeter.update(s); //Update the strengthMeter if one has been added.
                }
            }
        });
    }

    /**
     * Sets specific settings to the InputFeedback depending on the type which is specified in the
     * constructor
     */
    private void setupType(EditText e, int type){
        if(type == TYPE_EMAIL){
            errorMessage = ERROR_MESSAGE_EMAIL;
            regex = REGULAR_EXPRESSION_EMAIL;
            e.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS); //keyboard setting
        }
        else if(type == TYPE_PASSWORD){
            errorMessage = ERROR_MESSAGE_PASSWORD;
            regex = REGULAR_EXPRESSION_PASSWORD;
            e.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD); //keyboard setting
            e.setTransformationMethod(PasswordTransformationMethod.getInstance()); //Hide password
        }
        else if(type == TYPE_NUMBER){
            errorMessage = ERROR_MESSAGE_NUMBERS;
            regex = REGULAR_EXPRESSION_NUMBER;
            e.setInputType(InputType.TYPE_CLASS_NUMBER); //keyboard setting
        }
        else if(type == TYPE_DEFAULT){
            errorMessage = ERROR_MESSAGE_DEAFULT;
            regex = REGULAR_EXPRESSION_DEAFULT;
        }
    }

    /**
     * Add a strengthMeter to the InputFeedback object
     * @param strengthMeter
     * @see StrengthMeter
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
     * @see InputValidator
     */
    public void setValidation(InputValidator inputValidator){
        this.inputValidator = inputValidator;
    }

    /**
     * Funtion to give the InputFeedback a new regex that can be used to validate input.
     * @param regex RegularExpression
     */
    public void setRegularExpression(String regex){
        this.regex = regex;
    }

    /**
     * Function to set the error text that is displayed when input is invalid.
     * @param errorMessage text to be displayed.
     */
    public void setErrorMessage(String errorMessage){
        this.errorMessage = errorMessage;
    }

    /**
     * @return Returns the text that is displayed in the LabelView
     */
    public String getLabel(){
        return label;
    }

    /**
     * @param label Sets the text of the Label
     */
    public void setLabel(String label){
        this.label = label;
    }

    /**
     * @return Returns the text that is displayed in the errorView when the input is invalid.
     */
    public String getErrorMessage(){
        return errorMessage;
    }

    /**
     * @return Returns the current regex
     */
    public String getRegualarExpression(){
        return regex;
    }

    /**
     *  Used to change the image displayed when the input is valid.
     * @param imageResource
     */
    public void setImageResourceValidInput(int imageResource){
        imgResValidInput = imageResource;
    }
    /**
     * Used to change the image displayed when the input is invalid.
     * @param imageResource
     */
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
        if(validInput){
            return textField.getText();
        }
        return null;
    }

    /**
     * Check if the text enterd into the inputFeedBack is valid.
     * @return Return true if input is valid.
     */
    public boolean hasValidInput(){
        return hasValidInput();
    }

}
