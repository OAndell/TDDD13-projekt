package com.example.oscar.tddd13_projekt;

import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        //Email example, uses the default setting for Email
        InputFeedback inputEmail = new InputFeedback(this, "Email", InputFeedback.TYPE_EMAIL);

        //Age Example. Uses the TYPE_NUMBER setting but regex and errormessage is changed.
        InputFeedback inputNumber = new InputFeedback(this,"Age", InputFeedback.TYPE_NUMBER);
        inputNumber.setRegularExpression("\\d{1,2}");
        inputNumber.setErrorMessage("Enter 1-2 digits");

        //Example where the InputFeedback uses a custom inputValidation function
        InputFeedback inputQuestion= new InputFeedback(this, "What is 5+5?", "Wrong answer", new InputValidator() {
            @Override
            boolean isValid(Editable s, String regex) {
                return s.toString().equals("10");
            }
        });

        //Password Example, Customized by setting a custom regex and errormessage.
        //Also uses a StrengthMeter which is added to the InputFeedback object.
        InputFeedback inputPassword = new InputFeedback(this,"Password", InputFeedback.TYPE_PASSWORD);
        inputPassword.setRegularExpression(".{5,}");
        inputPassword.setErrorMessage("");
        inputPassword.addStrengthMeter(new StrengthMeter(this));

        layout.addView(inputEmail);
        layout.addView(inputNumber);
        layout.addView(inputQuestion);
        layout.addView(inputPassword);
        setContentView(layout);
    }
}
