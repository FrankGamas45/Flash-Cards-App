package com.frank.flashcardsapp;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
                    View answerSideView = findViewById(R.id.answer);

// get the center for the clipping circle
                    int cx = answerSideView.getWidth() / 2;
                    int cy = answerSideView.getHeight() / 2;

// get the final radius for the clipping circle
                    float finalRadius = (float) Math.hypot(cx, cy);

// create the animator for this view (the start radius is zero)
                    Animator anim = ViewAnimationUtils.createCircularReveal(answerSideView, cx, cy, 0f, finalRadius);

// hide the question and show the answer to prepare for playing the animation!
                    flashcardQuestion.setVisibility(View.INVISIBLE);
                    answerSideView.setVisibility(View.VISIBLE);

                    anim.setDuration(2000);
                    anim.start();
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
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

        findViewById(R.id.next_Btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(allFlashcards.size()==0)
                    return;
                currentCardDisplayedIndex++;

                if(currentCardDisplayedIndex>=allFlashcards.size()){
                    currentCardDisplayedIndex = 0;
                }
                allFlashcards = flashcardDatabase.getAllCards();
                com.frank.flashcardsapp.Flashcard flashcard = allFlashcards.get(currentCardDisplayedIndex);
                final Animation leftOutAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.left_out);
                final Animation rightInAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.right_in);
                leftOutAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        ((TextView)findViewById(R.id.answer)).setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // this method is called when the animation is finished playing
                        findViewById(R.id.question).startAnimation(rightInAnim);
                        ((TextView) findViewById(R.id.answer)).setText(flashcard.getAnswer());
                        ((TextView) findViewById(R.id.question)).setText(flashcard.getQuestion());
                        ((TextView) findViewById(R.id.question)).setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // we don't need to worry about this method
                    }
                });

                if(findViewById(R.id.question).getVisibility()==View.VISIBLE)
                    findViewById(R.id.question).startAnimation(leftOutAnim);
                else {
                    findViewById(R.id.answer).startAnimation(leftOutAnim);
                }
                
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        TextView flashcardQuestion = findViewById(R.id.question);
        TextView flashcardAnswer = findViewById(R.id.answer);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && data != null) { // this 100 needs to match the 100 we used when we called startActivityForResult!
            String question = data.getExtras().getString("key1"); // 'string1' needs to match the key we used when we put the string in the Intent
            String answer = data.getExtras().getString("key2");
            flashcardQuestion.setVisibility(View.VISIBLE);
            flashcardAnswer.setVisibility(View.INVISIBLE);
            flashcardQuestion.setText(question);
            flashcardAnswer.setText(answer);
            flashcardDatabase.insertCard(new com.frank.flashcardsapp.Flashcard(question, answer));
            allFlashcards = flashcardDatabase.getAllCards();
        }
        else{
            flashcardQuestion.setVisibility(View.VISIBLE);
            flashcardAnswer.setVisibility(View.INVISIBLE);
        }
    }
}