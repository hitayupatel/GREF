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

public class ThreeBlankTypeActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private TextView quesTextView, scoreTextView, explanationTextView;
    private RadioButton radioButton1, radioButton2, radioButton3, radioButton4, radioButton5;
    private RadioButton radioButton6, radioButton7, radioButton8, radioButton9;
    private RadioButton radioButtonAnswer1, radioButtonAnswer2, radioButtonAnswer3;
    private RadioGroup radioGroup1, radioGroup2, radioGroup3;
    private Button submitButton, nextButton, showExplanationButton , hideExplanationButton;
    private List<TCThreeBlankQuestions> mTCThreeBlankQuestions;
    private TCThreeBlankQuestions mQuestion;
    private int score = 0;
    private String scr = " ";
    private int count = 0;
    private int flagNext = 1;
    private int flag3 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_blank_type);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mDatabases = FirebaseDatabase.getInstance().getReference().
                child("categories");
        DatabaseReference mDatabase1 = mDatabases.child("Text Completion");
        DatabaseReference mDatabase2 = mDatabase1.child("Three Blank");

        score = 0;
        scr = String.valueOf(score);
        scoreTextView = findViewById(R.id.score_tc_three_blank);
        scoreTextView.setText(scr);

        Log.v("Child", "DataBaseDetails");
        Log.v("Child", mDatabase.getRoot().toString());
        Log.v("Child", mDatabase.toString());
        Log.v("Child", mDatabase1.toString());
        Log.v("Child", mDatabase2.child("Question 01").toString());
        Log.v("string","data");

        quesTextView = findViewById(R.id.ques_tc_three_blank);
        radioGroup1 = findViewById(R.id.tc_three_radio1);
        radioGroup2 = findViewById(R.id.tc_three_radio2);
        radioGroup3 = findViewById(R.id.tc_three_radio3);
        radioButton1 = findViewById(R.id.tcthree_option1);
        radioButton2 = findViewById(R.id.tcthree_option2);
        radioButton3 = findViewById(R.id.tcthree_option3);
        radioButton4 = findViewById(R.id.tcthree_option4);
        radioButton5 = findViewById(R.id.tcthree_option5);
        radioButton6 = findViewById(R.id.tcthree_option6);
        radioButton7 = findViewById(R.id.tcthree_option7);
        radioButton8 = findViewById(R.id.tcthree_option8);
        radioButton9 = findViewById(R.id.tcthree_option9);
        submitButton = findViewById(R.id.submit_tc_three_blank);
        nextButton = findViewById(R.id.next_tc_three_blank);
        showExplanationButton = findViewById(R.id.show_expl_tc_three_blank);
        hideExplanationButton = findViewById(R.id.hide_expl_tc_three_blank);
        explanationTextView = findViewById(R.id.expl_text_tc_three_blank);
        mTCThreeBlankQuestions = new ArrayList<TCThreeBlankQuestions>();

        final String[] a1 = {" "};
        final String[] a2 = {" "};
        final String[] a3 = {" "};

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
                int selectedId2 = radioGroup2.getCheckedRadioButtonId();
                int selectedId3 = radioGroup3.getCheckedRadioButtonId();

                if(selectedId1 < 0 || selectedId2 < 0 || selectedId3 <0) {
                    Toast.makeText(getApplicationContext(),"Select the options available",Toast.LENGTH_SHORT)
                            .show();
                } else {

                    // find the radiobutton by returned id
                    radioButtonAnswer1 = findViewById(selectedId1);

                    // find the radiobutton by returned id
                    radioButtonAnswer2 = findViewById(selectedId2);

                    // find the radiobutton by returned id
                    radioButtonAnswer3 = findViewById(selectedId3);

                    if (radioButtonAnswer1.getText().toString().equals(a1[0]) &&
                            radioButtonAnswer2.getText().toString().equals(a2[0]) &&
                            radioButtonAnswer3.getText().toString().equals(a3[0])) {
                        flag3 = 1;
                    } else {
                        flag3 = 0;
                        Toast.makeText(getApplicationContext(), "Incorrect Answer",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                if(flag3 == 1) {
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
                    String answer3 = questionValue.child("Answer3").getValue(String.class);
                    String explanation = questionValue.child("Explanation").getValue(String.class);
                    String option1 = questionValue.child("OptionA").getValue(String.class);
                    String option2 = questionValue.child("OptionB").getValue(String.class);
                    String option3 = questionValue.child("OptionC").getValue(String.class);
                    String option4 = questionValue.child("OptionD").getValue(String.class);
                    String option5 = questionValue.child("OptionE").getValue(String.class);
                    String option6 = questionValue.child("OptionF").getValue(String.class);
                    String option7 = questionValue.child("OptionG").getValue(String.class);
                    String option8 = questionValue.child("OptionH").getValue(String.class);
                    String option9 = questionValue.child("OptionI").getValue(String.class);
                    String question = questionValue.child("Question").getValue(String.class);

                    a1[0] = answer1;
                    a2[0] = answer2;
                    a3[0] = answer3;

                    mQuestion = new TCThreeBlankQuestions(question ,answer1, answer2, answer3,
                            option1, option2, option3, option4, option5, option6 ,option7 ,option8
                            ,option9,explanation);
                    Log.d("TAG", answer1 + " / " + answer2 + " / "  + explanation + " / " + option1
                            + " / " + option2 + " / " + option3 + " / " + option4 + " / " + option5
                            + " / " + question);
                    quesTextView.setText(question);
                    radioButton1.setText(option1);
                    radioButton2.setText(option2);
                    radioButton3.setText(option3);
                    radioButton4.setText(option4);
                    radioButton5.setText(option5);
                    radioButton6.setText(option6);
                    radioButton7.setText(option7);
                    radioButton8.setText(option8);
                    radioButton9.setText(option9);
                    mTCThreeBlankQuestions.add(mQuestion);

                    ct++;
                    count = ct;
                }

                showExplanationButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TCThreeBlankQuestions tcThree = new TCThreeBlankQuestions();
                        Log.v("intout", String.valueOf(count));
                        if(count == mTCThreeBlankQuestions.size()) {
                            tcThree = mTCThreeBlankQuestions.get(count - 1);
                        } else {
                            tcThree = mTCThreeBlankQuestions.get(count);
                        }
                        //Log.v("expl",tcOne.getExplanation());
                        explanationTextView.setVisibility(View.VISIBLE);
                        explanationTextView.setText(tcThree.getExplanation());
                    }
                });

                nextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TCThreeBlankQuestions tcThree;
                        Log.v("int", String.valueOf(count));
                        if(count > 0) {
                            if(count == mTCThreeBlankQuestions.size()) {
                                Log.v("size",String.valueOf(mTCThreeBlankQuestions.size()));
                                count--;
                            }
                            count--;
                            Log.v("int", String.valueOf(count));
                            tcThree = mTCThreeBlankQuestions.get(count);
                            Log.v("ans", tcThree.getAnswer1());

                            radioGroup1.clearCheck();
                            radioGroup2.clearCheck();
                            radioGroup3.clearCheck();
                            quesTextView.setText(tcThree.getQuestion());
                            radioButton1.setText(tcThree.getOptionA());
                            radioButton2.setText(tcThree.getOptionB());
                            radioButton3.setText(tcThree.getOptionC());
                            radioButton4.setText(tcThree.getOptionD());
                            radioButton5.setText(tcThree.getOptionE());
                            radioButton6.setText(tcThree.getOptionF());
                            radioButton7.setText(tcThree.getOptionG());
                            radioButton8.setText(tcThree.getOptionH());
                            radioButton9.setText(tcThree.getOptionI());
                            String answer1 ,answer2 ,answer3;
                            answer1 = tcThree.getAnswer1();
                            answer2 = tcThree.getAnswer2();
                            answer3 = tcThree.getAnswer3();
                            a1[0] = answer1;
                            a2[0] = answer2;
                            a3[0] = answer3;
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
