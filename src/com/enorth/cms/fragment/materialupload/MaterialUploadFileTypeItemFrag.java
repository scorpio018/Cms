package com.enorth.cms.fragment.materialupload;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.enorth.cms.adapter.materialupload.MaterialUploadFileItemGridViewAdapter;
import com.enorth.cms.adapter.materialupload.MaterialUploadFileTypeItemListViewAdapter;
import com.enorth.cms.bean.materialupload.MaterialUploadFileItemBean;
import com.enorth.cms.utils.ImgUtil;
import com.enorth.cms.utils.LayoutParamsUtil;
import com.enorth.cms.utils.ViewUtil;
import com.enorth.cms.view.R;
import com.enorth.cms.widget.gridview.materialupload.MaterialUploadHistoryGridView;
import com.enorth.cms.widget.linearlayout.MaterialUploadFragLinearLayout;
import com.enorth.cms.widget.listview.CommonListView;
import com.enorth.cms.widget.listview.materialupload.MaterialUploadHistoryItemListView;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.AbsListView.OnScrollListener;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MaterialUploadFileTypeItemFrag extends Fragment implements OnScrollListener {
	private LinearLayout layout;
	
	private LayoutInflater inflater;
	
	private MaterialUploadHistoryItemListView listView;
	
	private List<View> items = new ArrayList<View>();
	
	private MaterialUploadFragLinearLayout fragLayout;
	
	private View firstItem;
	
	private String[] imgUrl = {
		/*"https://i.ytimg.com/vi/ksme1nbbTAc/maxresdefault.jpg",
		"https://i.ytimg.com/vi/7E3wLAN3fQQ/maxresdefault.jpg",
		"https://i.ytimg.com/vi/QzLUKrB1rck/maxresdefault.jpg",
		"https://i.ytimg.com/vi/4J0lWAKDWeU/maxresdefault.jpg",
		"https://i.ytimg.com/vi/RqvDwNkNqn8/maxresdefault.jpg",
		"https://i.ytimg.com/vi/qrn1d-RiYOU/maxresdefault.jpg",
		"http://www.shuaige5.com/d/file/20150901/f6ffd8c478f5e41544957930036fb8f0.jpeg",
		"http://pic.3h3.com/up/2013-3/201332593256431530.jpg",
		"https://i.ytimg.com/vi/SZR4gfEkjQY/maxresdefault.jpg",
		"https://i.ytimg.com/vi/Ps-gZlnGZT4/maxresdefault.jpg",
		"https://i.ytimg.com/vi/kw_IaJfPaek/maxresdefault.jpg",
		"http://www.bz55.com/uploads/allimg/101225/15124945S-0.jpg",
		"http://www.wallcoo.com/nature/Summer_Fantasy_Landscapes/wallpapers/1680x1050/Summer_Fantasy_landscape_by_photo_manipulation_13255063.jpg",
		"http://news.mydrivers.com/Img/20120217/2012021709492293.jpg"*/
		"https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s160-c/A%252520Photographer.jpg",
		"https://lh4.googleusercontent.com/--dq8niRp7W4/URquVgmXvgI/AAAAAAAAAbs/-gnuLQfNnBA/s160-c/A%252520Song%252520of%252520Ice%252520and%252520Fire.jpg",
		"https://lh5.googleusercontent.com/-7qZeDtRKFKc/URquWZT1gOI/AAAAAAAAAbs/hqWgteyNXsg/s160-c/Another%252520Rockaway%252520Sunset.jpg",
		"https://lh3.googleusercontent.com/--L0Km39l5J8/URquXHGcdNI/AAAAAAAAAbs/3ZrSJNrSomQ/s160-c/Antelope%252520Butte.jpg",
		"https://lh6.googleusercontent.com/-8HO-4vIFnlw/URquZnsFgtI/AAAAAAAAAbs/WT8jViTF7vw/s160-c/Antelope%252520Hallway.jpg",
		"https://lh4.googleusercontent.com/-WIuWgVcU3Qw/URqubRVcj4I/AAAAAAAAAbs/YvbwgGjwdIQ/s160-c/Antelope%252520Walls.jpg",
		"https://lh6.googleusercontent.com/-UBmLbPELvoQ/URqucCdv0kI/AAAAAAAAAbs/IdNhr2VQoQs/s160-c/Apre%2525CC%252580s%252520la%252520Pluie.jpg",
		"https://lh3.googleusercontent.com/-s-AFpvgSeew/URquc6dF-JI/AAAAAAAAAbs/Mt3xNGRUd68/s160-c/Backlit%252520Cloud.jpg",
		"https://lh5.googleusercontent.com/-bvmif9a9YOQ/URquea3heHI/AAAAAAAAAbs/rcr6wyeQtAo/s160-c/Bee%252520and%252520Flower.jpg",
		"https://lh5.googleusercontent.com/-n7mdm7I7FGs/URqueT_BT-I/AAAAAAAAAbs/9MYmXlmpSAo/s160-c/Bonzai%252520Rock%252520Sunset.jpg",
		"https://lh6.googleusercontent.com/-4CN4X4t0M1k/URqufPozWzI/AAAAAAAAAbs/8wK41lg1KPs/s160-c/Caterpillar.jpg",
		"https://lh3.googleusercontent.com/-rrFnVC8xQEg/URqufdrLBaI/AAAAAAAAAbs/s69WYy_fl1E/s160-c/Chess.jpg",
		"https://lh5.googleusercontent.com/-WVpRptWH8Yw/URqugh-QmDI/AAAAAAAAAbs/E-MgBgtlUWU/s160-c/Chihuly.jpg",
		"https://lh5.googleusercontent.com/-0BDXkYmckbo/URquhKFW84I/AAAAAAAAAbs/ogQtHCTk2JQ/s160-c/Closed%252520Door.jpg",
		"https://lh3.googleusercontent.com/-PyggXXZRykM/URquh-kVvoI/AAAAAAAAAbs/hFtDwhtrHHQ/s160-c/Colorado%252520River%252520Sunset.jpg",
		"https://lh3.googleusercontent.com/-ZAs4dNZtALc/URquikvOCWI/AAAAAAAAAbs/DXz4h3dll1Y/s160-c/Colors%252520of%252520Autumn.jpg",
		"https://lh4.googleusercontent.com/-GztnWEIiMz8/URqukVCU7bI/AAAAAAAAAbs/jo2Hjv6MZ6M/s160-c/Countryside.jpg",
		"https://lh4.googleusercontent.com/-bEg9EZ9QoiM/URquklz3FGI/AAAAAAAAAbs/UUuv8Ac2BaE/s160-c/Death%252520Valley%252520-%252520Dunes.jpg",
		"https://lh6.googleusercontent.com/-ijQJ8W68tEE/URqulGkvFEI/AAAAAAAAAbs/zPXvIwi_rFw/s160-c/Delicate%252520Arch.jpg",
		"https://lh5.googleusercontent.com/-Oh8mMy2ieng/URqullDwehI/AAAAAAAAAbs/TbdeEfsaIZY/s160-c/Despair.jpg",
		"https://lh5.googleusercontent.com/-gl0y4UiAOlk/URqumC_KjBI/AAAAAAAAAbs/PM1eT7dn4oo/s160-c/Eagle%252520Fall%252520Sunrise.jpg",
		"https://lh3.googleusercontent.com/-hYYHd2_vXPQ/URqumtJa9eI/AAAAAAAAAbs/wAalXVkbSh0/s160-c/Electric%252520Storm.jpg",
		"https://lh5.googleusercontent.com/-PyY_yiyjPTo/URqunUOhHFI/AAAAAAAAAbs/azZoULNuJXc/s160-c/False%252520Kiva.jpg",
		"https://lh6.googleusercontent.com/-PYvLVdvXywk/URqunwd8hfI/AAAAAAAAAbs/qiMwgkFvf6I/s160-c/Fitzgerald%252520Streaks.jpg",
		"https://lh4.googleusercontent.com/-KIR_UobIIqY/URquoCZ9SlI/AAAAAAAAAbs/Y4d4q8sXu4c/s160-c/Foggy%252520Sunset.jpg",
		"https://lh6.googleusercontent.com/-9lzOk_OWZH0/URquoo4xYoI/AAAAAAAAAbs/AwgzHtNVCwU/s160-c/Frantic.jpg",
		"https://lh3.googleusercontent.com/-0X3JNaKaz48/URqupH78wpI/AAAAAAAAAbs/lHXxu_zbH8s/s160-c/Golden%252520Gate%252520Afternoon.jpg",
		"https://lh6.googleusercontent.com/-95sb5ag7ABc/URqupl95RDI/AAAAAAAAAbs/g73R20iVTRA/s160-c/Golden%252520Gate%252520Fog.jpg",
		"https://lh3.googleusercontent.com/-JB9v6rtgHhk/URqup21F-zI/AAAAAAAAAbs/64Fb8qMZWXk/s160-c/Golden%252520Grass.jpg",
		"https://lh4.googleusercontent.com/-EIBGfnuLtII/URquqVHwaRI/AAAAAAAAAbs/FA4McV2u8VE/s160-c/Grand%252520Teton.jpg",
		"https://lh4.googleusercontent.com/-WoMxZvmN9nY/URquq1v2AoI/AAAAAAAAAbs/grj5uMhL6NA/s160-c/Grass%252520Closeup.jpg",
		"https://lh3.googleusercontent.com/-6hZiEHXx64Q/URqurxvNdqI/AAAAAAAAAbs/kWMXM3o5OVI/s160-c/Green%252520Grass.jpg",
		"https://lh5.googleusercontent.com/-6LVb9OXtQ60/URquteBFuKI/AAAAAAAAAbs/4F4kRgecwFs/s160-c/Hanging%252520Leaf.jpg",
		"https://lh4.googleusercontent.com/-zAvf__52ONk/URqutT_IuxI/AAAAAAAAAbs/D_bcuc0thoU/s160-c/Highway%2525201.jpg",
		"https://lh6.googleusercontent.com/-H4SrUg615rA/URquuL27fXI/AAAAAAAAAbs/4aEqJfiMsOU/s160-c/Horseshoe%252520Bend%252520Sunset.jpg",
		"https://lh4.googleusercontent.com/-JhFi4fb_Pqw/URquuX-QXbI/AAAAAAAAAbs/IXpYUxuweYM/s160-c/Horseshoe%252520Bend.jpg",
		"https://lh5.googleusercontent.com/-UGgssvFRJ7g/URquueyJzGI/AAAAAAAAAbs/yYIBlLT0toM/s160-c/Into%252520the%252520Blue.jpg",
		"https://lh3.googleusercontent.com/-CH7KoupI7uI/URquu0FF__I/AAAAAAAAAbs/R7GDmI7v_G0/s160-c/Jelly%252520Fish%2525202.jpg",
		"https://lh4.googleusercontent.com/-pwuuw6yhg8U/URquvPxR3FI/AAAAAAAAAbs/VNGk6f-tsGE/s160-c/Jelly%252520Fish%2525203.jpg",
		"https://lh5.googleusercontent.com/-GoUQVw1fnFw/URquv6xbC0I/AAAAAAAAAbs/zEUVTQQ43Zc/s160-c/Kauai.jpg",
		"https://lh6.googleusercontent.com/-8QdYYQEpYjw/URquwvdh88I/AAAAAAAAAbs/cktDy-ysfHo/s160-c/Kyoto%252520Sunset.jpg",
		"https://lh4.googleusercontent.com/-vPeekyDjOE0/URquwzJ28qI/AAAAAAAAAbs/qxcyXULsZrg/s160-c/Lake%252520Tahoe%252520Colors.jpg",
		"https://lh4.googleusercontent.com/-xBPxWpD4yxU/URquxWHk8AI/AAAAAAAAAbs/ARDPeDYPiMY/s160-c/Lava%252520from%252520the%252520Sky.jpg",
		"https://lh3.googleusercontent.com/-897VXrJB6RE/URquxxxd-5I/AAAAAAAAAbs/j-Cz4T4YvIw/s160-c/Leica%25252050mm%252520Summilux.jpg",
		"https://lh5.googleusercontent.com/-qSJ4D4iXzGo/URquyDWiJ1I/AAAAAAAAAbs/k2pBXeWehOA/s160-c/Leica%25252050mm%252520Summilux.jpg",
		"https://lh6.googleusercontent.com/-dwlPg83vzLg/URquylTVuFI/AAAAAAAAAbs/G6SyQ8b4YsI/s160-c/Leica%252520M8%252520%252528Front%252529.jpg",
		"https://lh3.googleusercontent.com/-R3_EYAyJvfk/URquzQBv8eI/AAAAAAAAAbs/b9xhpUM3pEI/s160-c/Light%252520to%252520Sand.jpg",
		"https://lh3.googleusercontent.com/-fHY5h67QPi0/URqu0Cp4J1I/AAAAAAAAAbs/0lG6m94Z6vM/s160-c/Little%252520Bit%252520of%252520Paradise.jpg",
		"https://lh5.googleusercontent.com/-TzF_LwrCnRM/URqu0RddPOI/AAAAAAAAAbs/gaj2dLiuX0s/s160-c/Lone%252520Pine%252520Sunset.jpg",
		"https://lh3.googleusercontent.com/-4HdpJ4_DXU4/URqu046dJ9I/AAAAAAAAAbs/eBOodtk2_uk/s160-c/Lonely%252520Rock.jpg",
		"https://lh6.googleusercontent.com/-erbF--z-W4s/URqu1ajSLkI/AAAAAAAAAbs/xjDCDO1INzM/s160-c/Longue%252520Vue.jpg",
		"https://lh6.googleusercontent.com/-0CXJRdJaqvc/URqu1opNZNI/AAAAAAAAAbs/PFB2oPUU7Lk/s160-c/Look%252520Me%252520in%252520the%252520Eye.jpg",
		"https://lh3.googleusercontent.com/-D_5lNxnDN6g/URqu2Tk7HVI/AAAAAAAAAbs/p0ddca9W__Y/s160-c/Lost%252520in%252520a%252520Field.jpg",
		"https://lh6.googleusercontent.com/-flsqwMrIk2Q/URqu24PcmjI/AAAAAAAAAbs/5ocIH85XofM/s160-c/Marshall%252520Beach%252520Sunset.jpg",
		"https://lh4.googleusercontent.com/-Y4lgryEVTmU/URqu28kG3gI/AAAAAAAAAbs/OjXpekqtbJ4/s160-c/Mono%252520Lake%252520Blue.jpg",
		"https://lh4.googleusercontent.com/-AaHAJPmcGYA/URqu3PIldHI/AAAAAAAAAbs/lcTqk1SIcRs/s160-c/Monument%252520Valley%252520Overlook.jpg",
		"https://lh4.googleusercontent.com/-vKxfdQ83dQA/URqu31Yq_BI/AAAAAAAAAbs/OUoGk_2AyfM/s160-c/Moving%252520Rock.jpg",
		"https://lh5.googleusercontent.com/-CG62QiPpWXg/URqu4ia4vRI/AAAAAAAAAbs/0YOdqLAlcAc/s160-c/Napali%252520Coast.jpg",
		"https://lh6.googleusercontent.com/-wdGrP5PMmJQ/URqu5PZvn7I/AAAAAAAAAbs/m0abEcdPXe4/s160-c/One%252520Wheel.jpg",
		"https://lh6.googleusercontent.com/-6WS5DoCGuOA/URqu5qx1UgI/AAAAAAAAAbs/giMw2ixPvrY/s160-c/Open%252520Sky.jpg",
		"https://lh6.googleusercontent.com/-u8EHKj8G8GQ/URqu55sM6yI/AAAAAAAAAbs/lIXX_GlTdmI/s160-c/Orange%252520Sunset.jpg",
		"https://lh6.googleusercontent.com/-74Z5qj4bTDE/URqu6LSrJrI/AAAAAAAAAbs/XzmVkw90szQ/s160-c/Orchid.jpg",
		"https://lh6.googleusercontent.com/-lEQE4h6TePE/URqu6t_lSkI/AAAAAAAAAbs/zvGYKOea_qY/s160-c/Over%252520there.jpg",
		"https://lh5.googleusercontent.com/-cauH-53JH2M/URqu66v_USI/AAAAAAAAAbs/EucwwqclfKQ/s160-c/Plumes.jpg",
		"https://lh3.googleusercontent.com/-eDLT2jHDoy4/URqu7axzkAI/AAAAAAAAAbs/iVZE-xJ7lZs/s160-c/Rainbokeh.jpg",
		"https://lh5.googleusercontent.com/-j1NLqEFIyco/URqu8L1CGcI/AAAAAAAAAbs/aqZkgX66zlI/s160-c/Rainbow.jpg",
		"https://lh5.googleusercontent.com/-DRnqmK0t4VU/URqu8XYN9yI/AAAAAAAAAbs/LgvF_592WLU/s160-c/Rice%252520Fields.jpg",
		"https://lh3.googleusercontent.com/-hwh1v3EOGcQ/URqu8qOaKwI/AAAAAAAAAbs/IljRJRnbJGw/s160-c/Rockaway%252520Fire%252520Sky.jpg",
		"https://lh5.googleusercontent.com/-wjV6FQk7tlk/URqu9jCQ8sI/AAAAAAAAAbs/RyYUpdo-c9o/s160-c/Rockaway%252520Flow.jpg",
		"https://lh6.googleusercontent.com/-6cAXNfo7D20/URqu-BdzgPI/AAAAAAAAAbs/OmsYllzJqwo/s160-c/Rockaway%252520Sunset%252520Sky.jpg",
		"https://lh3.googleusercontent.com/-sl8fpGPS-RE/URqu_BOkfgI/AAAAAAAAAbs/Dg2Fv-JxOeg/s160-c/Russian%252520Ridge%252520Sunset.jpg",
		"https://lh6.googleusercontent.com/-gVtY36mMBIg/URqu_q91lkI/AAAAAAAAAbs/3CiFMBcy5MA/s160-c/Rust%252520Knot.jpg",
		"https://lh6.googleusercontent.com/-GHeImuHqJBE/URqu_FKfVLI/AAAAAAAAAbs/axuEJeqam7Q/s160-c/Sailing%252520Stones.jpg",
		"https://lh3.googleusercontent.com/-hBbYZjTOwGc/URqu_ycpIrI/AAAAAAAAAbs/nAdJUXnGJYE/s160-c/Seahorse.jpg",
		"https://lh3.googleusercontent.com/-Iwi6-i6IexY/URqvAYZHsVI/AAAAAAAAAbs/5ETWl4qXsFE/s160-c/Shinjuku%252520Street.jpg",
		"https://lh6.googleusercontent.com/-amhnySTM_MY/URqvAlb5KoI/AAAAAAAAAbs/pFCFgzlKsn0/s160-c/Sierra%252520Heavens.jpg",
		"https://lh5.googleusercontent.com/-dJgjepFrYSo/URqvBVJZrAI/AAAAAAAAAbs/v-F5QWpYO6s/s160-c/Sierra%252520Sunset.jpg",
		"https://lh4.googleusercontent.com/-Z4zGiC5nWdc/URqvBdEwivI/AAAAAAAAAbs/ZRZR1VJ84QA/s160-c/Sin%252520Lights.jpg",
		"https://lh4.googleusercontent.com/-_0cYiWW8ccY/URqvBz3iM4I/AAAAAAAAAbs/9N_Wq8MhLTY/s160-c/Starry%252520Lake.jpg",
		"https://lh3.googleusercontent.com/-A9LMoRyuQUA/URqvCYx_JoI/AAAAAAAAAbs/s7sde1Bz9cI/s160-c/Starry%252520Night.jpg",
		"https://lh3.googleusercontent.com/-KtLJ3k858eY/URqvC_2h_bI/AAAAAAAAAbs/zzEBImwDA_g/s160-c/Stream.jpg",
		"https://lh5.googleusercontent.com/-dFB7Lad6RcA/URqvDUftwWI/AAAAAAAAAbs/BrhoUtXTN7o/s160-c/Strip%252520Sunset.jpg",
		"https://lh5.googleusercontent.com/-at6apgFiN20/URqvDyffUZI/AAAAAAAAAbs/clABCx171bE/s160-c/Sunset%252520Hills.jpg",
		"https://lh4.googleusercontent.com/-7-EHhtQthII/URqvEYTk4vI/AAAAAAAAAbs/QSJZoB3YjVg/s160-c/Tenaya%252520Lake%2525202.jpg",
		"https://lh6.googleusercontent.com/-8MrjV_a-Pok/URqvFC5repI/AAAAAAAAAbs/9inKTg9fbCE/s160-c/Tenaya%252520Lake.jpg",
		"https://lh5.googleusercontent.com/-B1HW-z4zwao/URqvFWYRwUI/AAAAAAAAAbs/8Peli53Bs8I/s160-c/The%252520Cave%252520BW.jpg",
		"https://lh3.googleusercontent.com/-PO4E-xZKAnQ/URqvGRqjYkI/AAAAAAAAAbs/42nyADFsXag/s160-c/The%252520Fisherman.jpg",
		"https://lh4.googleusercontent.com/-iLyZlzfdy7s/URqvG0YScdI/AAAAAAAAAbs/1J9eDKmkXtk/s160-c/The%252520Night%252520is%252520Coming.jpg",
		"https://lh6.googleusercontent.com/-G-k7YkkUco0/URqvHhah6fI/AAAAAAAAAbs/_taQQG7t0vo/s160-c/The%252520Road.jpg",
		"https://lh6.googleusercontent.com/-h-ALJt7kSus/URqvIThqYfI/AAAAAAAAAbs/ejiv35olWS8/s160-c/Tokyo%252520Heights.jpg",
		"https://lh5.googleusercontent.com/-Hy9k-TbS7xg/URqvIjQMOxI/AAAAAAAAAbs/RSpmmOATSkg/s160-c/Tokyo%252520Highway.jpg",
		"https://lh6.googleusercontent.com/-83oOvMb4OZs/URqvJL0T7lI/AAAAAAAAAbs/c5TECZ6RONM/s160-c/Tokyo%252520Smog.jpg",
		"https://lh3.googleusercontent.com/-FB-jfgREEfI/URqvJI3EXAI/AAAAAAAAAbs/XfyweiRF4v8/s160-c/Tufa%252520at%252520Night.jpg",
		"https://lh4.googleusercontent.com/-vngKD5Z1U8w/URqvJUCEgPI/AAAAAAAAAbs/ulxCMVcU6EU/s160-c/Valley%252520Sunset.jpg",
		"https://lh6.googleusercontent.com/-DOz5I2E2oMQ/URqvKMND1kI/AAAAAAAAAbs/Iqf0IsInleo/s160-c/Windmill%252520Sunrise.jpg",
		"https://lh5.googleusercontent.com/-biyiyWcJ9MU/URqvKculiAI/AAAAAAAAAbs/jyPsCplJOpE/s160-c/Windmill.jpg",
		"https://lh4.googleusercontent.com/-PDT167_xRdA/URqvK36mLcI/AAAAAAAAAbs/oi2ik9QseMI/s160-c/Windmills.jpg",
		"https://lh5.googleusercontent.com/-kI_QdYx7VlU/URqvLXCB6gI/AAAAAAAAAbs/N31vlZ6u89o/s160-c/Yet%252520Another%252520Rockaway%252520Sunset.jpg",
		"https://lh4.googleusercontent.com/-e9NHZ5k5MSs/URqvMIBZjtI/AAAAAAAAAbs/1fV810rDNfQ/s160-c/Yosemite%252520Tree.jpg"
	};
	
	private boolean isShowOff = false;
	
	public MaterialUploadFileTypeItemFrag(MaterialUploadFragLinearLayout fragLayout) {
		this.fragLayout = fragLayout;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		layout = (LinearLayout) inflater.inflate(R.layout.material_upload_file_type_item_frag, null);
		long maxMemory = ImgUtil.getMaxMemory();
		Log.e("maxMemory", "【" + maxMemory + "】");
		this.inflater = inflater;
		initShowFileTime();
//		initMaterialUploadDetail();
		listView = (MaterialUploadHistoryItemListView) layout.findViewById(R.id.materialUploadFileItemListView);
//		listView = new MaterialUploadHistoryItemListView(getContext(), fragLayout);
		for (int i = 0; i < 5; i++) {
			if (isShowOff) {
				break;
			}
			initMaterialUploadDetail();
		}
		MaterialUploadFileTypeItemListViewAdapter adapter = new MaterialUploadFileTypeItemListViewAdapter(getContext(), 0, items);
		listView.setAdapter(adapter);
//		ViewUtil.setListViewHeightBasedOnChildren(listView);
//		adapter.notifyDataSetChanged();
		listView.setOnScrollListener(this);
		/*LayoutParams initMatchLayout = LayoutParamsUtil.initMatchLayout();
		layout.addView(listView, initMatchLayout);*/
		return layout;
	}
	
	/**
	 * 初始化文件显示的时间区间
	 * @param view
	 */
	private void initShowFileTime() {
		// 获取当前显示文件的时间的layout
		LinearLayout materialUploadFileTypeLayout = (LinearLayout) layout.findViewById(R.id.materialUploadFileTypeLayout);
		materialUploadFileTypeLayout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(MaterialUploadFileTypeItemFrag.this.getContext(), "点击了最近一天", Toast.LENGTH_SHORT).show();;
			}
		});
		// 获取当前显示文件的时间的文字说明
		TextView materialUploadFileTypeText = (TextView) layout.findViewById(R.id.materialUploadFileTypeText);
		materialUploadFileTypeText.setText("最近一天");
	}
	
	private Date now = new Date();
	
	private Random rd = new Random();
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private int count = 0;
	
	private int length = imgUrl.length;
	
	private void initMaterialUploadDetail() {
		LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.material_upload_file_history_item, null);
		TextView uploadTimeTV = (TextView) layout.findViewById(R.id.uploadTimeTV);
		uploadTimeTV.setText(sdf.format(now));
		now.setTime(now.getTime() - 60000);
		TextView uploadTitleTV = (TextView) layout.findViewById(R.id.uploadTitleTV);
		uploadTitleTV.setText("我上传的标题");
//		GridLayout fileGridLayout = (GridLayout) layout.findViewById(R.id.fileGridLayout);
		MaterialUploadHistoryGridView fileGridView = (MaterialUploadHistoryGridView) layout.findViewById(R.id.photo_wall);
//		List<View> gridViews = new ArrayList<View>();
		int n = rd.nextInt(2) + 9;
		if (length - count == 0) {
			return;
		}
		String[] imgUrl = new String[length - count > n ? n : length - count];
		for (int i = 0; i < n; i++) {
			if (length == (count + 1)) {
				break;
			}
			imgUrl[i] = this.imgUrl[count++];
		}
		MaterialUploadFileItemGridViewAdapter adapter = new MaterialUploadFileItemGridViewAdapter(getContext(), 0, imgUrl, fileGridView);
		fileGridView.setAdapter(adapter);
//		ViewUtil.setGridViewHeightBasedOnChildren(fileGridView, 4);
//		adapter.notifyDataSetChanged();
		if (items.size() == 0) {
			firstItem = layout;
		}
		items.add(layout);
	}
	
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		/*int top = firstItem.getTop();
		if (top < 0) {
			if (fragLayout.isOpen()) {
				fragLayout.startMove();
			}
		} else {
			if (!fragLayout.isOpen()) {
				fragLayout.startMove();
			}
		}*/
	}
	
}
