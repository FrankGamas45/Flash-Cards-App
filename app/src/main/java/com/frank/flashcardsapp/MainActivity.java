package com.frank.flashcardsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    com.frank.flashcardsapp.FlashcardDatabase flashcardDatabase;
    List<com.frank.flashcardsapp.Flashcard> allFlashcards;
    int currentCardDisplayedIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flashcardDatabase = new com.frank.flashcardsapp.FlashcardDatabase(getApplicationContext());
        allFlashcards = flashcardDatabase.getAllCards();
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

        findViewById(R.id.next_Btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TextView) findViewById(R.id.question)).setVisibility(View.INVISIBLE);
                ((TextView) findViewById(R.id.answer)).setVisibility(View.VISIBLE);
                if(allFlashcards.size()==0)
                    return;
                currentCardDisplayedIndex++;

                if(currentCardDisplayedIndex>=allFlashcards.size()){
                    currentCardDisplayedIndex = 0;
                }
                allFlashcards = flashcardDatabase.getAllCards();
                com.frank.flashcardsapp.Flashcard flashcard = allFlashcards.get(currentCardDisplayedIndex);

                ((TextView) findViewById(R.id.question)).setText(flashcard.getAnswer());
                ((TextView) findViewById(R.id.answer)).setText(flashcard.getQuestion());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && data != null) { // this 100 needs to match the 100 we used when we called startActivityForResult!
            String question = data.getExtras().getString("key1"); // 'string1' needs to match the key we used when we put the string in the Intent
            String answer = data.getExtras().getString("key2");
            TextView flashcardQuestion = findViewById(R.id.question);
            TextView flashcardAnswer = findViewById(R.id.answer);
            flashcardQuestion.setVisibility(View.VISIBLE);
            flashcardAnswer.setVisibility(View.INVISIBLE);
            flashcardQuestion.setText(question);
            flashcardAnswer.setText(answer);
            flashcardDatabase.insertCard(new com.frank.flashcardsapp.Flashcard(question, answer));
            allFlashcards = flashcardDatabase.getAllCards();
        }
        else{
            TextView flashcardQuestion = findViewById(R.id.question);
            TextView flashcardAnswer = findViewById(R.id.answer);
            flashcardQuestion.setVisibility(View.VISIBLE);
            flashcardAnswer.setVisibility(View.INVISIBLE);
        }
    }
}