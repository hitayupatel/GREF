package com.example.hitayu.gref11;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SentenceEquivalenceActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private TextView quesTextView, scoreTextView, explanationTextView;
    private CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6;
    private List<SEQuestions> mSEQuestions;
    private SEQuestions mQuestion;
    private Button submitButton, nextButton, showExplanationButton, hideExplanationButton;
    private int score = 0;
    private String scr = " ";
    private int count = 0;
    private int flagNext = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sentence_equivalence);

        //mDatabase = FirebaseDatabase.getInstance().getReference().child("categories").child(
                //"Sentence Eqiuvalence").child("Question01").child("Question");

        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mDatabases = FirebaseDatabase.getInstance().getReference().
                child("categories");
        DatabaseReference mDatabase1 = mDatabases.child("Sentence Equivalence");

        score = 0;
        scr = String.valueOf(score);
        scoreTextView = findViewById(R.id.score_se);
        scoreTextView.setText(scr);

        Log.v("Child", "DataBaseDetails");
        Log.v("Child", mDatabase.getRoot().toString());
        Log.v("Child", mDatabase.toString());
        Log.v("Child", mDatabase1.toString());
        Log.v("Child", mDatabase1.child("Question 01").toString());
        Log.v("string","data");
        //DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference().child("se");
        //DatabaseReference child1 = mDatabase.child("question");


        quesTextView = findViewById(R.id.se_question_part);
        checkBox1 = findViewById(R.id.se_option1);
        checkBox2 = findViewById(R.id.se_option2);
        checkBox3 = findViewById(R.id.se_option3);
        checkBox4 = findViewById(R.id.se_option4);
        checkBox5 = findViewById(R.id.se_option5);
        checkBox6 = findViewById(R.id.se_option6);
        submitButton = findViewById(R.id.submit_se);
        showExplanationButton = findViewById(R.id.show_expl_se);
        nextButton = findViewById(R.id.next_se);
        explanationTextView = findViewById(R.id.expl_text_se);
        hideExplanationButton = findViewById(R.id.hide_expl_se);
        mSEQuestions = new ArrayList<SEQuestions>();
        final String[] a1 = {" "};
        final String[] a2 = {" "};

        nextButton.setVisibility(View.INVISIBLE);
        showExplanationButton.setVisibility(View.INVISIBLE);
        hideExplanationButton.setVisibility(View.INVISIBLE);

        hideExplanationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = " ";
                explanationTextView.setText(str);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int countc = 0;

                List<CheckBox> items = new ArrayList<CheckBox>();
                items.add(checkBox1);
                items.add(checkBox2);
                items.add(checkBox3);
                items.add(checkBox4);
                items.add(checkBox5);
                items.add(checkBox6);

                for (CheckBox item : items){
                    if(item.isChecked()) {
                        String text = item.getText().toString();
                        Log.v("option", text);
                        countc++;
                    }
                }
                int flag1 = 0;
                int flag2 = 0;
                int flag = 0;
                if(countc == 2) {
                    for (CheckBox item : items){
                        if(item.isChecked()) {
                            if(item.getText().toString().equals(a1[0])) {
                                flag1 = 1;
                                break;
                            }
                        }
                    }
                    for (CheckBox item : items){
                        if(item.isChecked()) {
                            if(item.getText().toString().equals(a2[0])) {
                                flag2 = 1;
                                break;
                            }
                        }
                    }
                    if(flag1 == 1 && flag2 == 1) {
                        score++;
                        scr = String.valueOf(score);
                        scoreTextView.setText(scr);
                    } else {
                        Toast.makeText(getApplicationContext(),"Incorrect Answer",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Log.v("msg","Wrong Answer");
                    Toast.makeText(getApplicationContext(),"Incorrect Answer",Toast.LENGTH_SHORT)
                            .show();
                }

                if(flagNext == 1) {
                    nextButton.setVisibility(View.VISIBLE);
                } else {
                    nextButton.setVisibility(View.INVISIBLE);
                }
                showExplanationButton.setVisibility(View.VISIBLE);
                hideExplanationButton.setVisibility(View.VISIBLE);
                submitButton.setVisibility(View.INVISIBLE);

            }
        });


        mDatabase1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int ct = 0;
                for(DataSnapshot questionValue : dataSnapshot.getChildren()) {
                    String answer1 = questionValue.child("Answer1").getValue(String.class);
                    String answer2 = questionValue.child("Answer2").getValue(String.class);
                    String explanation = questionValue.child("Explanation").getValue(String.class);
                    String option1 = questionValue.child("OptionA").getValue(String.class);
                    String option2 = questionValue.child("OptionB").getValue(String.class);
                    String option3 = questionValue.child("OptionC").getValue(String.class);
                    String option4 = questionValue.child("OptionD").getValue(String.class);
                    String option5 = questionValue.child("OptionE").getValue(String.class);
                    String option6 = questionValue.child("OptionF").getValue(String.class);
                    String question = questionValue.child("Question").getValue(String.class);

                    a1[0] = answer1;
                    a2[0] = answer2;

                    mQuestion = new SEQuestions(question ,answer1 ,answer2 ,option1 ,option2 ,
                            option3 ,option4 ,option5 ,option6 ,explanation);
                    Log.v("TAG", answer1 + " / " + answer2 + " / " + explanation + " / " + option1
                            + " / " + option2 + " / " + option3 + " / " + option4 + " / " + option5
                            + " / " + option6 + " / " + question);
                    quesTextView.setText(question);
                    checkBox1.setText(option1);
                    checkBox2.setText(option2);
                    checkBox3.setText(option3);
                    checkBox4.setText(option4);
                    checkBox5.setText(option5);
                    checkBox6.setText(option6);
                    mSEQuestions.add(mQuestion);

                    ct++;
                    count = ct;
                }

                showExplanationButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SEQuestions s1 ;
                        Log.v("intout", String.valueOf(count));
                        if(count == mSEQuestions.size()) {
                            s1 = mSEQuestions.get(count - 1);
                        } else {
                            s1 = mSEQuestions.get(count);
                        }
                        //Log.v("expl",s1.getExplanation());
                        explanationTextView.setVisibility(View.VISIBLE);
                        explanationTextView.setText(s1.getExplanation());
                    }
                });

                nextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SEQuestions se ;
                        Log.v("int", String.valueOf(count));
                        if(count > 0) {
                            if(count == mSEQuestions.size()) {
                                Log.v("size",String.valueOf(mSEQuestions.size()));
                                count--;
                            }
                            count--;
                            Log.v("int", String.valueOf(count));
                            se = mSEQuestions.get(count);
                            Log.v("ans", se.getAnswer1());
                            checkBox1.setChecked(false);
                            checkBox2.setChecked(false);
                            checkBox3.setChecked(false);
                            checkBox4.setChecked(false);
                            checkBox5.setChecked(false);
                            checkBox6.setChecked(false);
                            quesTextView.setText(se.getQuestion());
                            checkBox1.setText(se.getOptionA());
                            checkBox2.setText(se.getOptionB());
                            checkBox3.setText(se.getOptionC());
                            checkBox4.setText(se.getOptionD());
                            checkBox5.setText(se.getOptionE());
                            checkBox6.setText(se.getOptionF());
                            String answer1, answer2;
                            answer1 = se.getAnswer1();
                            answer2 = se.getAnswer2();
                            a1[0] = answer1;
                            a2[0] = answer2;
                            submitButton.setVisibility(View.VISIBLE);
                            nextButton.setVisibility(View.INVISIBLE);
                            showExplanationButton.setVisibility(View.INVISIBLE);
                            hideExplanationButton.setVisibility(View.INVISIBLE);
                            String s = " ";
                            explanationTextView.setText(s);
                            explanationTextView.setVisibility(View.INVISIBLE);
                            if(count == 0) {
                                flagNext = 0;
                            } else {
                                flagNext = 1;
                            }

                        } else {
                            Toast.makeText(getApplicationContext(),"Questions are over",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
