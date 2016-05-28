package com.enorth.cms.view.upload;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.enorth.cms.adapter.upload.ImageFolderAdapter;
import com.enorth.cms.adapter.upload.ImageGridItemContainCheckAdapter;
import com.enorth.cms.broadcast.CommonBroadcast;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.listener.CommonOnCheckedChangeListener;
import com.enorth.cms.listener.uploadpic.OnPhotoSelectedListener;
import com.enorth.cms.utils.ActivityJumpUtil;
import com.enorth.cms.utils.CameraUtil;
import com.enorth.cms.utils.GalleryUtil;
import com.enorth.cms.utils.ImgUtil;
import com.enorth.cms.utils.ScreenTools;
import com.enorth.cms.utils.StringUtil;
import com.enorth.cms.view.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class GalleryActivity extends Activity implements IGalleryView {
	/**
	 * 标头左侧按钮
	 */
	private TextView titleLeftTV;
	/**
	 * 标题
	 */
	private TextView titleMiddleTV;
	/**
	 * 标头右侧按钮
	 */
	private TextView titleRightTV;
	/**
	 * 图片列表
	 */
	private GridView photoGrid;
	/**
	 * 原图
	 */
	private RadioButton artworkPicRB;
	/**
	 * 中图
	 */
	private RadioButton middlePicRB;
	/**
	 * 小图
	 */
	private RadioButton smallPicRB;
	/**
	 * 底部预览按钮
	 */
	private TextView preview;
	/**
	 * 头部的标题
	 */
//	private TextView titleName;
	/**
	 * 取消按钮
	 */
//	private TextView quxiaoBtn;
	/**
	 * 头部的三角图标
	 */
//	private ImageView titleIcon;
	/**
	 * 头部的layout
	 */
//	private RelativeLayout headLayout;
	
	private TextView materialFromTypeTV;
	
	/**
	 * 相册的工具类
	 */
	private GalleryUtil galleryUtil;
	/**
	 * 图片文件类型的过滤器
	 */
//	private UploadImgFileNameFilter uploadImgFileNameFilter;
	/**
	 * 新拍的照片
	 */
//	private File newTakePhotoFile;
	
	private String path;
	/**
	 * 传入的已经选中的图片所对应的文件夹
	 */
	private String designationPath;
	
//	private String imagename;
	
	private ImageGridItemContainCheckAdapter gridItemAdapter;
	
	private PopupWindow popupWindow;
	
	private ImageFolderAdapter folderAdapter;
	
	private View dirview;
	
	private ListView dirListView;
	
	private String broadcaseAction;
	
	private CommonBroadcast commonBroadcast;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);
		ImageGridItemContainCheckAdapter.getmSelectedImage().clear();
		initBaseData();
		initTitle();
		initBottom();
		initView();
		getImg();
		initEvent();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case ParamConst.TAKE_CAMERA_PICTURE:
//			Toast.makeText(this, path + CameraUtil.getImagename(), 1).show();
			if (CameraUtil.getNewTakePhotoFile() != null) {
				ImgUtil.refreshGallery(CameraUtil.getNewTakePhotoFile(), this);
			}
			List<String> imgs = galleryUtil.getmImageFoldersMap().get(galleryUtil.getmImgDir().getAbsolutePath()).getImgs();
			imgs.add(0, path + "/" + CameraUtil.getImagename());
			galleryUtil.setmImgs(imgs);
			gridItemAdapter.changeData(galleryUtil.getmImgs()/*, galleryUtil.getmImgDir().getAbsolutePath()*/);
			break;
		case ParamConst.GALLERY_ACTIVITY_TO_UPLOAD_PIC_PREVIEW_ACTIVITY_REQUEST_CODE:
			if (resultCode == ParamConst.UPLOAD_PIC_PREVIEW_ACTIVITY_BACK_TO_GALLERY_ACTIVITY_RESULT_CODE) {
				Bundle bundle = data.getExtras();
				/*ArrayList<String> checkedImgDatas = bundle.getStringArrayList(ParamConst.CHECKED_IMG_DATAS);
				ImageGridItemContainCheckAdapter.setmSelectedImage(checkedImgDatas);*/
				gridItemAdapter.changeData(galleryUtil.getmImgs()/*, galleryUtil.getmImgDir().getAbsolutePath()*/);
				titleRightTV.setText("完成(" + ImageGridItemContainCheckAdapter.getmSelectedImage().size() + "张)");
				preview.setText("预览("+ImageGridItemContainCheckAdapter.getmSelectedImage().size() + "张)");
			}
			break;
		}
	}
	
	/**
	 * 初始化基本数据
	 */
	private void initBaseData() {
		Intent intent = getIntent();
		if (intent != null) {
			broadcaseAction = intent.getStringExtra(ParamConst.BROADCAST_ACTION);
			if (StringUtil.isNotEmpty(broadcaseAction)) {
				commonBroadcast = new CommonBroadcast(this, ParamConst.CLOSE_ACTIVITY);
			}
		}
		getCheckedImg();
	}
	
	private void getCheckedImg() {
		Intent intent = getIntent();
		ArrayList<String> imgDatas = intent.getStringArrayListExtra(ParamConst.CHECKED_IMG_DATAS);
		if (imgDatas != null) {
			ImageGridItemContainCheckAdapter.setmSelectedImage(imgDatas);
			designationPath = new File(imgDatas.get(0)).getParentFile().getAbsolutePath();
		}
	}
	
	private void initTitle() {
		initTitleLeft();
		initTitleMiddle();
		initTitleRight();
	}
	
	private void initBottom() {
		initRadioBtn();
		initPreview();
	}
	
	private void initTitleLeft() {
		titleLeftTV = (TextView) findViewById(R.id.titleLeftTV);
		titleLeftTV.setText("返回");
		titleLeftTV.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				commonBroadcast.close(GalleryActivity.this);
//				GalleryActivity.this.onBackPressed();
			}
		});
	}
	
	private void initTitleMiddle() {
		titleMiddleTV = (TextView) findViewById(R.id.titleMiddleTV);
	}
	
	private void initTitleRight() {
		titleRightTV = (TextView) findViewById(R.id.titleRightTV);
		titleRightTV.setText("完成(" + ImageGridItemContainCheckAdapter.getmSelectedImage().size() + "张)");
		titleRightTV.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (ImageGridItemContainCheckAdapter.getmSelectedImage().size() == 0) {
					Toast.makeText(GalleryActivity.this, "请先选择一张图片后再点击完成", Toast.LENGTH_SHORT).show();
					return;
				}
//				ActivityJumpUtil.sendImgDatasToActivity((ArrayList<String>) GridItemContainCheckAdapter.getmSelectedImage(), (ArrayList<String>) GridItemContainCheckAdapter.getmSelectedImage(), 0, GalleryActivity.this, UploadPicFinishCheckActivity.class);
				Intent intent = new Intent();
				// 用于表示当前进入相册进行操作时发送到广播中的action
				intent.putExtra(ParamConst.BROADCAST_ACTION, broadcaseAction);
				ActivityJumpUtil.sendTakePhotoToActivity((ArrayList<String>) ImageGridItemContainCheckAdapter.getmSelectedImage(), GalleryActivity.this, UploadPicFinishCheckActivity.class, ParamConst.ADD_PIC_IS_JUMP_TO_PREV_ACTIVITY_YES, intent);
			}
		});
	}
	
	private void initRadioBtn() {
		artworkPicRB = (RadioButton) findViewById(R.id.artworkPicRB);
		// 将原图选中
		artworkPicRB.setChecked(true);
		middlePicRB = (RadioButton) findViewById(R.id.middlePicRB);
		smallPicRB = (RadioButton) findViewById(R.id.smallPicRB);
	}
	
	private void initPreview() {
		preview = (TextView) findViewById(R.id.preview);
		preview.setText("预览("+ImageGridItemContainCheckAdapter.getmSelectedImage().size() + "张)");
	}
	
	/**
	 * 获取控件
	 * @throws Exception 
	 */
	private void initView() {
		photoGrid = (GridView) findViewById(R.id.commonGridView);
		materialFromTypeTV = (TextView) findViewById(R.id.materialFromTypeTV);
	}
	
	private void getImg() {
		galleryUtil = new GalleryUtil(this, designationPath) {
			
			@Override
			public void setDataView() {
				path = galleryUtil.getmImgDir().getAbsolutePath();
				if (getmImgDir() == null){
					Toast.makeText(getApplicationContext(), "一张图片没扫描到",Toast.LENGTH_SHORT).show();
					return;
				}

				if(getmImgDir().exists()){
					setmImgs(getmImageFoldersMap().get(galleryUtil.getmImgDir().getAbsolutePath()).getImgs());
				}
				
				gridItemAdapter = new ImageGridItemContainCheckAdapter(GalleryActivity.this, getmImgs(), broadcaseAction/*, getmImgDir().getAbsolutePath()*/);
				photoGrid.setAdapter(gridItemAdapter);
				gridItemAdapter.setOnPhotoSelectedListener(new OnPhotoSelectedListener() {
					@Override
					public void takePhoto() {
						CameraUtil.takePhoto(GalleryActivity.this, getmImgDir().getAbsolutePath());
					}
					@Override
					public void photoClick(List<String> number) {
						preview.setText("预览("+ImageGridItemContainCheckAdapter.getmSelectedImage().size() + "张)");
						titleRightTV.setText("完成("+ImageGridItemContainCheckAdapter.getmSelectedImage().size() + "张)");
					}
				});
				getFirstCheckedFolder().setSelected(true);
				materialFromTypeTV.setText(galleryUtil.getmImgDir().getName());
				titleMiddleTV.setText("共" + getmPicsSize() + "张");
			}
		};
	}
	
	/**
	 * 初始化展示文件夹的popupWindw
	 */
	private void initListDirPopupWindow(){
		if(popupWindow == null){
			dirview = LayoutInflater.from(this).inflate(R.layout.image_select_dirlist, null);
			dirListView = (ListView)dirview.findViewById(R.id.id_list_dirs);
			popupWindow = new PopupWindow(dirview, LayoutParams.MATCH_PARENT, ScreenTools.getPhoneHeight(this) * 3 / 5);
			folderAdapter = new ImageFolderAdapter(this, galleryUtil.getmImageFolders());
			dirListView.setAdapter(folderAdapter);
		}
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
		popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		// 显示的位置为:屏幕的宽度的一半-PopupWindow的高度的一半
		int layoutHeight = dirview.getMeasuredHeight();
		int bottomHeightPx = ScreenTools.dimenDip2px(R.dimen.material_upload_bottom_layout_height, this);
		int yoffInDip = ScreenTools.px2dip(bottomHeightPx, this) + layoutHeight;
		popupWindow.showAsDropDown(materialFromTypeTV, 0, -yoffInDip);
		dirListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				materialFromTypeTV.setText(galleryUtil.getmImageFolders().get(position).getName());
				titleMiddleTV.setText("共" + galleryUtil.getmImageFolderSize().get(position) + "张");
				// 改变将要显示的图片文件夹
				galleryUtil.setmImgDir(new File(galleryUtil.getmImageFolders().get(position).getDir()));
				// 获取改变的显示图片的文件夹
				galleryUtil.setmImgs(galleryUtil.getmImageFoldersMap().get(galleryUtil.getmImgDir().getAbsolutePath()).getImgs());
				// 将PopupWindow中ListView里的所有item的选中状态取消，并将当前选中的item进行选中
				int size = galleryUtil.getmImageFolders().size();
				for (int i = 0; i < size; i++) {
					galleryUtil.getmImageFolders().get(i).setSelected(false);
				}
				galleryUtil.getmImageFolders().get(position).setSelected(true);
				// 将PopupWindow弹出的ListView里面的数据进行更新（目前就是改变一下对勾的位置）
				folderAdapter.changeData(galleryUtil.getmImageFolders());
				// 将重新获取的图片和对应的文件夹的数据进行刷新
				gridItemAdapter.changeData(galleryUtil.getmImgs()/*, galleryUtil.getmImgDir().getAbsolutePath()*/);
				if(popupWindow!=null){
					popupWindow.dismiss();
				}
			}
		});

	}

	/**
	 * 监听事件
	 */
	private void initEvent(){
		initRadioBtnEvent();
		initMaterialFromTypeTVEvent();
		initPreviewEvent();
	}
	
	private void initRadioBtnEvent() {
//		artworkPicRB.setOnCheckedChangeListener();
		artworkPicRB.setOnCheckedChangeListener(new CommonOnCheckedChangeListener(this, artworkPicRB){});
		middlePicRB.setOnCheckedChangeListener(new CommonOnCheckedChangeListener(this, middlePicRB){});
		smallPicRB.setOnCheckedChangeListener(new CommonOnCheckedChangeListener(this, smallPicRB){});
	}
	
	/**
	 * 为底部的布局设置点击事件，弹出popupWindow
	 */
	private void initMaterialFromTypeTVEvent() {
		materialFromTypeTV.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				// 初始化展示文件夹的popupWindow
				initListDirPopupWindow();
			}
		});
	}
	
	private void initPreviewEvent() {
		preview.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (ImageGridItemContainCheckAdapter.getmSelectedImage().size() == 0) {
					Toast.makeText(GalleryActivity.this, "请先选择一张图片后再点击预览", Toast.LENGTH_SHORT).show();
					return;
				}
				Intent intent = new Intent();
				intent.putExtra(ParamConst.BROADCAST_ACTION, broadcaseAction);
//				Toast.makeText(GalleryActivity.this, GridItemAdapter.getmSelectedImage().toString(), 1).show();
				ActivityJumpUtil.sendImgDatasToActivity((ArrayList<String>) ImageGridItemContainCheckAdapter.getmSelectedImage(), (ArrayList<String>) ImageGridItemContainCheckAdapter.getmSelectedImage(), 0, GalleryActivity.this, UploadPicPreviewActivity.class, intent);
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		commonBroadcast.close(this);
//		super.onBackPressed();
	}
}
