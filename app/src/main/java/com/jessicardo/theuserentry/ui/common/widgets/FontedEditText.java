package com.jessicardo.theuserentry.ui.common.widgets;

import com.jessicardo.theuserentry.R;
import com.jessicardo.theuserentry.util.FontType;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

public class FontedEditText extends AppCompatEditText {

    public FontedEditText(Context context) {
        super(context);
        init(null);
    }

    public FontedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public FontedEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        //Typeface.createFromAsset doesn't work in the layout editor.
        if (!isInEditMode()) {
            TypedArray styledAttrs = getContext()
                    .obtainStyledAttributes(attrs, R.styleable.FontedTextView);
            int fontIntValue = styledAttrs.getInt(R.styleable.FontedTextView_fontName,
                    FontType.ROBOTOSLAB_REGULAR.ordinal());
            styledAttrs.recycle();

            FontType font = FontType.values()[fontIntValue];
            setTypeface(font.getTypeface());
        }
    }
}

