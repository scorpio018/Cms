package com.enorth.cms.widget.imageview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class PreviewImageView extends ImageView {
	
	private int position;

	public PreviewImageView(Context context) {
		super(context);
	}
	
	public PreviewImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
	}
	
	public PreviewImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
	}
	
	public PreviewImageView(Context context, AttributeSet attrs, int defStyleAttr,
            int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
	}

}
