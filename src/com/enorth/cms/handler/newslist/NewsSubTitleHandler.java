package com.enorth.cms.handler.newslist;

import org.json.JSONObject;

import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.handler.UrlRequestCommonHandler;
import com.enorth.cms.utils.SharedPreUtil;
import com.enorth.cms.view.R;
import com.enorth.cms.view.news.NewsCommonActivity;

import android.os.Message;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
/**
 * 将接口中获取的频道加载到新闻频道/图文直播的频道显示位置上
 * @author yangyang
 *
 */
public class NewsSubTitleHandler extends UrlRequestCommonHandler {
	
	private NewsCommonActivity activity;
	
	public NewsSubTitleHandler(NewsCommonActivity activity) {
		this.activity = activity;
	}

	@Override
	public void success(Message msg) throws Exception {
		JSONObject jo = (JSONObject) msg.obj;
		long channelId = jo.getLong("deptId");
		SharedPreUtil.put(activity, ParamConst.CUR_CHANNEL_ID, channelId);
		SharedPreUtil.put(activity, ParamConst.CUR_CHANNEL_ID_PARENT_ID, jo.getLong("parentId"));
		activity.newsSubTitleText = jo.getString("deptName");
		LinearLayout layout = (LinearLayout) activity.findViewById(R.id.newsSubTitleLineLayout);
		activity.newsSubTitleTV = (TextView) layout.findViewById(R.id.newsSubTitleText);
		activity.newsSubTitleTV.setText(activity.newsSubTitleText);
		SharedPreUtil.put(activity, ParamConst.CUR_CHANNEL_NAME, activity.newsSubTitleText);
		activity.isSubTitleInitFinish = true;
	}

	@Override
	public void noData(Message msg) throws Exception {
		
	}

	@Override
	public void error(Message msg) throws Exception {
		
	}

	@Override
	public void resultDefault(Message msg) throws Exception {
		
	}
	
}