package com.enorth.cms.fragment.materialupload;

import java.util.ArrayList;
import java.util.List;

import com.enorth.cms.adapter.materialupload.MaterialUploadFileTypeItemFragmentPagerAdapter;
import com.enorth.cms.utils.ColorUtil;
import com.enorth.cms.utils.LayoutParamsUtil;
import com.enorth.cms.view.R;
import com.enorth.cms.widget.linearlayout.MaterialUploadFragLinearLayout;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/**
 * 素材上传下面的历史信息（图片、视频历史信息）
 * @author yangyang
 *
 */
@SuppressLint("InflateParams")
public class MaterialUploadHistoryFrag extends Fragment {

	private LinearLayout layout;
	
	private int[] materialUploadFileTypeTexts = {R.string.materialUploadFileTypeTextPic, R.string.materialUploadFileTypeTextVideo};
	
	private List<TextView> materialUploadFileTypeTVs = new ArrayList<TextView>();
	
	private ViewPager materialUploadFileViewPager;
	
	private MaterialUploadFragLinearLayout fragLayout;
	
	public MaterialUploadHistoryFrag(MaterialUploadFragLinearLayout fragLayout) {
		this.fragLayout = fragLayout;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		layout = (LinearLayout) inflater.inflate(R.layout.material_upload_history_frag, null);
		initFileTypeEvent();
		initViewPager();
		return layout;
	}
	
	private int offset;
	
	private int currentPos = 0;
	
	private ImageView underLine;
	
	/**
	 * 初始化导航条中定义的文件类型
	 */
	private void initFileTypeEvent() {
		LinearLayout materialUploadFileTypeLayout = (LinearLayout) layout.findViewById(R.id.materialUploadFileTypeLayout);
		
		underLine = (ImageView) layout.findViewById(R.id.underline);
		underLine.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
			
			@Override
			public boolean onPreDraw() {
				// 获取点图片的宽度，点的移动动画时用
				offset = underLine.getWidth();
				return true;
			}
		});
		int height = (int) getResources().getDimension(R.dimen.material_upload_file_type_text_height);
		int length = materialUploadFileTypeTexts.length;
		for (int i = 0; i < length; i++) {
			final TextView tv = new TextView(this.getContext());
			tv.setText(materialUploadFileTypeTexts[i]);
			LayoutParams layoutParams = LayoutParamsUtil.initHeightAndPercentWeight(height, 0.5f);
			tv.setGravity(Gravity.CENTER);
			final int position = i;
			if (i == 0) {
				tv.setTextColor(ColorUtil.getCommonBlueColor(getActivity()));
			}
			tv.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					for (TextView textView : materialUploadFileTypeTVs) {
						textView.setTextColor(ColorUtil.getBlack(getActivity()));
					}
					tv.setTextColor(ColorUtil.getCommonBlueColor(getActivity()));
					moveCursorTo(position);
					materialUploadFileViewPager.setCurrentItem(position, false);
				}
			});
			materialUploadFileTypeLayout.addView(tv, layoutParams);
			materialUploadFileTypeTVs.add(tv);
		}
	}
	
	/**
	 * 滑动时 坐标点点移动动画
	 * 
	 * @param position
	 */
	private void moveCursorTo(int position) {
		AnimationSet animationSet = new AnimationSet(true);
		TranslateAnimation translateAnimation = new TranslateAnimation(offset
				* currentPos, offset * position, 0, 0);
		currentPos = position;
		animationSet.addAnimation(translateAnimation);
		animationSet.setDuration(300);
		animationSet.setFillAfter(true);
		underLine.startAnimation(animationSet);
	}
	
	private void initViewPager() {
		materialUploadFileViewPager = (ViewPager) layout.findViewById(R.id.materialUploadFileViewPager);
		List<MaterialUploadFileTypeItemFrag> items = new ArrayList<MaterialUploadFileTypeItemFrag>();
		int length = materialUploadFileTypeTexts.length;
		for (int i = 0; i < length; i++) {
			MaterialUploadFileTypeItemFrag frag = new MaterialUploadFileTypeItemFrag(fragLayout);
			items.add(frag);
		}
		MaterialUploadFileTypeItemFragmentPagerAdapter adapter = new MaterialUploadFileTypeItemFragmentPagerAdapter(getFragmentManager(), items);
		materialUploadFileViewPager.setAdapter(adapter);
	}
	
	private void addCurViewTouchEvent() {
		
	}
	
}
