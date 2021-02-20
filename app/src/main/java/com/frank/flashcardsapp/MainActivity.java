package com.frank.flashcardsapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView flashcardQuestion = findViewById(R.id.question);
        TextView flashcardAnswer = findViewById(R.id.answer);
        findViewById(R.id.parent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(findViewById(R.id.answer).getVisibility()==View.INVISIBLE) {
                    flashcardQuestion.setVisibility(View.INVISIBLE);
                    flashcardAnswer.setVisibility(View.VISIBLE);
                }
                else{
                    flashcardQuestion.setVisibility(View.VISIBLE);
                    flashcardAnswer.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
}