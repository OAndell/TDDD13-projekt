package com.example.oscar.tddd13_projekt;

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

        InputFeedBack inputEmail = new InputFeedBack(this, "Email", InputFeedBack.TYPE_EMAIL);

        InputFeedBack inputPassword = new InputFeedBack(this,"Password", InputFeedBack.TYPE_PASSWORD);

        InputFeedBack inputNumber = new InputFeedBack(this,"Age",InputFeedBack.TYPE_NUMBER);
        inputNumber.setRegualarExpression("\\d{1,2}");
        inputNumber.setErrorMessage("Enter 1-2 digits");


        layout.addView(inputEmail);
        layout.addView(inputPassword);
        layout.addView(inputNumber);
        setContentView(layout);
    }
}
