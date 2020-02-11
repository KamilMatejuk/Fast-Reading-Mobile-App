package com.example.fastreading;

import android.text.format.DateUtils;

import java.io.Serializable;
import java.util.Date;

public class SavingRecord implements Serializable {

    private Date date;
    private int correct;
    private int incorrect;

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

    public boolean isToday(){
        return DateUtils.isToday(this.date.getTime());
    }

    public int getPercentage(){
        return 100*correct/(correct+incorrect);
    }

    public int getCount(){
        return (this.correct + this.incorrect);
    }
}
