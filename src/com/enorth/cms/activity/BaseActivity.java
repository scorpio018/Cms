package com.enorth.cms.activity;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.widget.Toast;

public abstract class BaseActivity extends Activity {
	/**
	 * 判断是否是点击两次返回键
	 */
	private boolean isQuit = false;
	
	public abstract void exitClick();
	
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
                Toast.makeText(getApplicationContext(), "再按一次退出程序",  
                        Toast.LENGTH_SHORT).show();  
                // 利用handler延迟发送更改状态信息  
                mHandler.sendEmptyMessageDelayed(0, 2000);  
            } else {  
                finish();  
                System.exit(0);  
            }  
        }  
        return false;  
    }
}
