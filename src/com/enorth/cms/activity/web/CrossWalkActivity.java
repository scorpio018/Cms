package com.enorth.cms.activity.web;

import org.xwalk.core.XWalkView;

import com.enorth.cms.activity.R;
import com.enorth.cms.activity.R.id;
import com.enorth.cms.activity.R.layout;
import com.enorth.cms.client.CommonXWalkResourceClient;
import com.enorth.cms.js.JsInterface;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class CrossWalkActivity extends Activity {
	
	private XWalkView xWalkView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crosswalk_basic);
		xWalkView = (XWalkView) findViewById(R.id.activity_x_walk_view);
		xWalkView.load("http://10.0.70.71:9000/html5-demo", null);
		xWalkView.setResourceClient(new CommonXWalkResourceClient(xWalkView));
//		xWalkView.load("javascript:document.body.contentEditable=true;", null);
//		xWalkView.evaluateJavascript(script, callback);
		//绑定
		xWalkView.addJavascriptInterface(new JsInterface(), "NativeInterface");
		
//		CrossWalkRefreshLayout layout = ((CrossWalkRefreshLayout) findViewById(R.id.refresh_cross_walk));
//		layout.commonLayout.setOnRefreshListener(new MyCrossWalkListener(xWalkView));
//		xWalkView = (XWalkView) findViewById(R.id.content_cross_walk);
//		xWalkView.setResourceClient(new CommonXWalkResourceClient(xWalkView, layout));
//		xWalkView.load("http://blog.csdn.net/zhongkejingwang", null);
		
	}
	
	@Override
    protected void onPause() {
        super.onPause();
        if (xWalkView != null) {
            xWalkView.pauseTimers();
            xWalkView.onHide();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (xWalkView != null) {
            xWalkView.resumeTimers();
            xWalkView.onShow();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (xWalkView != null) {
            xWalkView.onDestroy();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (xWalkView != null) {
            xWalkView.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (xWalkView != null) {
            xWalkView.onNewIntent(intent);
        }
    }
}