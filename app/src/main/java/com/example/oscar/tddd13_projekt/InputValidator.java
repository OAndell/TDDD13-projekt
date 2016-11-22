package com.example.oscar.tddd13_projekt;

import android.text.Editable;

/**
 ** Created by Oscar on 2016-11-21.*/
public abstract class InputValidator {
    abstract boolean isValid(Editable s, String regex);
}
