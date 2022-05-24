package com.example.multiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Results extends AppCompatActivity {

    private int HighScore = 0;
    private TextView score;
    private ImageView achievement1, achievement2, achievement3, achievement4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        score = findViewById(R.id.score);
        achievement1 = findViewById(R.id.Achievement1);
        achievement2 = findViewById(R.id.Achievement2);
        achievement3 = findViewById(R.id.Achievement3);
        achievement4 = findViewById(R.id.Achievement4);


        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        HighScore = sharedPreferences.getInt("HighScore", 0);

        if(HighScore > 0){
            score.setText(HighScore+"/10");
        }else {
            score.setText("Quiz haven't been started!");
        }

        if(HighScore >= 3){
            achievement1.setImageResource(R.drawable._9_star_png_image);
        }
        if(HighScore >= 5){
            achievement2.setImageResource(R.drawable._9_star_png_image);
        }
        if(HighScore >= 7){
            achievement3.setImageResource(R.drawable._9_star_png_image);
        }
        if(HighScore == 10){
            achievement4.setImageResource(R.drawable._9_star_png_image);
        }

    }

}