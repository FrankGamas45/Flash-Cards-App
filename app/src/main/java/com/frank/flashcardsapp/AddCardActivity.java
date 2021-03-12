package com.frank.flashcardsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

    findViewById(R.id.cancel_Btn).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    });

    findViewById(R.id.save_Btn).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent data = new Intent();
            data.putExtra("key1", ((EditText)findViewById(R.id.question)).getText().toString());
            data.putExtra("key2", ((EditText)findViewById((R.id.answer))).getText().toString());
            setResult(RESULT_OK, data);
            finish();
        }
    });
    }
}