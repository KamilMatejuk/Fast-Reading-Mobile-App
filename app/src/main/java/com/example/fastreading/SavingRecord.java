package com.example.fastreading;

import java.io.Serializable;
import java.util.Date;

public class SavingRecord implements Serializable {

    Date date;
    int correct;
    int incorrect;

    public SavingRecord(){
        this.date = new Date();
        this.correct = 0;
        this.incorrect = 0;
    }

    public void addCorrect(int c){
        this.correct += c;
    }

    public void addInorrect(int ic){
        this.incorrect += ic;
    }

    public Date getDate(){
        return this.date;
    }

    public int getPercentage(){
        return 100*correct/(correct+incorrect);
    }
}
