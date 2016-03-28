package com.enorth.cms.view;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.widget.Toast;

public class BaseFragmentActivity extends FragmentActivity {
	/**
	 * 判断是否是点击两次返回键
	 */
	private boolean isQuit = false;

	protected Intent intent = new Intent();

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			isQuit = false;
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (!isQuit) {
				isQuit = true;
				Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
				// 利用handler延迟发送更改状态信息
				mHandler.sendEmptyMessageDelayed(0, 2000);
			} else {
				finish();
				System.exit(0);
				// intent.setClass(this, ExitActivity.class);
				// intent.putExtra(ExitActivity.EXIST, true);
				// startActivity(intent);
			}
		}
		return false;
	}
}
