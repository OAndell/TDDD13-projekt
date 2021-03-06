package com.example.oscar.tddd13_projekt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.widget.LinearLayout;

import com.example.oscar.tddd13_projekt.InputFeedback.InputFeedback;
import com.example.oscar.tddd13_projekt.InputFeedback.InputValidator;
import com.example.oscar.tddd13_projekt.StrengthMeter.StrengthMeter;

public class MainActivity extends AppCompatActivity {

    InputFeedback inputEmail;
    InputFeedback inputNumber;
    InputFeedback inputQuestion;
    InputFeedback inputPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        //Email example, uses the default setting for Email
        inputEmail = new InputFeedback(this, "Email", InputFeedback.TYPE_EMAIL);

        //Age Example. Uses the TYPE_NUMBER setting but regex and errormessage is changed.
        inputNumber = new InputFeedback(this,"Age", InputFeedback.TYPE_NUMBER);
        inputNumber.setRegularExpression("\\d{1,2}");
        inputNumber.setErrorMessage("Enter 1-2 digits");

        //Example where the InputFeedback uses a custom inputValidation function
         inputQuestion= new InputFeedback(this, "Capital of Norway?", "Wrong answer", new InputValidator() {
            @Override
            public boolean isValid(Editable s, String regex) {
                return (s.toString().toLowerCase().equals("oslo"));
            }
        });

        //Password Example, Customized by setting a custom regex and errormessage.
        //Also uses a StrengthMeter which is added to the InputFeedback object.
        inputPassword = new InputFeedback(this,"Password", InputFeedback.TYPE_PASSWORD);
        inputPassword.setRegularExpression(".{5,}");
        inputPassword.setErrorMessage("");
        inputPassword.addStrengthMeter(new StrengthMeter(this));

        layout.addView(inputEmail);
        layout.addView(inputNumber);
        layout.addView(inputQuestion);
        layout.addView(inputPassword);
        setContentView(layout);

        //Setup Testing id
        inputEmail.getInputField().setId(1000);
        inputNumber.getInputField().setId(1001);
        inputQuestion.getInputField().setId(1002);
        inputPassword.getInputField().setId(1003);

    }

}
