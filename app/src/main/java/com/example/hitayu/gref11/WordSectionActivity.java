package com.example.hitayu.gref11;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class WordSectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_section);

        // Find the View that shows the numbers category
        TextView textViewWordSearch = (TextView) findViewById(R.id.word_search);

        // Set a click listener on that View
        textViewWordSearch.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@link NumbersActivity}
                Intent wordSearchIntent = new Intent(WordSectionActivity.this, SearchWordsActivity.class);

                // Start the new activity
                startActivity(wordSearchIntent);
            }
        });
        // Find the View that shows the numbers category
        TextView textViewPracticeWords = (TextView) findViewById(R.id.flashcard);

        // Set a click listener on that View
        textViewPracticeWords.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@link NumbersActivity}
                Intent wordPracticeIntent = new Intent(WordSectionActivity.this, PracticeWordsActivity.class);

                // Start the new activity
                startActivity(wordPracticeIntent);
            }
        });
    }
}
