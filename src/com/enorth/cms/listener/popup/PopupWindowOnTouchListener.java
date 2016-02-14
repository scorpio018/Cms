package com.enorth.cms.listener.popup;

import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.listener.CommonOnTouchListener;
import com.enorth.cms.utils.AnimUtil;
import com.enorth.cms.utils.SharedPreUtil;
import com.enorth.cms.view.R;
import com.enorth.cms.view.news.ChannelSearchActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PopupWindowOnTouchListener extends CommonOnTouchListener {
	
	private LinearLayout layout;
	
	private int curPosition;
	
	private ChannelSearchActivity activity;
	
	private String curChooseChannelName;
	
	private RelativeLayout chooseChannelItem;
	
	public PopupWindowOnTouchListener(ChannelSearchActivity activity, String curChooseChannelName, int curPosition, LinearLayout layout, int touchSlop) {
		super.touchSlop = touchSlop;
		this.activity = activity;
		this.curChooseChannelName = curChooseChannelName;
		this.curPosition = curPosition;
		this.layout = layout;
		init();
	}
	
	private TextView chooseChannelName;
	
	private String curChannelName;
	
	private ImageView checkedIV;
	
	private void init() {
		LayoutInflater inflater = LayoutInflater.from(activity);
		chooseChannelItem = (RelativeLayout) inflater.inflate(R.layout.choose_channel_popup, null);
		chooseChannelName = (TextView) chooseChannelItem.findViewById(R.id.chooseChannelName);
		curChannelName = activity.allChooseChannelName.get(curPosition);
		chooseChannelName.setText(curChannelName);
		checkedIV = (ImageView) chooseChannelItem.getChildAt(0);
		if (curChooseChannelName.equals(curChannelName)) {
			checkedIV.setVisibility(View.VISIBLE);
		} else {
			checkedIV.setVisibility(View.GONE);
		}
		changeColor(R.color.bottom_text_color_green, R.color.channel_popup_color);
		chooseChannelItem.setOnTouchListener(this);
		layout.addView(chooseChannelItem);
	}

	@Override
	public boolean isClickBackgroungColorChange() {
		return true;
	}

	@Override
	public void touchMove(View v) {
		
	}

	@Override
	public boolean onImgChangeBegin() {
		return true;
	}
	
	@Override
	public void onImgChangeDo() {
		int childCount = layout.getChildCount();
		for (int j = 0; j < childCount; j++) {
			if (curPosition == j) {
				if (!activity.curChooseChannelType.equals(curChannelName)) {
					checkedIV.setVisibility(View.VISIBLE);
					activity.curChooseChannelTV.setText(curChannelName);
					SharedPreUtil.put(activity, ParamConst.CUR_CHOOSE_CHANNEL_TYPE, curChannelName);
					activity.curChooseChannelType = curChannelName;
					AnimUtil.showRefreshFrame(activity);
					if (curChannelName.equals(ParamConst.MY_CHANNEL)) {
						try {
							activity.isFirstEnter = true;
							activity.getMyChannel();
							activity.presenter.getMyChannel(ParamConst.USER_ID, activity.myChannelHandler);
						} catch (Exception e) {
							activity.error(e);
						}
					} else if (curChannelName.equals(ParamConst.ALL_CHANNEL)) {
						try {
							activity.getAllChannel();
						} catch (Exception e) {
							activity.error(e);
						}
					}
				}
			} else {
				checkedIV.setVisibility(View.GONE);
			}
		}
	}
	
	@Override
	public void onImgChangeEnd() {
		activity.popupWindow.dismiss();
		activity.popupWindow = null;
	}

}
