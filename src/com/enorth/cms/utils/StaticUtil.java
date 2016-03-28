package com.enorth.cms.utils;

import java.util.ArrayList;
import java.util.List;

import com.enorth.cms.bean.LoginUserUsedBean;
import com.enorth.cms.bean.login.ChannelBean;
import com.enorth.cms.bean.login.LoginBean;
import com.enorth.cms.consts.ParamConst;

import android.content.Context;

/**
 * 存储项目中经常会用到的数据bean
 * 
 * @author yangyang
 *
 */
public class StaticUtil {

	private static LoginUserUsedBean loginUserUsedBean;

	static {
		loginUserUsedBean = new LoginUserUsedBean();
	}

	/**
	 * 将当前登录的用户bean存入loginUserUsedBean中
	 * 
	 * @param loginBean
	 */
	public static void saveCurLoginBean(LoginBean loginBean) {
		loginUserUsedBean.setLoginBean(loginBean);
	}

	/**
	 * 将当前登录的用户对应的频道bean存入loginUserUsedBean中
	 * 
	 * @param channelBean
	 */
	public static void saveCurChannelBean(ChannelBean channelBean) {
		loginUserUsedBean.setCurChannelBean(channelBean);
	}
	
	/**
	 * 将从根频道到当前登录的用户对应的频道的树型集合存入loginUserUsedBean中
	 * @param channelBeans
	 */
	public static void saveChannelBeansTree(List<ChannelBean> channelBeans) {
		loginUserUsedBean.setChannelBeansTree(channelBeans);
	}
	
	/**
	 * 当前登录的用户对应的从根频道到当前登录的用户对应的频道的名称的树型数组存入loginUserUsedBean中
	 * @param channelNamesTree
	 */
	public static void saveChannelNamesTree(String[] channelNamesTree) {
		loginUserUsedBean.setChannelNamesTree(channelNamesTree);
	}

	/**
	 * 获取当前登录的用户对应的用户信息bean
	 * 
	 * @param context
	 * @return
	 */
	public static LoginBean getCurLoginBean(Context context) {
		if (loginUserUsedBean.getLoginBean() == null) {
			LoginBean loginBean = (LoginBean) BeanParamsUtil.getObject(LoginBean.class, context);
			loginUserUsedBean.setLoginBean(loginBean);
		}
		return loginUserUsedBean.getLoginBean();
	}

	/**
	 * 获取当前登录的用户对应的频道信息bean
	 * 
	 * @param context
	 * @return
	 */
	public static ChannelBean getCurChannelBean(Context context) {
		if (loginUserUsedBean.getCurChannelBean() == null) {
			ChannelBean channelBean = (ChannelBean) BeanParamsUtil.getObject(ChannelBean.class, context);
			loginUserUsedBean.setCurChannelBean(channelBean);
		}
		return loginUserUsedBean.getCurChannelBean();
	}
	
	/**
	 * 获取当前登录的用户对应的从根频道到当前登录的用户对应的频道的树型集合
	 * @param context
	 * @return
	 */
	public static List<ChannelBean> getChannelBeansTree(Context context) {
		if (loginUserUsedBean.getChannelBeansTree() == null) {
			String[] channelBeansStr = SharedPreUtil.getStringArray(context, ParamConst.CHANNEL, ParamConst.CHANNEL);
			List<ChannelBean> channelBeans = new ArrayList<ChannelBean>();
			for (String channelBeanStr : channelBeansStr) {
				ChannelBean channelBean = (ChannelBean) SharedPreUtil.deSerializeObject(channelBeanStr);
				channelBeans.add(channelBean);
			}
			loginUserUsedBean.setChannelBeansTree(channelBeans);
		}
		return loginUserUsedBean.getChannelBeansTree();
	}
	
	/**
	 * 获取当前登录的用户对应的从根频道到当前登录的用户对应的频道的名称的树型数组
	 * @param context
	 * @return
	 */
	public static String[] getChannelNamesTree(Context context) {
		if (loginUserUsedBean.getChannelNamesTree() == null) {
			String[] channelNamesStr = SharedPreUtil.getStringArray(context, ParamConst.CHANNEL, ParamConst.CHANNEL_NAME_CONTENT);
			loginUserUsedBean.setChannelNamesTree(channelNamesStr);
		}
		return loginUserUsedBean.getChannelNamesTree();
	}
}
