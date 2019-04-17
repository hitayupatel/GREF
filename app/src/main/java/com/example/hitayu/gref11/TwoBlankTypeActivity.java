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

public class TwoBlankTypeActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private TextView quesTextView, scoreTextView , explanationTextView;
    private RadioButton radioButton1, radioButton2, radioButton3, radioButton4, radioButton5;
    private RadioButton radioButton6, radioButtonAnswer1 , radioButtonAnswer2;
    private Button nextButton, submitButton ,showExplanationButton , hideExplanationButton;
    private RadioGroup radioGroup1, radioGroup2;
    private List<TCTwoBlankQuestions> mTCTwoBlankQuestions;
    private TCTwoBlankQuestions mQuestion;
    private int score = 0;
    private String scr = " ";
    private int flagNext = 1;
    private int flag = 0;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_blank_type);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mDatabases = FirebaseDatabase.getInstance().getReference().
                child("categories");
        DatabaseReference mDatabase1 = mDatabases.child("Text Completion");
        DatabaseReference mDatabase2 = mDatabase1.child("Two Blank");

        score = 0;
        scr = String.valueOf(score);
        scoreTextView = findViewById(R.id.score_tc_two_blank);
        scoreTextView.setText(scr);

        Log.v("Child", "DataBaseDetails");
        Log.v("Child", mDatabase.getRoot().toString());
        Log.v("Child", mDatabase.toString());
        Log.v("Child", mDatabase1.toString());
        Log.v("Child", mDatabase2.child("Question 01").toString());
        Log.v("string","data");

        quesTextView = findViewById(R.id.ques_tc_two_blank);
        radioGroup1 = findViewById(R.id.tc_two_blank_radio1);
        radioGroup2 = findViewById(R.id.tc_two_blank_radio2);
        radioButton1 = findViewById(R.id.tctwo_option1);
        radioButton2 = findViewById(R.id.tctwo_option2);
        radioButton3 = findViewById(R.id.tctwo_option3);
        radioButton4 = findViewById(R.id.tctwo_option4);
        radioButton5 = findViewById(R.id.tctwo_option5);
        radioButton6 = findViewById(R.id.tctwo_option6);
        submitButton = findViewById(R.id.submit_tc_two_blank);
        nextButton = findViewById(R.id.next_tc_two_blank);
        showExplanationButton = findViewById(R.id.show_expl_tc_two_blank);
        hideExplanationButton = findViewById(R.id.hide_expl_tc_two_blank);
        explanationTextView = findViewById(R.id.expl_text_tc_two_blank);
        mTCTwoBlankQuestions = new ArrayList<TCTwoBlankQuestions>();
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
               // get selected radio button from radioGroup
                int selectedId1 = radioGroup1.getCheckedRadioButtonId();

                // get selected radio button from radioGroup
                int selectedId2 = radioGroup2.getCheckedRadioButtonId();

                if(selectedId1 < 0 || selectedId2 < 0) {
                    Toast.makeText(getApplicationContext(),"Select the options available",Toast.LENGTH_SHORT)
                            .show();
                } else {

                    // find the radiobutton by returned id
                    radioButtonAnswer1 = findViewById(selectedId1);

                    // find the radiobutton by returned id
                    radioButtonAnswer2 = findViewById(selectedId2);

                    if(radioButtonAnswer1.getText().toString().equals(a1[0]) &&
                            radioButtonAnswer2.getText().toString().equals(a2[0])) {
                        flag = 1;
                    } else {
                        flag = 0;
                        Toast.makeText(getApplicationContext(),"Incorrect Answer",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                if( flag == 1) {
                    score++;
                    scr = String.valueOf(score);
                    scoreTextView.setText(scr);
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

        mDatabase2.addValueEventListener(new ValueEventListener() {
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

                    mQuestion = new TCTwoBlankQuestions(question ,answer1, answer2, option1, option2
                            , option3, option4, option5, option6 ,explanation);
                    Log.d("TAG", answer1 + " / " + answer2 + " / "  + explanation + " / " + option1
                            + " / " + option2 + " / " + option3 + " / " + option4 + " / " + option5
                            + " / " + question);

                    a1[0] = answer1;
                    a2[0] = answer2;
                    quesTextView.setText(question);
                    radioButton1.setText(option1);
                    radioButton2.setText(option2);
                    radioButton3.setText(option3);
                    radioButton4.setText(option4);
                    radioButton5.setText(option5);
                    radioButton6.setText(option6);
                    mTCTwoBlankQuestions.add(mQuestion);

                    ct++;
                    count = ct;

                    Log.v("count",String.valueOf(count));
                    /*SEQuestions s11 = new SEQuestions();
                    s11 = mSEQuestions.get(0);
                    Log.v("str1",s11.getAnswer1());*/
                }

                showExplanationButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TCTwoBlankQuestions tcTwo = new TCTwoBlankQuestions();
                        Log.v("intout", String.valueOf(count));
                        if(count == mTCTwoBlankQuestions.size()) {
                            tcTwo = mTCTwoBlankQuestions.get(count - 1);
                        } else {
                            tcTwo = mTCTwoBlankQuestions.get(count);
                        }
                        //Log.v("expl",tcOne.getExplanation());
                        explanationTextView.setVisibility(View.VISIBLE);
                        explanationTextView.setText(tcTwo.getExplanation());
                    }
                });

                nextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TCTwoBlankQuestions tcTwo;
                        Log.v("int", String.valueOf(count));
                        if(count > 0) {
                            if(count == mTCTwoBlankQuestions.size()) {
                                Log.v("size",String.valueOf(mTCTwoBlankQuestions.size()));
                                count--;
                            }
                            count--;
                            Log.v("int", String.valueOf(count));
                            tcTwo = mTCTwoBlankQuestions.get(count);
                            Log.v("ans", tcTwo.getAnswer1());

                            radioGroup1.clearCheck();
                            radioGroup2.clearCheck();
                            quesTextView.setText(tcTwo.getQuestion());
                            radioButton1.setText(tcTwo.getOptionA());
                            radioButton2.setText(tcTwo.getOptionB());
                            radioButton3.setText(tcTwo.getOptionC());
                            radioButton4.setText(tcTwo.getOptionD());
                            radioButton5.setText(tcTwo.getOptionE());
                            radioButton6.setText(tcTwo.getOptionF());
                            String answer1 ,answer2;
                            answer1 = tcTwo.getAnswer1();
                            answer2 = tcTwo.getAnswer2();
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
