package com.example.hitayu.gref11.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hitayu.gref11.AppConstants;
import com.example.hitayu.gref11.MainApplication;
import com.example.hitayu.gref11.R;
import com.example.hitayu.gref11.PracticeWordsActivity;
import com.example.hitayu.gref11.db.DataSource;
import com.example.hitayu.gref11.model.Card;
import com.example.hitayu.gref11.model.CardSet;

public class AddCardFragment extends Fragment implements AppConstants {

	private CardSet mCardSet;
	private String mFrontPageWord;
	private String mBackPageWord;
	
	private View mView;
	private EditText mEditText;
	private TextView mTextViewTitle;
	
	private boolean mWordToggle;
	private boolean mIsSaved;
	
	private DataSource mDataSource;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		return inflater.inflate(R.layout.add_card_fragment, container, false);
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mDataSource = ((PracticeWordsActivity)getActivity()).getDataSource();
		
		mFrontPageWord = "";
		mBackPageWord = "";
		mWordToggle = false;
		mIsSaved = false;
		
		mEditText = (EditText)getActivity().findViewById(R.id.editTextAdd);
		
		mTextViewTitle = (TextView)getActivity().findViewById(R.id.textViewAddTitle);
		
		ImageButton imageButtonAddCancel = (ImageButton)getActivity().findViewById(R.id.imageButtonAddCancel);
		imageButtonAddCancel.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				((MainApplication)getActivity().getApplication()).doAction(ACTION_SHOW_CARD_SETS, Boolean.TRUE);
			}
		});
		
		ImageButton imageButtonAddSave = (ImageButton)getActivity().findViewById(R.id.imageButtonAddSave);
		imageButtonAddSave.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {

				if(!isValid(mEditText.getText().toString().trim())) {
					
					return;
				}
				
				if(mWordToggle) { // Back side
				
					mBackPageWord = mEditText.getText().toString().trim();
					
					if(null == mFrontPageWord || "".equals(mFrontPageWord)) {
						
						Toast.makeText(getActivity().getApplicationContext(), R.string.add_card_save_message_warning1, Toast.LENGTH_LONG).show();
						return;
					}
				}
				else { // Front side
					
					mFrontPageWord = mEditText.getText().toString().trim();
					
					if(null == mFrontPageWord || "".equals(mFrontPageWord)) {

						Toast.makeText(getActivity().getApplicationContext(), R.string.add_card_save_message_warning2, Toast.LENGTH_LONG).show();
						return;
					}
					else if(null == mBackPageWord || "".equals(mBackPageWord)) {
						
						Toast.makeText(getActivity().getApplicationContext(), R.string.add_card_save_message_back_page, Toast.LENGTH_SHORT).show();
						
						flipCard(mView);
						return;
					}
				}

				mIsSaved = addCard(mCardSet, mFrontPageWord, mBackPageWord);

				if(mIsSaved) {

					Toast.makeText(getActivity().getApplicationContext(), R.string.add_card_save_message_success, Toast.LENGTH_SHORT).show();
					((MainApplication)getActivity().getApplication()).doAction(ACTION_SHOW_ADD_CARD, mCardSet);
				}
				else {

					Toast.makeText(getActivity().getApplicationContext(), R.string.add_card_save_message_error, Toast.LENGTH_LONG).show();
				}
				
				mEditText.setText("");
			}
		});

		mView = getActivity().findViewById(R.id.relativeLayoutAdd);
		mView.setOnLongClickListener(new OnLongClickListener() {
			
			public boolean onLongClick(View v) {
				
				flipCard(v);

				return false;
			}
		});
	}
	
	@Override
	public void onResume() {
		super.onResume();
		((MainApplication)getActivity().getApplication()).doAction(ACTION_SET_HELP_CONTEXT, Integer.valueOf(HELP_CONTEXT_ADD_CARD));
	}
	
	public void setCardSet(CardSet cardSet) {
		
		this.mCardSet = cardSet;
	}
	
	private void flipCard(final View v) {
		
		final Animation flip1 = AnimationUtils.loadAnimation(v.getContext(), R.anim.flip1);
		final Animation flip2 = AnimationUtils.loadAnimation(v.getContext(), R.anim.flip2);
		flip1.setAnimationListener(new AnimationListener() {
			
			public void onAnimationStart(Animation animation) { /* Nothing to do here */ }
			
			public void onAnimationRepeat(Animation animation) { /* Nothing to do here */ }
			
			public void onAnimationEnd(Animation animation) {
				
				mWordToggle ^= true;

				if(mWordToggle) { // Back side
					
					// Save front side word
					mFrontPageWord = mEditText.getText().toString().trim();
					
					// Restore back side word
					if(null == mBackPageWord || "".equals(mBackPageWord)) {
						
						mEditText.setText("");
					}
					else {
						
						mEditText.setText(mBackPageWord);
					}
					
					mTextViewTitle.setText(R.string.add_card_back_title);
				}
				else { // Front side
					
					// Save back side word
					mBackPageWord = mEditText.getText().toString().trim();
					
					// Restore front side word
					if(null == mFrontPageWord || "".equals(mFrontPageWord)) {
						
						mEditText.setText("");
					}
					else {
						
						mEditText.setText(mFrontPageWord);
					}
					
					mTextViewTitle.setText(R.string.add_card_front_title);
				}
				
				v.startAnimation(flip2);
			}
		});
		
		v.startAnimation(flip1);
	}
	
	private boolean addCard(CardSet cardSet, String cardQuestion, String cardAnswer) {
		
		
		cardSet.setCardCount(cardSet.getCardCount() + 1);
		
		Card card = new Card();
		card.setQuestion(cardQuestion);
		card.setAnswer(cardAnswer);
		card.setCardSetId(cardSet.getId());
		card.setDisplayOrder(cardSet.getCardCount());
		
		try {
			
			mDataSource.createCard(card);
		}
		catch(Exception e) {
		
			cardSet.setCardCount(cardSet.getCardCount() - 1);
			return false;
		}
		
		mDataSource.updateCardSet(cardSet);
		
		return true;
	}
	
	private boolean isValid(String input) {
		
		if(null == input) {
			
			Toast.makeText(getActivity().getApplicationContext(), R.string.input_validation_warning, Toast.LENGTH_SHORT).show();
			return false;
		}
		
		return true;
	}
}
