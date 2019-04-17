package com.example.hitayu.gref11.model;

import android.util.Log;

import com.example.hitayu.gref11.AppConstants;

import org.json.JSONException;
import org.json.JSONObject;


public class CardSet implements Comparable<CardSet> {
	
	public static final int ADD_CARD_FRAGMENT = 1;
	public static final int CARDS_PAGER_FRAGMENT = 2;
	
	/*
	 * These KEYs are used to access the CardSet JSON data
	 */
	public static final String ID_KEY = "i";
	public static final String EXTERNAL_ID_KEY = "e";
	public static final String TITLE_KEY = "n";
	public static final String FRAGMENT_KEY = "f";
	public static final String CARD_COUNT_KEY = "c";
	
	private long mId;
	private String mTitle;
	private String mExternalId;
	private int mFragmentId;
	private int mCardCount = 0;
	
	public CardSet() { }
	
	public long getId() {
		return mId;
	}

	public void setId(long id) {
		this.mId = id;
	}

	public String getTitle() {
		return mTitle;
	}
	
	public void setTitle(String title) {
		this.mTitle = title;
	}
	
	public String getExternalId() {
		return mExternalId;
	}
	
	public void setExternalId(String externalId) {
		this.mExternalId = externalId;
	}

	public int getFragmentId() {
		return mFragmentId;
	}

	public void setFragmentId(int fragmentId) {
		this.mFragmentId = fragmentId;
	}
	
	public int getCardCount() {
		return mCardCount;
	}

	public void setCardCount(int cardCount) {
		this.mCardCount = cardCount;
	}

	public boolean isRemote() {

		if(null != mExternalId && !"".equals(mExternalId)) {
			
			return true;
		}
		else {
			
			return false;
		}
	}

	public boolean hasCards() {
		
		if(0 < mCardCount) {
			
			return true;
		}
		else {
			
			return false;
		}
	}
	
	@Override
	public String toString() {
		
		return mTitle;
	}

	public int compareTo(CardSet listItem) {
		
		return mTitle.compareTo(listItem.getTitle());
	}
	
	public JSONObject getJSON() {
		
		JSONObject json = new JSONObject();
		
		try {
		
			json.put(EXTERNAL_ID_KEY, mExternalId);
			json.put(TITLE_KEY, mTitle);
			json.put(FRAGMENT_KEY, mFragmentId);
			json.put(CARD_COUNT_KEY, mCardCount);
			
		}
		catch(JSONException e) {
			
			Log.e(AppConstants.LOG_TAG, "JSONException", e);
		}
		
		return json;
	}

	@Override
	public int hashCode() {
		
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mTitle == null) ? 0 : mTitle.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CardSet other = (CardSet) obj;
		if (mTitle == null) {
			if (other.mTitle != null)
				return false;
		} else if (!mTitle.equals(other.mTitle))
			return false;
		return true;
	}
}
