package com.example.oscar.tddd13_projekt;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.media.MediaMetadataCompat;
import android.view.View;

import com.example.oscar.tddd13_projekt.InputFeedback.InputFeedback;
import com.example.oscar.tddd13_projekt.InputFeedback.InputValidator;

import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.is;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class InputTest {

    private ArrayList<String> validEmail = new ArrayList<>();
    private ArrayList<String> invalidEmail = new ArrayList<>();
    private ArrayList<String> validNumbers = new ArrayList<>();
    private ArrayList<String> invalidNumbers = new ArrayList<>();
    private ArrayList<String> validPassword = new ArrayList<>();

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void preTest(){
        initValidEmailAdresses();
        initInvalidEmailAdresses();
        initValidNumbers();
        initInvalidNumbers();
    }

    public void initValidEmailAdresses() {
        validEmail.add("Email@adress.com");
        validEmail.add("Goran@annieloof.se");
        validEmail.add("Georgij@Zjukov.ru");
        validEmail.add("oscar@andell.eu");
    }

    public void initInvalidEmailAdresses(){
        invalidEmail.add("123");
        invalidEmail.add("thisisnotanemail.com");
        invalidEmail.add("Hej mitt namn ar Olof");
        invalidEmail.add("AspectreishauntingEuropethespectreofcommunism.AllthepowersofoldEuropehaveenteredintoaholyallia...");
    }

    public void initValidNumbers(){
        validNumbers.add("1");
        validNumbers.add("33");
        validNumbers.add("7");
        validNumbers.add("99");
    }

    public void initInvalidNumbers(){
        invalidNumbers.add(" ");
        invalidNumbers.add("333");
        invalidNumbers.add("femtiofem");
        invalidNumbers.add("333123");
    }

    public void initValidPassword(){
        validPassword.add("password123");
    }


    @Test
    public void validEmailTest() {
        testInput(1000, validEmail, true, mActivityRule.getActivity().inputEmail);
    }

    @Test
    public void invalidEmailTest() {
        testInput(1000, invalidEmail, false, mActivityRule.getActivity().inputEmail);
    }

    @Test
    public void validNumberInputTest(){
       testInput(1001,validNumbers,true , mActivityRule.getActivity().inputNumber);
    }

    @Test
    public void invalidNumberInputTest(){
        testInput(1001,invalidNumbers,false, mActivityRule.getActivity().inputNumber);
    }


    public void testInput(int viewId,  ArrayList<String> testCases, boolean correctAns, InputFeedback inputfeedback){
        for (int i = 0; i < testCases.size(); i++) {
            onView(withId(viewId)).perform(typeText(testCases.get(i)), closeSoftKeyboard());
            assertThat(inputfeedback.hasValidInput(), is(correctAns));
            onView(withId(viewId)).perform(clearText());
        }
    }


}