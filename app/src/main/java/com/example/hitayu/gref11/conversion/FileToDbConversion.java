package com.example.hitayu.gref11.conversion;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.hitayu.gref11.AppConstants;
import com.example.hitayu.gref11.MainApplication;
import com.example.hitayu.gref11.R;
import com.example.hitayu.gref11.db.DataSource;
import com.example.hitayu.gref11.model.Card;
import com.example.hitayu.gref11.model.CardSet;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileToDbConversion implements AppConstants {

	private Context mContext;
	private DataSource mDataSource;
	private ProgressDialog mDialog;

	public FileToDbConversion() { }

	public void convert(Context context, DataSource dataSource) {

		this.mContext = context;
		this.mDataSource = dataSource;
		
		mDialog = ProgressDialog.show(context, "", mContext.getString(R.string.conversion), true);

		new ConversionTask().execute();
	}

	class ConversionTask extends AsyncTask<Void, Void, List<CardSet>> {


		@Override
		protected List<CardSet> doInBackground(Void... params) {

			List<CardSet> cardSets = new ArrayList<CardSet>();
			
			// Get all the file names
			String[] fileNames = mContext.fileList();

			for(String fileName : fileNames) {

				CardSet cardSet = mDataSource.createCardSet(fileName);
				int displayOrder = 1;

				FileInputStream fis;
				BufferedReader reader = null;

				try {

					fis =  mContext.openFileInput(fileName);
					reader = new BufferedReader(new InputStreamReader(fis));
					String card;

					while((card = reader.readLine()) != null) {

						if(null != card && !"".equals(card) && 3 <= card.length()) {

							String[] words = card.split(":");

							if(words.length == 2) {

								Card newCard = new Card();
								newCard.setQuestion(words[0]);
								newCard.setAnswer(words[1]);
								newCard.setCardSetId(cardSet.getId());
								newCard.setDisplayOrder(displayOrder);
								displayOrder += 1;
								mDataSource.createCard(newCard);
							}
						}
					}
				}
				catch(FileNotFoundException e) {

					Log.w(AppConstants.LOG_TAG, "FileNotFoundException: while reading words from file", e);
				}
				catch(IOException e) {

					Log.w(AppConstants.LOG_TAG, "IOException: while reading words from file", e);
				}
				finally {

					try {

						if(null != reader) {

							reader.close();
						}
					}
					catch (IOException e) {

						Log.e(AppConstants.LOG_TAG, "IOException", e);
					}
				}

				cardSet.setCardCount(displayOrder - 1);
				mDataSource.updateCardSet(cardSet);
				cardSets.add(cardSet);
				mContext.deleteFile(fileName);
			}
			
			return cardSets;
		}
		
		@Override
		protected void onPostExecute(List<CardSet> cardSets) {
			
			MainApplication mainApplication = (MainApplication)((Activity)mContext).getApplication();
			
			for(CardSet cardSet : cardSets) {
				
				mainApplication.doAction(ACTION_ADD_CARD_SET, cardSet);
			}
			
			mDialog.cancel();
		}
	}
}