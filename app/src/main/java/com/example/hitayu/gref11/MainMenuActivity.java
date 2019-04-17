package com.example.hitayu.gref11;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainMenuActivity extends AppCompatActivity {


    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        signOut();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(MainMenuActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };

        // Find the View that shows the numbers category
        TextView textViewWordsection = (TextView) findViewById(R.id.word_section);

        // Set a click listener on that View
        textViewWordsection.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@link NumbersActivity}
                Intent wordSectionIntent = new Intent(getApplicationContext(), WordSectionActivity.class);

                // Start the new activity
                startActivity(wordSectionIntent);
            }
        });

        // Find the View that shows the numbers category
        TextView textViewSE = (TextView) findViewById(R.id.se_section);

        // Set a click listener on that View
        textViewSE.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@link NumbersActivity}
                Intent sentenceEquivalenceIntent = new Intent(getApplicationContext(), SentenceEquivalenceActivity.class);

                // Start the new activity
                startActivity(sentenceEquivalenceIntent);
            }
        });

        // Find the View that shows the numbers category
        TextView textViewTC = (TextView) findViewById(R.id.tc_section);

        // Set a click listener on that View
        textViewTC.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@link NumbersActivity}
                Intent textCompletionIntent = new Intent(getApplicationContext(), TextCompletionActivity.class);

                // Start the new activity
                startActivity(textCompletionIntent);
            }
        });

        // Find the View that shows the numbers category
        TextView textViewRC = (TextView) findViewById(R.id.rc_section);

        // Set a click listener on that View
        textViewRC.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@link NumbersActivity}
                Intent readingComprehensionIntent = new Intent(getApplicationContext(), ReadingComprehensionActivity.class);

                // Start the new activity
                startActivity(readingComprehensionIntent);
            }
        });

        // Find the View that shows the numbers category
        TextView textViewHint = (TextView) findViewById(R.id.hint_section);

        // Set a click listener on that View
        textViewHint.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@link NumbersActivity}
                Intent hintsIntent = new Intent(getApplicationContext(), HintsActivity.class);

                // Start the new activity
                startActivity(hintsIntent);
            }
        });
    }

    //sign out method
    public void signOut() {
        auth.signOut();
        finish();
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
    }
}
