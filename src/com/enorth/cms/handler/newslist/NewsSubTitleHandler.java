package com.enorth.cms.handler.newslist;

import org.json.JSONObject;

import com.enorth.cms.bean.login.ChannelBean;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.handler.UrlRequestCommonHandler;
import com.enorth.cms.utils.BeanParamsUtil;
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
	public void success(Message msg) {
		ChannelBean bean = (ChannelBean) msg.obj;
		activity.setNewsSubTitleText(bean.getChannelName());
		LinearLayout layout = (LinearLayout) activity.findViewById(R.id.newsSubTitleLineLayout);
		activity.setNewsSubTitleTV((TextView) layout.findViewById(R.id.newsSubTitleText));
		activity.getNewsSubTitleTV().setText(activity.getNewsSubTitleText() + " ");
	}

	@Override
	public void noData(Message msg) {
		
	}

	@Override
	public void error(Message msg) {
		
	}

	@Override
	public void resultDefault(Message msg) {
		
	}
	
}
