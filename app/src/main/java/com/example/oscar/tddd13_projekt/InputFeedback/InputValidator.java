package com.example.oscar.tddd13_projekt.InputFeedback;

import android.text.Editable;

/**
 *Abstract class that specifies the algorithm used to determine if the input is valid in InputFeeback
 * @author Oscar Andell
 * @see InputFeedback
 */
public abstract class InputValidator {
    /**
     * Abstract function to determine if the input of an editText is valid.
     * @param s Editable from EditText
     * @param regex uses the regex saved in InputFeedback.
     * @return Return true if input is valid
     */
    public abstract boolean isValid(Editable s, String regex);
}
