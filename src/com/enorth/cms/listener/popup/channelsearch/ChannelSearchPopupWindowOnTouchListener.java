package com.enorth.cms.listener.popup.channelsearch;

import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.listener.popup.PopupWindowOnTouchListener;
import com.enorth.cms.utils.AnimUtil;
import com.enorth.cms.utils.SharedPreUtil;
import com.enorth.cms.view.news.ChannelSearchActivity;

import android.widget.LinearLayout;

public abstract class ChannelSearchPopupWindowOnTouchListener extends PopupWindowOnTouchListener {

	private ChannelSearchActivity activity;

	public ChannelSearchPopupWindowOnTouchListener(ChannelSearchActivity activity, LinearLayout layout) {
		super(activity, layout);
		this.activity = activity;
	}

	@Override
	public void checkItem(String curCheckedText) {
		activity.getCurChooseChannelTV().setText(curCheckedText);
		SharedPreUtil.put(activity, ParamConst.CUR_CHOOSE_CHANNEL_TYPE, curCheckedText);
		activity.setCurChooseChannelType(curCheckedText);
		AnimUtil.showRefreshFrame(activity, true);
		if (curCheckedText.equals(ParamConst.MY_CHANNEL)) {
			try {
				activity.isFirstEnter = true;
				activity.getMyChannel();
				activity.presenter.getMyChannel(ParamConst.USER_ID, activity.myChannelHandler);
			} catch (Exception e) {
				activity.error(e);
			}
		} else if (curCheckedText.equals(ParamConst.ALL_CHANNEL)) {
			try {
				activity.getAllChannel();
			} catch (Exception e) {
				activity.error(e);
			}
		}
	}
}