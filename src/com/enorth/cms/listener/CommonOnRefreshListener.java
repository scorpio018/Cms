package com.enorth.cms.listener;

import com.enorth.cms.refreshlayout.CommonRefreshLayout;

/**
	 * 刷新加载回调接口
	 * 
	 * @author yangyang
	 * 
	 */
	public interface CommonOnRefreshListener {
		/**
		 * 刷新操作
		 */
		void onRefresh(final CommonRefreshLayout layout);

		/**
		 * 加载操作
		 */
		void onLoadMore(final CommonRefreshLayout layout);
	}