package com.example.oscar.tddd13_projekt;

import android.text.Editable;

/**
 * Created by Oscar on 2016-11-25.
 */

public abstract class StrengthMeterAlgorithm {
    /**
     * Abstract algorithm to calculate strength of input.
     * @param s Editable from EditText
     * @return Should return an integer between 0-4
     */
    public abstract int calcStrength(Editable s);
}
