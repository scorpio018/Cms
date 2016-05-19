package com.enorth.cms.fragment.login;

import java.util.List;

import com.enorth.cms.bean.ViewColorBasicBean;
import com.enorth.cms.bean.login.ScanBean;
import com.enorth.cms.common.EnableSimpleChangeTextView;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.listener.CommonOnClickListener;
import com.enorth.cms.listener.CommonOnTouchListener;
import com.enorth.cms.listener.login.ScanImageViewOnTouchListener;
import com.enorth.cms.listener.login.SystemChooseDelBtnListener;
import com.enorth.cms.listener.login.SystemChooseOnTouchListener;
import com.enorth.cms.listener.login.SystemChooseTextViewOnTouchListener;
import com.enorth.cms.listener.login.UserNameDelBtnListener;
import com.enorth.cms.listener.login.UserNameHistoryOnTouchListener;
import com.enorth.cms.utils.ColorUtil;
import com.enorth.cms.utils.LayoutParamsUtil;
import com.enorth.cms.utils.PopupWindowUtil;
import com.enorth.cms.utils.SharedPreUtil;
import com.enorth.cms.utils.StaticUtil;
import com.enorth.cms.view.R;
import com.enorth.cms.view.login.ILoginView;
import com.enorth.cms.view.login.MipcaActivityCapture;
import com.enorth.cms.widget.popupwindow.CommonPopupWindow;
import com.enorth.cms.widget.view.ViewShadow;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ScanFrag extends Fragment {
	
	private RelativeLayout layout;
	
	private ImageView scanIV;
	
	private TextView hintTV;
	
	private EnableSimpleChangeTextView systemChooseTV;
	
	private ViewColorBasicBean colorBasicBean;
	
	private ILoginView loginView;
	
	private PopupWindowUtil popupWindowUtil;
	
	private CommonPopupWindow popupWindow;
	
	private List<ScanBean> scanBeans;
	
	private List<String> scanNames;
	
	private ScanBean curScanBeans;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		layout = (RelativeLayout) inflater.inflate(R.layout.scan_frag, null);
		/*ViewShadow scanImageView = new ViewShadow(getContext(), R.drawable.ma);
		scanImageView.setImageResource(R.drawable.ma);
		RelativeLayout.LayoutParams params = LayoutParamsUtil.initRelaMatchLayout();
		params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
		layout.addView(scanImageView, params);*/
		initView();
		initData();
		initEvent();
		return layout;
	}
	
	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		loginView = (ILoginView) context;
	}
	
	private void initView() {
		scanIV = (ImageView) layout.findViewById(R.id.scanIV);
		hintTV = (TextView) layout.findViewById(R.id.hintTV);
		systemChooseTV = (EnableSimpleChangeTextView) layout.findViewById(R.id.systemChooseTV);
		// 重置背景色和文字颜色
		initColorBasicBean();
	}
	
	private void initColorBasicBean() {
		colorBasicBean = new ViewColorBasicBean(getContext());
		colorBasicBean.setmBgNormalColor(ColorUtil.getWhiteColor(getContext()));
		colorBasicBean.setmTextNormalColor(ColorUtil.getGray(getContext()));
		systemChooseTV.setColorBasicBean(colorBasicBean);
	}
	
	private void initData() {
		// 初始化曾经扫描过的发布系统的信息
		scanBeans = StaticUtil.getScanBeans(getContext());
		curScanBeans = StaticUtil.getCurScanBean(getContext());
		if (scanBeans == null || scanBeans.size() == 0) {
			systemChooseTV.setVisibility(View.GONE);
		} else {
			scanNames = StaticUtil.getScanNames(getContext());
			systemChooseTV.setVisibility(View.VISIBLE);
			systemChooseTV.setText(curScanBeans.getScanName());
		}
	}
	
	private void initEvent() {
		scanIV.setOnTouchListener(new ScanImageViewOnTouchListener(loginView.getActivity()) {
			
			@Override
			public void onImgChangeEnd(View v) {
				Intent intent = new Intent();
				intent.setClass(loginView.getActivity(), MipcaActivityCapture.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				loginView.getActivity().startActivityForResult(intent, ParamConst.SCANNING_REQUEST_CODE);
			}
		});
		/*systemChooseTV.setOnClickListener(new CommonOnClickListener() {
			
			@Override
			public void onClick(View v) {
				initPopupWindow();
			}
		});*/
		new CommonOnClickListener(systemChooseTV, false, 0) {
			
			@Override
			public void onClick(View v) {
				initPopupWindow();
			}
		};
	}
	
	private void initPopupWindow() {
		if (popupWindowUtil == null) {
			popupWindowUtil = new PopupWindowUtil(getContext(), systemChooseTV) {
				
				@Override
				public void initItems(LinearLayout layout) {
					SystemChooseTextViewOnTouchListener itemListener = new SystemChooseTextViewOnTouchListener(ScanFrag.this, layout) {
						
						@Override
						public void onImgChangeEnd(View v) {
							popupWindow.dismiss();
							popupWindow = null;
						}
					};
					SystemChooseDelBtnListener delBtnListener = new SystemChooseDelBtnListener(ScanFrag.this, layout);
					initPopupWindowItemsContainDelMark(layout, itemListener, delBtnListener, scanNames);
				}
			};
		}
		popupWindowUtil.setWidth(systemChooseTV.getMeasuredWidth());
		popupWindowUtil.setPopupBgColor(ColorUtil.getWhiteColor(getContext()));
		popupWindowUtil.setPopupBgTouchColor(ColorUtil.getBgGrayPress(getContext()));
		popupWindowUtil.setTextColor(ColorUtil.getBlack(getContext()));
		popupWindow = popupWindowUtil.initPopupWindow();
	}

	public EnableSimpleChangeTextView getSystemChooseTV() {
		return systemChooseTV;
	}

	public void setSystemChooseTV(EnableSimpleChangeTextView systemChooseTV) {
		this.systemChooseTV = systemChooseTV;
	}

	public List<ScanBean> getScanBeans() {
		return scanBeans;
	}

	public void setScanBeans(List<ScanBean> scanBeans) {
		this.scanBeans = scanBeans;
	}

	public List<String> getScanNames() {
		return scanNames;
	}

	public void setScanNames(List<String> scanNames) {
		this.scanNames = scanNames;
	}
	
}
