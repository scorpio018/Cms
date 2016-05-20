package com.enorth.cms.view.news;

import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.listener.CommonOnClickListener;
import com.enorth.cms.utils.ColorUtil;
import com.enorth.cms.view.R;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 普通新闻添加
 * 
 * @author yangyang
 *
 */
public class NewsAddActivity extends NewsEditCommonActivity {
	/**
	 * 送签
	 */
	private TextView songqianTV;
	/**
	 * 保存
	 */
	private TextView saveTV;
	/**
	 * 预览
	 */
	private TextView previewTV;
	/**
	 * 存退
	 */
	private TextView saveAndQuitTV;
	/**
	 * layout中包含普通新闻里面稿源
	 */
	private LinearLayout differentItem;
	/**
	 * 稿源
	 */
	private TextView manuscripts;
	/**
	 * 关键词layout
	 */
	private LinearLayout keywordItem;
	/**
	 * 关键词
	 */
	private TextView keywordTV;

	@Override
	public int setContentView() {
		return R.layout.activity_news_add;
	}

	@Override
	public void initTitleBtns() {
		RelativeLayout btnsLayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.edit_news_title_edit_btns,
				null);
		songqianTV = (TextView) btnsLayout.findViewById(R.id.songqianTV);
		saveTV = (TextView) btnsLayout.findViewById(R.id.saveTV);
		previewTV = (TextView) btnsLayout.findViewById(R.id.previewTV);
		saveAndQuitTV = (TextView) btnsLayout.findViewById(R.id.saveAndQuitTV);
		titleLayout.addView(btnsLayout);
	}

	@Override
	public void initTitleBtnsEvent() {
		// 送签点击事件
		new CommonOnClickListener(songqianTV, true, ColorUtil.getLightBlue(this)) {

			@Override
			public void onClick(View v) {
				Toast.makeText(NewsAddActivity.this, "点击了送签按钮", Toast.LENGTH_SHORT).show();
			}
		};

		// 保存点击事件
		new CommonOnClickListener(saveTV, true, ColorUtil.getLightBlue(this)) {

			@Override
			public void onClick(View v) {
				Toast.makeText(NewsAddActivity.this, "点击了保存按钮", Toast.LENGTH_SHORT).show();
			}
		};

		// 预览点击事件
		new CommonOnClickListener(previewTV, true, ColorUtil.getLightBlue(this)) {

			@Override
			public void onClick(View v) {
				Toast.makeText(NewsAddActivity.this, "点击了预览按钮", Toast.LENGTH_SHORT).show();
			}
		};
		
		// 存退点击事件
		new CommonOnClickListener(saveAndQuitTV, true, ColorUtil.getLightBlue(this)) {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(NewsAddActivity.this, "点击了存退按钮", Toast.LENGTH_SHORT).show();
			}
		};
	}

	@Override
	public void addDifferentLayout() {
		differentItem = (LinearLayout) getLayoutInflater().inflate(R.layout.normal_edit_news_diff_item, null);
		manuscripts = (TextView) differentItem.findViewById(R.id.manuscripts);
		differentLayout.addView(differentItem);
		manuscripts.setOnClickListener(new CommonOnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(NewsAddActivity.this, AddManuscriptsActivity.class);
				NewsAddActivity.this.startActivityForResult(intent, ParamConst.NEWS_ADD_ACTIVITY_TO_ADD_MANUSCRIPTS_ACTIVITY_REQUEST_CODE);
			}
		});
	}

	@Override
	public void addKeyword() {
		keywordItem = (LinearLayout) getLayoutInflater().inflate(R.layout.normal_edit_news_keyword, null);
		keywordTV = (TextView) keywordLayout.findViewById(R.id.keywordTV);
		keywordLayout.addView(keywordItem);
	}

	@Override
	public boolean hasEnclosure() {
		return true;
	}

}
