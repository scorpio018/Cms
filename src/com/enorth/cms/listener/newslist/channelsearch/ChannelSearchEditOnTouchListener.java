package com.enorth.cms.listener.newslist.channelsearch;

import com.enorth.cms.view.news.ChannelSearchActivity;

import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class ChannelSearchEditOnTouchListener implements OnTouchListener {
	
	private ChannelSearchActivity activity;
	
	public ChannelSearchEditOnTouchListener(ChannelSearchActivity activity) {
		this.activity = activity;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		Drawable drawable = activity.searchChannelET.getCompoundDrawables()[2];
		if (drawable == null)
            return false;

        if (event.getAction() != MotionEvent.ACTION_UP)
            return false;

        int intrinsicWidth = drawable.getIntrinsicWidth();
        //触摸点位置判断
        if (event.getX() > activity.searchChannelET.getWidth() - activity.searchChannelET.getPaddingRight() - intrinsicWidth) {
        	activity.searchChannelET.setText("");
        }

        return false;
	}

}
