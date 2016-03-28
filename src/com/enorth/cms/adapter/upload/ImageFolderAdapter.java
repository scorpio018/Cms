package com.enorth.cms.adapter.upload;

import java.util.List;

import com.enorth.cms.bean.upload.ImageFolder;
import com.enorth.cms.enums.Type;
import com.enorth.cms.utils.ImageLoader;
import com.enorth.cms.view.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ImageFolderAdapter extends BaseAdapter{
	private Context context;
	private List<ImageFolder> list;
	
	public ImageFolderAdapter(Context context, List<ImageFolder> list) {
		super();
		this.context = context;
		this.list = list;
	}

	public void changeData(List<ImageFolder> list){
		this.list=list;
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public ImageFolder getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if(convertView==null){
			convertView=LayoutInflater.from(context).inflate(R.layout.list_dir_item, null);
			holder=new ViewHolder();
			holder.dirItemIcon=(ImageView)convertView.findViewById(R.id.id_dir_choose);
			holder.dirItemImage=(ImageView)convertView.findViewById(R.id.id_dir_item_image);
			holder.dirItemName=(TextView)convertView.findViewById(R.id.id_dir_item_name);
			holder.dirItemNum=(TextView)convertView.findViewById(R.id.id_dir_item_count);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		ImageLoader.getInstance(3,Type.LIFO).loadImageLocal(list.get(position).getFirstImagePath(), holder.dirItemImage);
		holder.dirItemName.setText(list.get(position).getName());
		holder.dirItemNum.setText(list.get(position).getCount()+"张");
		if(list.get(position).isSelected())
			holder.dirItemIcon.setVisibility(View.VISIBLE);
		else
			holder.dirItemIcon.setVisibility(View.INVISIBLE);
		return convertView;
	}

	class ViewHolder{
		ImageView dirItemImage;
		TextView dirItemName;
		TextView dirItemNum;
		ImageView dirItemIcon;
		RelativeLayout listdirLayout;
	}

}
