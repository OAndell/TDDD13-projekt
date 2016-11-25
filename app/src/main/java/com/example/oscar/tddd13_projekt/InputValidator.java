package com.example.oscar.tddd13_projekt;

import android.text.Editable;

/**
 ** Created by Oscar on 2016-11-21.*/
public abstract class InputValidator {
    /**
     * Abstract function to determine if the input of an editText is valid.
     * @param s Editable from EditText
     * @param regex uses the regex saved in InputFeedback.
     * @return Return true if input is valid
     */
    abstract boolean isValid(Editable s, String regex);
}
