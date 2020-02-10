package com.example.fastreading.trening;

import android.os.Bundle;

public class Observation extends AbstractTrening {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void textChanged(CharSequence s) {

    }

    @Override
    protected void newText() {

    }

    @Override
    protected void startTest() {
        testForLandscapeLayout();
    }

}
