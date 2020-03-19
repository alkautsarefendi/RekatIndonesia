package com.fortuna.rgpmobile.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

import com.fortuna.rgpmobile.R;

@SuppressLint("AppCompatCustomView")
public class CustomEditTextRoboto extends EditText {

    public CustomEditTextRoboto(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), getResources().getString(R.string.font_name)));
    }

}