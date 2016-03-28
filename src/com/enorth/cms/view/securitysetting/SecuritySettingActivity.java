package com.enorth.cms.view.securitysetting;

import java.util.ArrayList;
import java.util.List;

import com.enorth.cms.adapter.CommonListViewAdapter;
import com.enorth.cms.listener.securitysetting.SecuritySettingTagItemOnTouchListener;
import com.enorth.cms.utils.ColorUtil;
import com.enorth.cms.utils.ViewUtil;
import com.enorth.cms.view.BaseActivity;
import com.enorth.cms.view.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SecuritySettingActivity extends BaseActivity implements ISecuritySettingView {
	
	private ListView securitySettingListView;
	
	private List<View> items = new ArrayList<View>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtil.setContentViewForMenu(this, R.layout.activity_security_setting);
		initView();
		ViewUtil.initMenuTitle(this, R.string.security_setting_title_text);
		initItem();
		initListView();
		/*try {
			
		} catch (Exception e) {
			Log.e("SecuritySettingActivity.oncreate() error", e.toString());
			e.printStackTrace();
		}*/
	}
	
	private void initView() {
		securitySettingListView = (ListView) findViewById(R.id.securitySettingListView);
	}
	
	private void initItem() {
		initChangePwdItem();
	}
	
	private void initChangePwdItem() {
		View item = getLayoutInflater().inflate(R.layout.security_setting_tag_item, null);
		item.setBackgroundColor(ColorUtil.getBgGrayDefault(this));
		RelativeLayout securitySettingTagItemLayout = (RelativeLayout) item.findViewById(R.id.securitySettingTagItemLayout);
		securitySettingTagItemLayout.setOnTouchListener(new SecuritySettingTagItemOnTouchListener(this));
		TextView securitySettingTagItemText = (TextView) item.findViewById(R.id.securitySettingTagItemText);
		securitySettingTagItemText.setText("登录密码");
		items.add(item);
	}
	
	private void initListView() {
		ListAdapter adapter = new CommonListViewAdapter(items);
		securitySettingListView.setAdapter(adapter);
	}
}
