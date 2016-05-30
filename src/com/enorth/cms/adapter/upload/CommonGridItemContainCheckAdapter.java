package com.enorth.cms.adapter.upload;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.BaseAdapter;

public abstract class CommonGridItemContainCheckAdapter extends BaseAdapter{
	/**
	 * view的种类个数（氛围TYPE_1、TYPE_2两种）
	 */
	final int VIEW_TYPE = 2;
	/**
	 * 照相
	 */
	final int TYPE_1 = 0;
	/**
	 * 图片
	 */
	final int TYPE_2 = 1;

	/**
	 * 文件夹路径
	 */
//	private String mDirPath;

	private Activity activity;
	/**
	 * 所有的图片
	 */
	protected List<String> mDatas = new ArrayList<String>();
	
	public CommonGridItemContainCheckAdapter(Activity activity, List<String> mDatas/*, String dirPath*/) {
		super();
		this.activity = activity;
		this.mDatas = mDatas;
//		this.mDirPath = dirPath;
	}

	public void changeData(List<String> mDatas) {
		this.mDatas = mDatas;
		notifyDataSetChanged();

	}

	@Override
	public String getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public List<String> getmDatas() {
		return mDatas;
	}

	public void setmDatas(List<String> mDatas) {
		this.mDatas = mDatas;
	}
}
