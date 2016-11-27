package com.example.oscar.tddd13_projekt.StrengthMeter;

import android.text.Editable;

/**
 *Abstract class that specifies the algorithm used to determine the strength of the StrengthMeter
 * component.
 * @author Oscar Andell
 * @see StrengthMeter
 */
public abstract class StrengthMeterAlgorithm {
    /**
     * Abstract algorithm to calculate strength of input.
     * @param s Editable from EditText
     * @return Should return an integer between 0-4
     */
    public abstract int calcStrength(Editable s);
}
