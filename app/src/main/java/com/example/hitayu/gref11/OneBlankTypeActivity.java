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

public class OneBlankTypeActivity extends AppCompatActivity {
	
	private DatabaseReference mDatabase;
    private TextView quesTextView, scoreTextView, explanationTextView;
    private RadioButton radioButton, radioButton1, radioButton2, radioButton3, radioButton4;
    private RadioButton radioButton5;
    private RadioGroup radioGroup;
    private List<TCOneBlankQuestions> mTCOneBlankQuestions;
    private TCOneBlankQuestions mQuestion;
    private Button submitButton, nextButton, showExplanationButton, hideExplanationButton;
    private int score = 0;
    private String scr = " ";
    private int flag = 0;
    private int count = 0;
    private int flagNext = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_blank_type);
		
		mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mDatabases = FirebaseDatabase.getInstance().getReference().
                child("categories");
        DatabaseReference mDatabase1 = mDatabases.child("Text Completion");
        DatabaseReference mDatabase2 = mDatabase1.child("One Blank");

        score = 0;
        scr = String.valueOf(score);
        scoreTextView = findViewById(R.id.score_tc_one_blank);
        scoreTextView.setText(scr);

        Log.v("Child", "DataBaseDetails");
        Log.v("Child", mDatabase.getRoot().toString());
        Log.v("Child", mDatabase.toString());
        Log.v("Child", mDatabase1.toString());
        Log.v("Child", mDatabase2.child("Question 01").toString());
        Log.v("string","data");
		
		quesTextView = findViewById(R.id.ques_tc_one_blank);
        radioGroup = findViewById(R.id.tc_one_radio);
        radioButton1 = findViewById(R.id.tcone_option1);
        radioButton2 = findViewById(R.id.tcone_option2);
        radioButton3 = findViewById(R.id.tcone_option3);
        radioButton4 = findViewById(R.id.tcone_option4);
        radioButton5 = findViewById(R.id.tcone_option5);
        submitButton = findViewById(R.id.submit_tc_one_blank);
        nextButton = findViewById(R.id.next_tc_one_blank);
        showExplanationButton = findViewById(R.id.show_expl_tc_one_blank);
        hideExplanationButton = findViewById(R.id.hide_expl_tc_one_blank);
        explanationTextView = findViewById(R.id.expl_text_tc_one_blank);
        mTCOneBlankQuestions = new ArrayList<TCOneBlankQuestions>();
        final String[] a1 = {" "};

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
                int selectedId = radioGroup.getCheckedRadioButtonId();
                //Log.v("id",String.valueOf(selectedId));

                if(selectedId < 0) {
                    Toast.makeText(getApplicationContext(),"Select the options available",Toast.LENGTH_SHORT)
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

                if(flag == 1) {
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
                    String answer = questionValue.child("Answer").getValue(String.class);
                    String explanation = questionValue.child("Explanation").getValue(String.class);
                    String option1 = questionValue.child("OptionA").getValue(String.class);
                    String option2 = questionValue.child("OptionB").getValue(String.class);
                    String option3 = questionValue.child("OptionC").getValue(String.class);
                    String option4 = questionValue.child("OptionD").getValue(String.class);
                    String option5 = questionValue.child("OptionE").getValue(String.class);
                    String question = questionValue.child("Question").getValue(String.class);
                    a1[0] = answer;
                    mQuestion = new TCOneBlankQuestions(question ,answer ,option1 ,option2 ,
                            option3 ,option4 ,option5  ,explanation);
                    Log.d("TAG", answer + " / "  + explanation + " / " + option1
                            + " / " + option2 + " / " + option3 + " / " + option4 + " / " + option5
                            + " / " + question);
                    quesTextView.setText(question);
                    radioButton1.setText(option1);
                    radioButton2.setText(option2);
                    radioButton3.setText(option3);
                    radioButton4.setText(option4);
                    radioButton5.setText(option5);
                    mTCOneBlankQuestions.add(mQuestion);
                    ct++;
                    count = ct;

                    /*SEQuestions s11 = new SEQuestions();
                    s11 = mSEQuestions.get(0);
                    Log.v("str1",s11.getAnswer1());*/
                }

                showExplanationButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TCOneBlankQuestions tcOne = new TCOneBlankQuestions();
                        Log.v("intout", String.valueOf(count));
                        if(count == mTCOneBlankQuestions.size()) {
                            tcOne = mTCOneBlankQuestions.get(count - 1);
                        } else {
                            tcOne = mTCOneBlankQuestions.get(count);
                        }
                        //Log.v("expl",tcOne.getExplanation());
                        explanationTextView.setVisibility(View.VISIBLE);
                        explanationTextView.setText(tcOne.getExplanation());
                    }
                });

                nextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TCOneBlankQuestions tcOne ;
                        Log.v("int", String.valueOf(count));
                        if(count > 0) {
                            if(count == mTCOneBlankQuestions.size()) {
                                Log.v("size",String.valueOf(mTCOneBlankQuestions.size()));
                                count--;
                            }
                            count--;
                            Log.v("int", String.valueOf(count));
                            tcOne = mTCOneBlankQuestions.get(count);
                            Log.v("ans", tcOne.getAnswer());

                            radioGroup.clearCheck();
                            quesTextView.setText(tcOne.getQuestion());
                            radioButton1.setText(tcOne.getOptionA());
                            radioButton2.setText(tcOne.getOptionB());
                            radioButton3.setText(tcOne.getOptionC());
                            radioButton4.setText(tcOne.getOptionD());
                            radioButton5.setText(tcOne.getOptionE());
                            String answer;
                            answer = tcOne.getAnswer();
                            a1[0] = answer;
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
