package com.example.hitayu.gref11;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class TextCompletionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_completion);

        // Find the View that shows the numbers category
        TextView textViewTCOneBlank = (TextView) findViewById(R.id.one_blank_type);

        // Set a click listener on that View
        textViewTCOneBlank.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@link NumbersActivity}
                Intent numbersIntent = new Intent(TextCompletionActivity.this, OneBlankTypeActivity.class);

                // Start the new activity
                startActivity(numbersIntent);
            }
        });

        // Find the View that shows the numbers category
        TextView textViewTCTwoBlank = (TextView) findViewById(R.id.two_blank_type);

        // Set a click listener on that View
        textViewTCTwoBlank.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@link NumbersActivity}
                Intent numbersIntent = new Intent(TextCompletionActivity.this, TwoBlankTypeActivity.class);

                // Start the new activity
                startActivity(numbersIntent);
            }
        });

        // Find the View that shows the numbers category
        TextView textViewTCThreeBlank = (TextView) findViewById(R.id.three_blank_type);

        // Set a click listener on that View
        textViewTCThreeBlank.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@link NumbersActivity}
                Intent numbersIntent = new Intent(TextCompletionActivity.this, ThreeBlankTypeActivity.class);

                // Start the new activity
                startActivity(numbersIntent);
            }
        });
    }
}
