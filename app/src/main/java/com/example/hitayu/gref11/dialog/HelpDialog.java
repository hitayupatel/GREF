package com.example.hitayu.gref11.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.Html;
import android.widget.TextView;

import com.example.hitayu.gref11.R;

public class HelpDialog extends Dialog {

	private TextView mHelpTextView;
	
	public HelpDialog(Context context) {
		super(context);

		setContentView(R.layout.help_dialog);
		setTitle(R.string.help_dialog_title);
		setCanceledOnTouchOutside(true);
		
		 mHelpTextView = (TextView) findViewById(R.id.textViewHelp);
	}

	public void setHelp(String help) {
		
		mHelpTextView.setText(Html.fromHtml(help));
	}
}
