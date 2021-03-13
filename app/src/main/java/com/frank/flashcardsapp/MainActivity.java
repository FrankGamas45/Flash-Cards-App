package com.frank.flashcardsapp;

import android.content.Intent;
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
        findViewById(R.id.add_Btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                MainActivity.this.startActivityForResult(intent, 100);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && data != null) { // this 100 needs to match the 100 we used when we called startActivityForResult!
            String string1 = data.getExtras().getString("key1"); // 'string1' needs to match the key we used when we put the string in the Intent
            String string2 = data.getExtras().getString("key2");
            TextView flashcardQuestion = findViewById(R.id.question);
            TextView flashcardAnswer = findViewById(R.id.answer);
            flashcardQuestion.setVisibility(View.VISIBLE);
            flashcardAnswer.setVisibility(View.INVISIBLE);
            flashcardQuestion.setText(string1);
            flashcardAnswer.setText(string2);
        }
        else{
            TextView flashcardQuestion = findViewById(R.id.question);
            TextView flashcardAnswer = findViewById(R.id.answer);
            flashcardQuestion.setVisibility(View.VISIBLE);
            flashcardAnswer.setVisibility(View.INVISIBLE);
        }
    }
}