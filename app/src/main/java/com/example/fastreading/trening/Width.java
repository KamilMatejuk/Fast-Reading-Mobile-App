package com.example.fastreading.trening;

import android.os.Bundle;
import android.text.Html;

public class Width extends AbstractTrening {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void textChanged(CharSequence s) {
        if(givenNumbers.get(givenNumbers.size()-1).length() == s.length()){
            userAnswers.add(String.valueOf(s));
            waitingForInput = false;
            editText.setText("");
        }
    }

    @Override
    protected void newText() {
        int num1 = (int)(Math.random()*(Math.pow(10,size)));
        int num2 = (int)(Math.random()*(Math.pow(10,size)));
        givenNumbers.add(num1 +"."+ num2);

        String testString = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        int charsInOneLine = textView.getPaint().breakText(testString,0, testString.length(),
                true, textView.getWidth(), null);
        int space = (int) ((charsInOneLine - (2*size + 1)) * (((double) width) / 100));
        String s = String.valueOf(num1);
        for(int i=0; i<space; i++){
            s += "&nbsp;";
        }
        s += "&#9899";
        for(int i=0; i<space; i++){
            s += "&nbsp;";
        }
        s += String.valueOf(num2);
        textView.setText(Html.fromHtml(s));
    }

    @Override
    protected void startTest() {
        testForLandscapeLayout();
    }
}
