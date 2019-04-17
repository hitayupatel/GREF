package com.example.hitayu.gref11;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReadingComprehensionActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private TextView quesTextView, scoreTextView, explanationTextView, passageTextView;
    private RadioButton radioButton, radioButton1, radioButton2, radioButton3, radioButton4;
    private RadioButton radioButton5;
    private RadioGroup radioGroup;
    private List<RCPassage> mRCPassageList;
    private List<RCPassageQuestions> mRCQuestionsList;
    private RCPassage mRCPassage;
    private RCPassageQuestions mRCPassageQuestions;
    private Button submitButton, nextButton, showExplanationButton, hideExplanationButton;
    private Button nextPassageButton;
    private int score = 0;
    private String scr = " ";
    private int flag = 0;
    private int flagFirstQuestion = 0;
    private int count = 0;
    private int flagNext = 1;
    private int flagNextPassage = 0;
    private int passageCount = 0;
    private int passageQuestions = 0;
    private int passageQuestionCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_comprehension);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mDatabases = FirebaseDatabase.getInstance().getReference().
                child("categories");
        DatabaseReference mDatabase1 = mDatabases.child("Reading Comprehension");
        DatabaseReference mDatabase2 = mDatabase1.child("Passages");
        DatabaseReference mDatabase3 = mDatabase1.child("Questions");

        score = 0;
        scr = String.valueOf(score);
        scoreTextView = (TextView) findViewById(R.id.score_rc);
        scoreTextView.setText(scr);

        Log.v("Child", "DataBaseDetails");
        Log.v("Child", mDatabase.getRoot().toString());
        Log.v("Child", mDatabase.toString());
        Log.v("Child", mDatabase1.toString());
        Log.v("Child", mDatabase2.child("Passage").toString());
        Log.v("strin``g", "data");

        passageTextView = (TextView) findViewById(R.id.rc_passage_part);
        quesTextView = (TextView) findViewById(R.id.rc_question_part);
        radioGroup = (RadioGroup) findViewById(R.id.rc_radio_group);
        radioButton1 = (RadioButton) findViewById(R.id.rc_option1);
        radioButton2 = (RadioButton) findViewById(R.id.rc_option2);
        radioButton3 = (RadioButton) findViewById(R.id.rc_option3);
        radioButton4 = (RadioButton) findViewById(R.id.rc_option4);
        radioButton5 = (RadioButton) findViewById(R.id.rc_option5);
        submitButton = findViewById(R.id.submit_rc);
        nextButton = findViewById(R.id.next_rc);
        nextPassageButton = findViewById(R.id.next_passage_rc);
        showExplanationButton = findViewById(R.id.show_expl_rc);
        hideExplanationButton = findViewById(R.id.hide_expl_rc);
        explanationTextView = findViewById(R.id.expl_text_rc);
        mRCPassageList = new ArrayList<RCPassage>();
        mRCQuestionsList = new ArrayList<RCPassageQuestions>();
        final String[] a1 = {" "};

        nextButton.setVisibility(View.INVISIBLE);
        showExplanationButton.setVisibility(View.INVISIBLE);
        hideExplanationButton.setVisibility(View.INVISIBLE);
        nextPassageButton.setVisibility(View.INVISIBLE);

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
                // get selected radio button from radioGroup
                int selectedId = radioGroup.getCheckedRadioButtonId();
                //Log.v("id",String.valueOf(selectedId));

                if (selectedId < 0) {
                    Toast.makeText(getApplicationContext(), "Incorrect Answer", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    // find the radiobutton by returned id
                    radioButton = findViewById(selectedId);

                    Log.v("rad", radioButton.getText().toString());
                    if (radioButton.getText().toString().equals(a1[0])) {
                        flag = 1;
                    } else {
                        Toast.makeText(getApplicationContext(), "Incorrect Answer",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                if (flag == 1) {
                    score++;
                    scr = String.valueOf(score);
                    scoreTextView.setText(scr);
                }

                if (flagNext == 1) {
                    nextButton.setVisibility(View.VISIBLE);
                } else {
                    nextButton.setVisibility(View.INVISIBLE);
                }
                showExplanationButton.setVisibility(View.VISIBLE);
                hideExplanationButton.setVisibility(View.VISIBLE);
                submitButton.setVisibility(View.INVISIBLE);
            }
        });

        if(flagNextPassage == 0) {
            nextPassageButton.setVisibility(View.INVISIBLE);
        } else {
            nextPassageButton.setVisibility(View.VISIBLE);
        }

        mDatabase2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int ct = 0;
                int questionNumber = 0;
                for (DataSnapshot passageValue : dataSnapshot.getChildren()) {
                    String passage = passageValue.child("Passage").getValue(String.class);
                    String questions = passageValue.child("Questions").getValue(String.class);
                    questionNumber = Integer.parseInt(questions);

                    Log.v("Passage", passage + " / " + questionNumber);
                    passageTextView.setText(passage);
                    mRCPassage = new RCPassage(passage, questionNumber);
                    mRCPassageList.add(mRCPassage);

                    ct++;
                    passageCount = ct;
                    passageQuestions = questionNumber;

                }

                nextPassageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RCPassage mRC;
                        Log.v("passage left", String.valueOf(passageCount));
                        if (passageCount > 0) {
                            if (passageCount == mRCPassageList.size()) {
                                Log.v("passage list size", String.valueOf(mRCPassageList.size()));
                                passageCount--;
                            }
                            passageCount--;
                            Log.v("passage left", String.valueOf(passageCount));
                            mRC = mRCPassageList.get(passageCount);
                            //Log.v("ans", mRC.getPassage() + " / " + mRC.getQuestionNumbers());
                            passageTextView.setText(mRC.getPassage());
                            int questionNumber = mRC.getQuestionNumbers();
                            passageQuestions = questionNumber;
                            flagFirstQuestion = 1;

                            if (passageCount == 0) {
                                flagNextPassage = 0;
                            } else {
                                flagNextPassage = 1;
                            }
                            nextPassageButton.setVisibility(View.INVISIBLE);
                        } else {
                            Toast.makeText(getApplicationContext(), "Questions are over",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int ct = 0;
                for (DataSnapshot questionValue : dataSnapshot.getChildren()) {
                    String answer = questionValue.child("Answer").getValue(String.class);
                    String explanation = questionValue.child("Explanation").getValue(String.class);
                    String option1 = questionValue.child("OptionA").getValue(String.class);
                    String option2 = questionValue.child("OptionB").getValue(String.class);
                    String option3 = questionValue.child("OptionC").getValue(String.class);
                    String option4 = questionValue.child("OptionD").getValue(String.class);
                    String option5 = questionValue.child("OptionE").getValue(String.class);
                    String question = questionValue.child("Question").getValue(String.class);
                    a1[0] = answer;

                    mRCPassageQuestions = new RCPassageQuestions(question, option1, option2,
                            option3, option4, option5, answer, explanation);

                    Log.d("TAG", answer + " / " + explanation + " / " + option1
                            + " / " + option2 + " / " + option3 + " / " + option4 + " / " + option5
                            + " / " + question);

                    radioButton1.setText(option1);
                    radioButton2.setText(option2);
                    radioButton3.setText(option3);
                    radioButton4.setText(option4);
                    radioButton5.setText(option5);
                    quesTextView.setText(question);
                    mRCQuestionsList.add(mRCPassageQuestions);

                    ct++;
                    passageQuestionCount = ct;
                }

                showExplanationButton.setOnClickListener(new View.OnClickListener()

                {
                    @Override
                    public void onClick(View view) {
                        RCPassageQuestions mRC;
                        Log.v("intout", String.valueOf(passageQuestionCount));
                        if (passageQuestionCount == mRCQuestionsList.size()) {
                            mRC = mRCQuestionsList.get(passageQuestionCount - 1);
                        } else {
                            mRC = mRCQuestionsList.get(passageQuestionCount);
                        }
                        //Log.v("expl",tcOne.getExplanation());
                        explanationTextView.setVisibility(View.VISIBLE);
                        explanationTextView.setText(mRC.getExplanation());
                    }
                });

                nextButton.setOnClickListener(new View.OnClickListener()

                {
                    @Override
                    public void onClick(View view) {
                        RCPassageQuestions mRC;
                        //Log.v("regular count", String.valueOf(passageQuestionCount));
                        if(passageQuestions > 1) {

                            if (passageQuestionCount > 0) {
                                if (passageQuestionCount == mRCQuestionsList.size()) {
                                    Log.v("question list size", String.valueOf(mRCQuestionsList.size()));
                                    passageQuestionCount--;
                                }
                                passageQuestionCount--;
                                Log.v("question count", String.valueOf(passageQuestionCount));

                                /*if(flagFirstQuestion == 1) {
                                    passageQuestionCount++;
                                    passageQuestions +=2;
                                    flagFirstQuestion = 0;
                                    Log.v("first int", String.valueOf(passageQuestionCount));
                                }*/
                                if(flagFirstQuestion == 0) {
                                    passageQuestions--;
                                }
                                flagFirstQuestion = 0;

                                Log.v("passage question count", String.valueOf(passageQuestions));
                                mRC = mRCQuestionsList.get(passageQuestionCount);
                                Log.v("ans", mRC.getAnswer());

                                Log.v("flagfirst",String.valueOf(flagFirstQuestion));
                                radioGroup.clearCheck();
                                quesTextView.setText(mRC.getQuestion());
                                radioButton1.setText(mRC.getOptionA());
                                radioButton2.setText(mRC.getOptionB());
                                radioButton3.setText(mRC.getOptionC());
                                radioButton4.setText(mRC.getOptionD());
                                radioButton5.setText(mRC.getOptionE());
                                String answer;
                                answer = mRC.getAnswer();
                                a1[0] = answer;
                                submitButton.setVisibility(View.VISIBLE);
                                nextButton.setVisibility(View.INVISIBLE);
                                showExplanationButton.setVisibility(View.INVISIBLE);
                                hideExplanationButton.setVisibility(View.INVISIBLE);
                                String s = " ";
                                explanationTextView.setText(s);
                                explanationTextView.setVisibility(View.INVISIBLE);
                                if (passageQuestionCount == 0) {
                                    flagNext = 0;
                                } else {
                                    flagNext = 1;
                                }
                                nextPassageButton.setVisibility(View.INVISIBLE);

                                flagNextPassage = 0;
                                Log.v("passage count",String.valueOf(passageCount));

                            } else {
                                Toast.makeText(getApplicationContext(), "Questions are over",
                                        Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            flagNextPassage = 1;
                            Log.v("abc","aaa");
                            Toast.makeText(getApplicationContext(), "Questions are over Press " +
                                    "Next Passage", Toast.LENGTH_SHORT).show();
                            nextPassageButton.setVisibility(View.VISIBLE);
                            Log.v("question count",String.valueOf(passageQuestionCount));
                            Log.v("passage question count",String.valueOf(passageQuestions));
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
