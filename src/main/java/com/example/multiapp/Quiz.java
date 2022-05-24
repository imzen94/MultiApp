package com.example.multiapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Quiz extends AppCompatActivity {

    private List<Questions> questionsList;
    private TextView tvQuestion, tvScore, tvQuestNo;
    private RadioGroup radioGroup;
    private RadioButton option1, option2, option3;
    private Button btnNext;
    private TextView alertTextView;
    private int HighScore = 0;

    int totalQ;
    int qNo;
    int score = 0;

    ColorStateList dfRbColor;
    boolean answered;
    boolean finished = false;


    private Questions currentQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);


        questionsList = new ArrayList<>();
        tvQuestion = findViewById(R.id.Question);
        tvQuestNo = findViewById(R.id.QNo);
        tvScore = findViewById(R.id.Score);
        radioGroup = findViewById(R.id.Options);
        option1 = findViewById(R.id.Option1);
        option2 = findViewById(R.id.Option2);
        option3 = findViewById(R.id.Option3);
        btnNext = findViewById(R.id.btnNext);
        alertTextView = findViewById(R.id.alertTextView);

        dfRbColor = option1.getTextColors();

        addQ();
        totalQ = questionsList.size();
        showNextQ();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(finished){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Quiz.this);

                    builder.setTitle("Quiz Finished");
                    builder.setMessage("You Scored " + score + "/" + totalQ + " !");


                    SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    HighScore = sharedPreferences.getInt("HighScore", 0);

                    if(score>HighScore){
                        myEdit.putInt("HighScore", score);
                        myEdit.commit();
                    }


                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            alertTextView.setVisibility(View.VISIBLE);
                            Intent intent = new Intent(Quiz.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
                    builder.show();
                }else if(!answered){
                   if(option1.isChecked() || option2.isChecked() || option3.isChecked()) {
                       checkAnswer();
                   }else{
                       Toast.makeText(Quiz.this, "Select an answer", Toast.LENGTH_SHORT).show();
                   }
                }else{
                    showNextQ();
                }
            }
        });


    }

    private void checkAnswer() {
        answered = true;
        RadioButton rbSelected = findViewById(radioGroup.getCheckedRadioButtonId());
        int answerNo = radioGroup.indexOfChild(rbSelected) + 1;
        if(answerNo == currentQ.getRightAns()){
            score++;
            tvScore.setText("Score: " + score);
        }
        option1.setTextColor(Color.RED);
        option2.setTextColor(Color.RED);
        option3.setTextColor(Color.RED);

        switch (currentQ.getRightAns()){
            case 1:
                option1.setTextColor(Color.GREEN);
                break;

            case 2:
                option2.setTextColor(Color.GREEN);
                break;

            case 3:
                option3.setTextColor(Color.GREEN);
                break;
        }

        if(qNo < totalQ){
            btnNext.setText(">>");
        }else{
            btnNext.setText("Finished!");
            finished = true;
        }
    }


    private void showNextQ() {

        radioGroup.clearCheck();
        option1.setTextColor(dfRbColor);
        option2.setTextColor(dfRbColor);
        option3.setTextColor(dfRbColor);

        if(qNo < totalQ){
            currentQ = questionsList.get(qNo);
            tvQuestion.setText(currentQ.getQ());
            option1.setText(currentQ.getOption1());
            option2.setText(currentQ.getOption2());
            option3.setText(currentQ.getOption3());

            qNo++;
            btnNext.setText("Submit");
            tvQuestNo.setText("Q" + qNo + "/" + totalQ);
            answered = false;
        }

    }


    private void addQ(){
        Random rand = new Random();
        final int  MIN = 1;
        final int  MAXINDEX = 10;
        final int  MAXTABLE = 10;
        final int  STEP = 1;
        String question = null;
        int ans = 0;
        int falseOption1 = 0;
        int falseOption2 = 0;


        int i,j;
        for(j=MIN;j<=MAXTABLE && questionsList.size() < 10; j+=STEP){
            i = rand.nextInt(MAXINDEX) + 1;
            ans = i * j;
            question = i + " * " + j + " = ?" ;
            falseOption1 = ans + rand.nextInt(20) + 1;
            falseOption2 = ans + rand.nextInt(20) + 1;

            List<Integer> optionList = new ArrayList();
            optionList.add(ans);
            optionList.add(falseOption1);
            optionList.add(falseOption2);
            Collections.shuffle(optionList);

            int rightAnsNo = 1;
            while(rightAnsNo <= 3 && (int)optionList.get(rightAnsNo-1) != ans)
                rightAnsNo++;

            questionsList.add(new Questions(question, Integer.toString(optionList.get(0)), Integer.toString(optionList.get(1)), Integer.toString(optionList.get(2)), rightAnsNo));
        }


    }


}