package com.enorth.cms.view.upload;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.enorth.cms.adapter.upload.ImageGridItemContainCheckAdapter;
import com.enorth.cms.adapter.upload.VideoGridItemContainCheckAdapter;
import com.enorth.cms.bean.upload.VideoInfo;
import com.enorth.cms.broadcast.CommonClosedBroadcast;
import com.enorth.cms.utils.AnimUtil;
import com.enorth.cms.utils.VideoUtil;
import com.enorth.cms.view.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class VideoActivity extends Activity implements IVideoView{
	/**
	 * 顶部菜单返回按钮
	 */
	private TextView titleBackTV;
	
	private TextView titleMiddleTV;
	/**
	 * 顶部菜单完成按钮
	 */
	private TextView titleCompleteTV;
	/**
	 * 视频的gridView
	 */
	private GridView videoGrid;
	/**
	 * 获取手机中的所有视频的工具类
	 */
	private VideoUtil videoUtil;
	/**
	 * 视频的GridView的适配器
	 */
	private VideoGridItemContainCheckAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video);
		initView();
		initData();
		initEvent();
	}
	
	private void initView() {
		titleBackTV = (TextView) findViewById(R.id.titleLeftTV);
		titleMiddleTV = (TextView) findViewById(R.id.titleMiddleTV);
		titleCompleteTV = (TextView) findViewById(R.id.titleRightTV);
		videoGrid = (GridView) findViewById(R.id.commonGridView);
	}
	
	private void initData() {
		initViewText();
		initVideoData();
	}
	
	private void initViewText() {
		titleBackTV.setText("返回");
		titleCompleteTV.setText("完成");
	}
	
	private void initVideoData() {
		videoUtil = new VideoUtil(this) {
			
			@Override
			public void setDataView() {
//				List<VideoInfo> videoInfos = getVideoInfos();
				List<String> thumbnailDatas = getThumbnailDatas();
				if (thumbnailDatas.size() == 0) {
					Toast.makeText(VideoActivity.this, "一段视频都没有找到", Toast.LENGTH_SHORT).show();
					return;
				}
				List<VideoInfo> videoInfos = getVideoInfos();
				adapter = new VideoGridItemContainCheckAdapter(VideoActivity.this, thumbnailDatas, videoInfos);
				videoGrid.setAdapter(adapter);
				titleMiddleTV.setText("共(" + videoUtil.getThumbnailDatas().size() + ")条视频");
			}
		};
	}
	
	private void initEvent() {
		initBackEvent();
		initCompleteEvent();
	}
	
	private void initBackEvent() {
		titleBackTV.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				VideoActivity.this.onBackPressed();
			}
		});
	}
	
	private void initCompleteEvent() {
		titleCompleteTV.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (VideoGridItemContainCheckAdapter.getmSelectedImage().size() == 0) {
					Toast.makeText(VideoActivity.this, "请先选择一条视频后再点击完成", Toast.LENGTH_SHORT).show();
					return;
				}
				AnimUtil.showRefreshFrame(VideoActivity.this, true, "正在上传视频");
				new Handler() {
					@Override
					public void handleMessage(Message msg) {
						AnimUtil.hideRefreshFrame();
						VideoActivity.this.finish();
					};
				}.sendMessageDelayed(new Message(), 2000);
			}
		});
	}

}
