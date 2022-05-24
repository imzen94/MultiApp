package com.example.multiapp;

public class Questions {
    private String question, option1, option2, option3;
    private int rightAns;

    public Questions(String question, String option1, String option2, String option3, int rightAns){
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.rightAns = rightAns;
    }

    public String getQ(){
        return question;
    }
    public void setQ(String question){
        this.question = question;
    }
    public String getOption1(){
        return option1;
    }
    public void setOption1(String question){
        this.option1 = option1;
    }
    public String getOption2(){
        return option2;
    }
    public void setOption2(String question){
        this.option2 = option2;
    }
    public String getOption3(){
        return option3;
    }
    public void setOption3(String question){
        this.option3 = option3;
    }
    public int getRightAns(){
        return rightAns;
    }
    public void setRightAns(int rightAns){
        this.rightAns = rightAns;
    }

}
