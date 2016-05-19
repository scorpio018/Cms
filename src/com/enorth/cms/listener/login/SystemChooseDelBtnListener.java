package com.enorth.cms.listener.login;

import com.enorth.cms.bean.login.ScanBean;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.fragment.login.ScanFrag;
import com.enorth.cms.listener.popup.PopupWindowContainDelMarkOnClickListener;
import com.enorth.cms.listener.popup.PopupWindowContainDelMarkOnTouchListener;
import com.enorth.cms.utils.SharedPreUtil;
import com.enorth.cms.utils.StaticUtil;
import com.enorth.cms.utils.StringUtil;

import android.view.View;
import android.widget.LinearLayout;

public class SystemChooseDelBtnListener extends PopupWindowContainDelMarkOnClickListener {
	
	private ScanFrag scanFrag;
	
	public SystemChooseDelBtnListener(ScanFrag scanFrag, LinearLayout layout) {
		super(scanFrag.getContext(), layout);
		this.scanFrag = scanFrag;
		
	}

	@Override
	public void checkItem(String curCheckedText) {
		String[] scanBeans = SharedPreUtil.getStringArray(context, ParamConst.REMEMBERED_SCAN_INFO, ParamConst.SCAN);
		String[] newScanBeans = new String[scanBeans.length - 1];
		int i = 0;
		for (String newScanBean : scanBeans) {
			if (StringUtil.isNotEmpty(newScanBean)) {
				ScanBean scanBean = (ScanBean) SharedPreUtil.deSerializeObject(newScanBean);
				// 将不需要删除的用户存入新的数组中
				if (scanBean.getScanName().equals(curCheckedText)) {
					scanFrag.getScanNames().remove(curCheckedText);
				} else {
					newScanBeans[i++] = newScanBean;
				}
			}
		}
		if (newScanBeans.length == 0) {
			StaticUtil.removeCurScanBean(context);
			scanFrag.getSystemChooseTV().setVisibility(View.GONE);
		}
		SharedPreUtil.put(context, ParamConst.REMEMBERED_SCAN_INFO, ParamConst.SCAN, newScanBeans);
	}
}
