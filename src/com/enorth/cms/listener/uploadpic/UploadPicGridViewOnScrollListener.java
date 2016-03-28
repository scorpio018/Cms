package com.enorth.cms.listener.uploadpic;

import com.enorth.cms.adapter.uploadpic.UploadPicGridViewAdapter;
import com.enorth.cms.listener.CommonOnScrollListener;

import android.widget.AbsListView;

public class UploadPicGridViewOnScrollListener extends CommonOnScrollListener {
	
	private UploadPicGridViewAdapter adapter;
	
	public UploadPicGridViewOnScrollListener(UploadPicGridViewAdapter adapter) {
		this.adapter = adapter;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (scrollState == SCROLL_STATE_IDLE) {
			adapter.loadAllTasks(adapter.getmFirstVisibleItem(), adapter.getmVisibleItemCount());
		} else {
			adapter.cancelAllTasks();
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		adapter.setmFirstVisibleItem(firstVisibleItem);
		adapter.setmVisibleItemCount(visibleItemCount);
		// 下载的任务应该由onScrollStateChanged里调用，但首次进入程序时onScrollStateChanged并不会调用，
		// 因此在这里为首次进入程序开启下载任务。
		if (adapter.isFirstEnter() && visibleItemCount > 0) {
			adapter.loadAllTasks(firstVisibleItem, visibleItemCount);
			adapter.setFirstEnter(false);
		}
	}

}
