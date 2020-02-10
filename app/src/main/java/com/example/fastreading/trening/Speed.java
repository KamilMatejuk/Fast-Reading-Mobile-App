package com.example.fastreading.trening;

import android.os.Bundle;

public class Speed extends AbstractTrening {

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
        int num = (int)(Math.random()*(Math.pow(10,size)));
        givenNumbers.add(String.valueOf(num));
        textView.setText(String.valueOf(num));
    }

    @Override
    protected void startTest() {
        testForPortraitLayout();
    }
}
