package com.enorth.cms.listener.materialupload;

import com.enorth.cms.adapter.materialupload.MaterialUploadFileItemGridViewAdapter;
import com.enorth.cms.listener.CommonOnScrollListener;

import android.widget.AbsListView;

public class MaterialUploadItemOnScrollListener extends CommonOnScrollListener {
	
	private MaterialUploadFileItemGridViewAdapter adapter;
	
	public MaterialUploadItemOnScrollListener(MaterialUploadFileItemGridViewAdapter adapter) {
		this.adapter = adapter;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// 仅当GridView静止时才去下载图片，GridView滑动时取消所有正在下载的任务
		if (scrollState == SCROLL_STATE_IDLE) {
			adapter.loadBitmaps(adapter.getmFirstVisibleItem(), adapter.getmVisibleItemCount());
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
			adapter.loadBitmaps(firstVisibleItem, visibleItemCount);
			adapter.setFirstEnter(false);
		}
	}

}
