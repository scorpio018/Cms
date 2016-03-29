package com.enorth.cms.view.login;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.enorth.cms.bean.login.ChannelBean;
import com.enorth.cms.bean.login.LoginBean;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.fragment.login.LoginFrag;
import com.enorth.cms.fragment.login.ScanFrag;
import com.enorth.cms.presenter.login.ILoginPresenter;
import com.enorth.cms.presenter.login.LoginPresenter;
import com.enorth.cms.utils.AnimUtil;
import com.enorth.cms.utils.BeanParamsUtil;
import com.enorth.cms.utils.JsonUtil;
import com.enorth.cms.utils.SharedPreUtil;
import com.enorth.cms.utils.StaticUtil;
import com.enorth.cms.utils.StringUtil;
import com.enorth.cms.view.BaseFragmentActivity;
import com.enorth.cms.view.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

public class LoginActivity extends BaseFragmentActivity implements ILoginView {
	/**
	 * 登录activity的总layout
	 */
//	private LinearLayout loginLayout;
	
	private FragmentManager fragmentManager;
	
	private FragmentTransaction fragmentTransaction;
	/**
	 * 扫描二维码
	 */
	private ScanFrag scanFrag;
	/**
	 * 登录模块
	 */
	private LoginFrag loginFrag;
	
	private ILoginPresenter presenter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();
		initData();
		initPresenter();
		initFrag();
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
		case ParamConst.SCANNING_REQUEST_CODE:
			if(resultCode == RESULT_OK){
				Bundle bundle = data.getExtras();
				//显示扫描到的内容
//				mTextView.setText(bundle.getString("result"));
				Log.e("扫描信息", bundle.getString("result"));
				Toast.makeText(getActivity(), "扫描信息：" + bundle.getString("result"), Toast.LENGTH_SHORT).show();
				//显示
//				mImageView.setImageBitmap((Bitmap) data.getParcelableExtra("bitmap"));
			}
			break;
		}
    }
	
	private void initView() {
//		loginLayout = (LinearLayout) findViewById(R.id.loginLayout);
	}
	
	private void initData() {
		SharedPreUtil.put(this, ParamConst.IS_LOGIN, false);
	}
	
	private void initPresenter() {
		presenter = new LoginPresenter(this);
	}
	
	private void initFrag() {
		fragmentManager = getSupportFragmentManager();
		fragmentTransaction = fragmentManager.beginTransaction();
		initScanFrag();
		initLoginFrag();
		fragmentTransaction.commit();
	}
	
	private void initScanFrag() {
		scanFrag = new ScanFrag();
		fragmentTransaction.replace(R.id.scanFrag, scanFrag);
	}
	
	private void initLoginFrag() {
		loginFrag = new LoginFrag();
		fragmentTransaction.replace(R.id.loginFrag, loginFrag);
	}

	@Override
	public LoginActivity getActivity() {
		return this;
	}

	@Override
	public void login(String resultStr, LoginBean bean, Boolean rememberUser, Handler handler) throws Exception {
		JSONObject jo = JsonUtil.initJsonObject(resultStr);
		// 将用户信息进行刷新保存
		refreshUser(bean, jo, rememberUser);
		// 将新闻频道进行保存
		saveChannel(jo);
		// 表示已经登录
		SharedPreUtil.put(this, ParamConst.IS_LOGIN, true);
		Message msg = new Message();
		msg.what = ParamConst.MESSAGE_WHAT_SUCCESS;
		handler.sendMessage(msg);
	}
	
	/**
	 * 将当前登录的用户进行保存，并刷新当前缓存的用户组（最多5个，如果超出则将最早保存的用户剔除）
	 * @param bean
	 * @param jo
	 * @param rememberUser
	 * @throws Exception
	 */
	private void refreshUser(LoginBean bean, JSONObject jo, boolean rememberUser) throws Exception {
		// 将种子存入缓存中，用于每次请求接口的时候与接口进行随机码匹配
		bean.setSeed(JsonUtil.getString(jo, ParamConst.USER_LOGIN_SEED));
		// 获得登录成功的用户ID、用户名
		JSONObject user = JsonUtil.getJSONObject(jo, ParamConst.LOGIN_USER);
		bean.setUserId(JsonUtil.getInt(user, ParamConst.LOGIN_USER_ID));
		bean.setTrueName(JsonUtil.getString(user, ParamConst.LOGIN_TRUE_NAME));
		bean.setToken(JsonUtil.getString(jo, ParamConst.LOGIN_TOKEN));
		bean.setRememberUser(rememberUser);
		// 将当前用户的信息存入SharedPreference中
		saveCurLoginUser(bean);
		// 将当前用户的bean进行序列化
		String curUser = SharedPreUtil.serializeObject(bean);
		// 获得存到SharedPreference对象的用户组
		String[] usersStr = SharedPreUtil.getStringArray(this, ParamConst.REMEMBERED_USER, ParamConst.LOGIN_USER);
		boolean oldUsersContainsCurUser = false;
		int length = 0;
		for (String userStr : usersStr) {
			if (StringUtil.isNotEmpty(userStr)) {
				length++;
				LoginBean savedUser = (LoginBean) SharedPreUtil.deSerializeObject(userStr);
				// 如果存入缓存的用户中有当前登录的用户，则将用户信息进行更新
				if (savedUser.getUserId() == bean.getUserId()) {
					userStr = curUser;
					oldUsersContainsCurUser = true;
				}
			}
		}
		if (!oldUsersContainsCurUser) {
			// 用户最多纪录5个
			String[] newUsersStr = new String[5];
			int i = 0;
			int j = 0;
			// 如果纪录的用户数量已经到达5个，则将最先存储的用户删除
			if (length == 5) {
				i = 1;
			}
			for (; i < length; i++) {
				if (StringUtil.isNotEmpty(usersStr[i])) {
					newUsersStr[j++] = usersStr[i];
				}
			}
			newUsersStr[j] = curUser;
			usersStr = newUsersStr;
		}
		// 将全新的用户组存入对应的位置
		SharedPreUtil.put(this, ParamConst.REMEMBERED_USER, ParamConst.LOGIN_USER, usersStr);
	}
	
	/**
	 * 将返回的频道信息进行保存
	 * @param jo
	 * @throws Exception
	 */
	private void saveChannel(JSONObject jo) throws Exception {
		JSONArray channels = JsonUtil.getJSONArray(jo, ParamConst.CHANNEL);
		int length = channels.length();
		String[] channelsStr = new String[length];
		String[] channelNames = new String[length];
		List<ChannelBean> channelBeans = new ArrayList<ChannelBean>();
		for (int i = 0; i < length; i++) {
			JSONObject channel = JsonUtil.getJSONObject(channels, i);
			ChannelBean channelBean = (ChannelBean) BeanParamsUtil.saveJsonToObject(channel, ChannelBean.class);
			channelBeans.add(channelBean);
			if (length == (i + 1)) {
				BeanParamsUtil.saveObject(channelBean, this);
				StaticUtil.saveCurChannelBean(channelBean);
			}
			channelNames[i] = channelBean.getChannelName();
			String channelStr = SharedPreUtil.serializeObject(channelBean);
			channelsStr[i] = channelStr;
		}
		SharedPreUtil.put(this, ParamConst.CHANNEL, ParamConst.CHANNEL, channelsStr);
		SharedPreUtil.put(this, ParamConst.CHANNEL, ParamConst.CHANNEL_NAME_CONTENT, channelNames);
		// 将从根频道到当前登录用户对应的频道的bean的树型集合进行全局变量存入
		StaticUtil.saveChannelBeansTree(channelBeans);
		// 将从根频道到当前登录用户对应的频道的频道名对应的树型集合进行全局变量存入
		StaticUtil.saveChannelNamesTree(channelNames);
	}
	
	/**
	 * 将当前登录的用户的所有单个信息存入SharedPreference中
	 * @param bean
	 * @throws Exception 
	 */
	private void saveCurLoginUser(LoginBean bean) throws Exception {
		BeanParamsUtil.saveObject(bean, this);
		StaticUtil.saveCurLoginBean(bean);
	}

	@Override
	public ILoginPresenter getPresenter() {
		return presenter;
	}
	
}
