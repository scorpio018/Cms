package com.enorth.cms.view.about;

import java.util.ArrayList;
import java.util.List;

import com.enorth.cms.adapter.CommonListViewAdapter;
import com.enorth.cms.bean.about.AboutProjectBean;
import com.enorth.cms.bean.login.ScanBean;
import com.enorth.cms.utils.ColorUtil;
import com.enorth.cms.utils.StaticUtil;
import com.enorth.cms.utils.ViewUtil;
import com.enorth.cms.view.BaseActivity;
import com.enorth.cms.view.R;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class AboutActivity extends BaseActivity implements IAboutView {

	private TextView aboutVersionTV;
	
	private ListView aboutAddProjectListView;
	
//	private List<AboutProjectBean> beans = new ArrayList<AboutProjectBean>();
	
	private List<ScanBean> scanBeans;
	
	private ScanBean curScanBean;
	
	private List<View> items = new ArrayList<View>();
	
//	private AboutProjectBean curCheckedBean;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtil.setContentViewForMenu(this, R.layout.activity_about);
		ViewUtil.initMenuTitle(this, getResources().getString(R.string.about_title_text));
		initView();
		initData();
		initListView();
	}
	
	private void initView() {
		aboutVersionTV = (TextView) findViewById(R.id.aboutVersionTV);
		aboutAddProjectListView = (ListView) findViewById(R.id.aboutAddProjectListView);
	}
	
	private void initData() {
		scanBeans = StaticUtil.getScanBeans(this);
		curScanBean = StaticUtil.getCurScanBean(this);
		aboutVersionTV.setText(curScanBean.getScanName() + curScanBean.getScanVersion());
	}
	
	private void initListView() {
		initItems();
		CommonListViewAdapter adapter = new CommonListViewAdapter(items);
		aboutAddProjectListView.setAdapter(adapter);
	}
	
	private void initItems() {
		initData();
		boolean isCheckedProject = false;
		for (ScanBean bean : scanBeans) {
			View item = getLayoutInflater().inflate(R.layout.about_add_project_item, null);
			TextView aboutProjectNameTV = (TextView) item.findViewById(R.id.aboutProjectNameTV);
			aboutProjectNameTV.setText(bean.getScanName());
			TextView aboutProjectVersionTV = (TextView) item.findViewById(R.id.aboutProjectVersionTV);
			aboutProjectVersionTV.setText(bean.getScanVersion());
			if (!isCheckedProject && curScanBean.getScanName().equals(bean.getScanName()) && curScanBean.getScanVersion().equals(bean.getScanVersion())) {
				aboutProjectNameTV.setTextColor(ColorUtil.getCommonBlueColor(this));
				aboutProjectVersionTV.setTextColor(ColorUtil.getCommonBlueColor(this));
				isCheckedProject = true;
			}
			items.add(item);
		}
		
	}
	
}
