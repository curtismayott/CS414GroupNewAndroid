package com.android.cs414groupnewandroid.controllers;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

/**
 * Created by darkbobo on 11/14/15.
 */
public class MyDialog extends Dialog {
	public MyDialog(Context context) {
		super(context);
	}

	public MyDialog(Context context, int themeResId) {
		super(context, themeResId);
	}

	protected MyDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	public void init(){
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	}
}
