package com.enorth.cms.task;

import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.refreshlayout.CommonRefreshLayout;
import com.enorth.cms.refreshlayout.PullToRefreshLayout;

import android.os.AsyncTask;

/**
 * @author yangyang
 * 
 */
public class CommonAutoRefreshAndLoadTask extends AsyncTask<Integer, Float, String> {
	
	private CommonRefreshLayout layout;
	
	public CommonAutoRefreshAndLoadTask(CommonRefreshLayout layout) {
		this.layout = layout;
	}

	@Override
	protected String doInBackground(Integer... params) {
		while (layout.pullDownY < 4 / 3 * layout.refreshDist) {
			layout.pullDownY += layout.MOVE_SPEED;
			publishProgress(layout.pullDownY);
			try {
				Thread.sleep(params[0]);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		layout.changeState(ParamConst.REFRESHING);
		// 刷新操作
		if (layout.mListener != null)
			layout.mListener.onRefresh(layout);
		layout.hide();
	}

	@Override
	protected void onProgressUpdate(Float... values) {
		if (layout.pullDownY > layout.refreshDist)
			layout.changeState(ParamConst.RELEASE_TO_REFRESH);
		layout.requestLayout();
	}

}