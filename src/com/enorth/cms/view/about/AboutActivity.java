package com.enorth.cms.view.about;

import java.util.ArrayList;
import java.util.List;

import com.enorth.cms.adapter.CommonListViewAdapter;
import com.enorth.cms.bean.about.AboutProjectBean;
import com.enorth.cms.utils.ColorUtil;
import com.enorth.cms.utils.ViewUtil;
import com.enorth.cms.view.BaseActivity;
import com.enorth.cms.view.R;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class AboutActivity extends BaseActivity implements IAboutView {
	
	private ListView aboutAddProjectListView;
	
	private List<AboutProjectBean> beans = new ArrayList<AboutProjectBean>();
	
	private List<View> items = new ArrayList<View>();
	
	private AboutProjectBean curCheckedBean;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtil.setContentViewForMenu(this, R.layout.activity_about);
		ViewUtil.initMenuTitle(this, getResources().getString(R.string.about_title_text));
		initView();
		initListView();
	}
	
	private void initView() {
		aboutAddProjectListView = (ListView) findViewById(R.id.aboutAddProjectListView);
	}
	
	private void initListView() {
		initItems();
		CommonListViewAdapter adapter = new CommonListViewAdapter(items);
		aboutAddProjectListView.setAdapter(adapter);
	}
	
	private void initItems() {
		initData();
		boolean isCheckedProject = false;
		for (AboutProjectBean bean : beans) {
			View item = getLayoutInflater().inflate(R.layout.about_add_project_item, null);
			TextView aboutProjectNameTV = (TextView) item.findViewById(R.id.aboutProjectNameTV);
			aboutProjectNameTV.setText(bean.getProjectName());
			TextView aboutProjectVersionTV = (TextView) item.findViewById(R.id.aboutProjectVersionTV);
			aboutProjectVersionTV.setText(bean.getProjectVersion());
			if (!isCheckedProject && curCheckedBean.getProjectName().equals(bean.getProjectName()) && curCheckedBean.getProjectVersion().equals(bean.getProjectVersion())) {
				aboutProjectNameTV.setTextColor(ColorUtil.getCommonBlueColor(this));
				aboutProjectVersionTV.setTextColor(ColorUtil.getCommonBlueColor(this));
				isCheckedProject = true;
			}
			items.add(item);
		}
		
	}
	
	private void initData() {
		AboutProjectBean bean1 = new AboutProjectBean();
		bean1.setProjectName("大数据系统");
		bean1.setProjectVersion("1.0.0.1");
		beans.add(bean1);
		AboutProjectBean bean2 = new AboutProjectBean();
		bean2.setProjectName("监控系统");
		bean2.setProjectVersion("1.0.0.1");
		beans.add(bean2);
		AboutProjectBean bean3 = new AboutProjectBean();
		bean3.setProjectName("内容发布系统系统");
		bean3.setProjectVersion("1.0.0.1");
		beans.add(bean3);
		AboutProjectBean bean4 = new AboutProjectBean();
		bean4.setProjectName("内容发布系统");
		bean4.setProjectVersion("1.0.0.1");
		beans.add(bean4);
		
		curCheckedBean = new AboutProjectBean();
		curCheckedBean.setProjectName("内容发布系统系统");
		curCheckedBean.setProjectVersion("1.0.0.1");
	}
}
