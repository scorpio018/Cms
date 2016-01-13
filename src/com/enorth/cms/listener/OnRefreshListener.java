package com.enorth.cms.listener;

import com.enorth.cms.refreshlayout.PullToRefreshLayout;

/**
	 * 刷新加载回调接口
	 * 
	 * @author yangyang
	 * 
	 */
	public interface OnRefreshListener {
		/**
		 * 刷新操作
		 */
		void onRefresh(PullToRefreshLayout pullToRefreshLayout);

		/**
		 * 加载操作
		 */
		void onLoadMore(PullToRefreshLayout pullToRefreshLayout);
	}