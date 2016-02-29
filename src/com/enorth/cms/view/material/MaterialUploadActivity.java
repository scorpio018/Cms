package com.enorth.cms.view.material;

import java.util.ArrayList;
import java.util.List;

import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.fragment.materialupload.MaterialUploadBtnGroupFrag;
import com.enorth.cms.fragment.materialupload.MaterialUploadHistoryFrag;
import com.enorth.cms.handler.materialupload.MaterialUploadTypeChangeHandler;
import com.enorth.cms.listener.materialupload.MaterialUploadBottomOnClickListener;
import com.enorth.cms.listener.popup.channelsearch.ChannelSearchPopupWindowOnTouchListener;
import com.enorth.cms.listener.popup.materialupload.MaterialUploadPopupWindowOnTouchListener;
import com.enorth.cms.presenter.materialupload.IMaterialUploadPresenter;
import com.enorth.cms.presenter.materialupload.MaterialUploadPresenter;
import com.enorth.cms.utils.PopupWindowUtil;
import com.enorth.cms.utils.ScreenTools;
import com.enorth.cms.utils.SharedPreUtil;
import com.enorth.cms.utils.ViewUtil;
import com.enorth.cms.view.R;
import com.enorth.cms.view.news.ChannelSearchActivity;
import com.enorth.cms.widget.linearlayout.MaterialUploadFragLinearLayout;
import com.enorth.cms.widget.popupwindow.CommonPopupWindow;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MaterialUploadActivity extends FragmentActivity implements IMaterialUploadView {
	
	private FragmentManager fragmentManager;
	
	private FragmentTransaction fragmentTransaction;
	/**
	 * 用作收回/展开的自定义的linearlayout
	 */
	private MaterialUploadFragLinearLayout fragLayout;
	/**
	 * 按钮组的fragment
	 */
	private MaterialUploadBtnGroupFrag btnGroupFrag;
	/**
	 * 附件历史的fragment
	 */
	private MaterialUploadHistoryFrag historyFrag;
	/**
	 * 底部菜单（标识当前的附件内容来源类别）
	 */
	private TextView materialFromTypeTV;
	
	private PopupWindowUtil popupWindowUtil;
	
	private CommonPopupWindow popupWindow;
	/**
	 * 附件上传的类型（目前有手机上传、所有历史两种）
	 */
	private List<String> allMaterialUploadType;
	/**
	 * 当前选中的附件上传类型
	 */
	private String curMaterialUploadType;
	
	private IMaterialUploadPresenter presenter;
	/**
	 * 当前选中的文件类型（目前分为图片/视频两种）
	 */
	private String curFileType;
	
	private MaterialUploadTypeChangeHandler materialUploadTypeChangeHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtil.setContentViewForMenu(this, R.layout.activity_material_upload);
		try {
			initBasicData();
			initNewsTitle();
			initFrag();
			initBottom();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initBasicData() {
		presenter = new MaterialUploadPresenter(this);
		initHandler();
		initAllMaterialUploadType();
		curMaterialUploadType = SharedPreUtil.getString(this, ParamConst.CUR_MATERIAL_UPLOAD_TYPE);
		if (curMaterialUploadType.equals("")) {
			curMaterialUploadType = ParamConst.MATERIAL_UPLOAD_TYPE_FROM_PHONE;
		}
	}
	
	private void initHandler() {
		materialUploadTypeChangeHandler = new MaterialUploadTypeChangeHandler(this);
	}
	
	/**
	 * 初始化所有上传的类型（底部菜单处）
	 */
	private void initAllMaterialUploadType() {
		allMaterialUploadType = new ArrayList<String>();
		allMaterialUploadType.add(ParamConst.MATERIAL_UPLOAD_TYPE_FROM_PHONE);
		allMaterialUploadType.add(ParamConst.MATERIAL_UPLOAD_TYPE_FROM_ALL);
	}
	
	/**
	 * 初始化标头
	 */
	private void initNewsTitle() throws Exception {
		initMenuBtn();
		initTitleText();
		initTitleSearchBtn();
	}
	
	/**
	 * 初始化按钮组和附件内容
	 * @throws Exception
	 */
	private void initFrag() throws Exception {
		initFragLayout();
		fragmentManager = getSupportFragmentManager();
		fragmentTransaction = fragmentManager.beginTransaction();
		initBtnGroupFrag();
		initFileHistoryFrag();
		fragmentTransaction.commit();
		
	}
	
	private void initBottom() throws Exception {
		materialFromTypeTV = (TextView) findViewById(R.id.materialFromTypeTV);
		materialFromTypeTV.setText(curMaterialUploadType);
//		LinearLayout materialUploadBottomLayout = (LinearLayout) findViewById(R.id.materialUploadBottomLayout);
		MaterialUploadBottomOnClickListener listener = new MaterialUploadBottomOnClickListener(this);
		materialFromTypeTV.setOnClickListener(listener);
	}
	
	/**
	 * 实例化频道选择弹出页面。如果弹出框处于激活状态，则将弹出框销毁，反之则实例化弹出页面
	 * @param curChooseChannelName
	 */
	public void initPopupWindow() {
		if (popupWindowUtil == null) {
			popupWindowUtil = new PopupWindowUtil(this, materialFromTypeTV) {
				
				@Override
				public void initItems(LinearLayout layout) {
					MaterialUploadPopupWindowOnTouchListener listener = new MaterialUploadPopupWindowOnTouchListener(MaterialUploadActivity.this, layout) {
						@Override
						public void onImgChangeEnd(View v) {
							popupWindow.dismiss();
							popupWindow = null;
						}
					};
					initPopupWindowItems(layout, listener, allMaterialUploadType, curMaterialUploadType);
				}
			};
			int bottomHeightPx = ScreenTools.dimenDip2px(R.dimen.material_upload_bottom_layout_height, this);
			/*popupWindowUtil.setY(bottomHeightPx);
			popupWindowUtil.setGravity(Gravity.BOTTOM|Gravity.START);*/
			popupWindowUtil.setYoffInPixels(bottomHeightPx);
		}
		popupWindow = popupWindowUtil.initPopupWindow();
	}
	
	/**
	 * 初始化左上角的菜单按钮
	 * @throws Exception
	 */
	private void initMenuBtn() throws Exception {
		ImageView menuBtn = (ImageView) findViewById(R.id.titleLeftBtn);
		menuBtn.setBackgroundResource(R.drawable.news_menu);
		ViewUtil.initMenuEvent(this, menuBtn);
	}
	
	/**
	 * 初始化标题
	 * @throws Exception
	 */
	private void initTitleText() throws Exception {
		TextView materialUploadTitleText = (TextView) findViewById(R.id.titleText);
		materialUploadTitleText.setText(R.string.material_upload_title_text);
	}
	
	/**
	 * 初始化右上角的搜索按钮
	 * @throws Exception
	 */
	private void initTitleSearchBtn() throws Exception {
		ImageView materialEditBtn = (ImageView) findViewById(R.id.titleRightBtn);
//		materialEditBtn.setBackgroundResource(R.drawable.news_search);
	}
	
	private void initFragLayout() {
		fragLayout = (MaterialUploadFragLinearLayout) findViewById(R.id.fragLayout);
	}
	
	/**
	 * 初始化初始化按钮组fragment
	 * @throws Exception
	 */
	private void initBtnGroupFrag() throws Exception {
		btnGroupFrag = new MaterialUploadBtnGroupFrag(fragLayout);
		fragmentTransaction.replace(R.id.materialUploadBtnGroupFrag, btnGroupFrag);
	}
	
	/**
	 * 初始化附件内容fragment
	 * @throws Exception
	 */
	private void initFileHistoryFrag() throws Exception {
		historyFrag = new MaterialUploadHistoryFrag(fragLayout);
		fragmentTransaction.replace(R.id.materialUploadHistoryFrag, historyFrag);
	}
	
	@Override
	public void getItemsByFileTypeAndUploadType(String resultStr, Handler handler) {
		Message msg = new Message();
		msg.what = 0;
		msg.obj = resultStr;
		handler.sendMessage(msg);
	}
	
	@Override
	public String getCurCheckedFileType() {
		return curFileType;
	}

	@Override
	public void changeFileType(String fileType) {
		curFileType = fileType;
	}

	public MaterialUploadBtnGroupFrag getBtnGroupFrag() {
		return btnGroupFrag;
	}

	public void setBtnGroupFrag(MaterialUploadBtnGroupFrag btnGroupFrag) {
		this.btnGroupFrag = btnGroupFrag;
	}

	public MaterialUploadHistoryFrag getHistoryFrag() {
		return historyFrag;
	}

	public void setHistoryFrag(MaterialUploadHistoryFrag historyFrag) {
		this.historyFrag = historyFrag;
	}

	public List<String> getAllMaterialUploadType() {
		return allMaterialUploadType;
	}

	public void setAllMaterialUploadType(List<String> allMaterialUploadType) {
		this.allMaterialUploadType = allMaterialUploadType;
	}

	public String getCurMaterialUploadType() {
		return curMaterialUploadType;
	}

	public void setCurMaterialUploadType(String curMaterialUploadType) {
		this.curMaterialUploadType = curMaterialUploadType;
	}

	public TextView getMaterialFromTypeTV() {
		return materialFromTypeTV;
	}

	public void setMaterialFromTypeTV(TextView materialFromTypeTV) {
		this.materialFromTypeTV = materialFromTypeTV;
	}

	public IMaterialUploadPresenter getPresenter() {
		return presenter;
	}

	public void setPresenter(IMaterialUploadPresenter presenter) {
		this.presenter = presenter;
	}

	public String getCurFileType() {
		return curFileType;
	}

	public void setCurFileType(String curFileType) {
		this.curFileType = curFileType;
	}

	public MaterialUploadTypeChangeHandler getMaterialUploadTypeChangeHandler() {
		return materialUploadTypeChangeHandler;
	}

	public void setMaterialUploadTypeChangeHandler(MaterialUploadTypeChangeHandler materialUploadTypeChangeHandler) {
		this.materialUploadTypeChangeHandler = materialUploadTypeChangeHandler;
	}

}
