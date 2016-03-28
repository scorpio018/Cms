package com.enorth.cms.fragment.login;

import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.listener.login.ScanImageViewOnTouchListener;
import com.enorth.cms.utils.LayoutParamsUtil;
import com.enorth.cms.view.R;
import com.enorth.cms.view.login.ILoginView;
import com.enorth.cms.view.login.MipcaActivityCapture;
import com.enorth.cms.widget.view.ViewShadow;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class ScanFrag extends Fragment {
	
	private RelativeLayout layout;
	
	private ImageView scanIV;
	
	private ILoginView loginView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		layout = (RelativeLayout) inflater.inflate(R.layout.scan_frag, null);
		/*ViewShadow scanImageView = new ViewShadow(getContext(), R.drawable.ma);
		scanImageView.setImageResource(R.drawable.ma);
		RelativeLayout.LayoutParams params = LayoutParamsUtil.initRelaMatchLayout();
		params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
		layout.addView(scanImageView, params);*/
		initView();
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
	}
	
}
