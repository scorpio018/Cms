package com.enorth.cms.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.enorth.cms.bean.LoginUserUsedBean;
import com.enorth.cms.bean.login.ChannelBean;
import com.enorth.cms.bean.login.LoginBean;
import com.enorth.cms.bean.login.ScanBean;
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
	 * 当前用户要向登录，必须要对应一个系统，此处存入的是曾经扫描过的系统的信息
	 * @param scanBeans
	 */
	public static void saveScanBeans(List<ScanBean> scanBeans, Context context) {
		List<String> scanNames = new ArrayList<String>();
		int size = scanBeans.size();
		String[] scanBeansStr = new String[size];
		int i = 0;
		for (ScanBean scanBean : scanBeans) {
			scanNames.add(scanBean.getScanName());
			scanBeansStr[i] = SharedPreUtil.serializeObject(scanBean);
			i++;
		}
		loginUserUsedBean.setScanBeans(scanBeans);
		loginUserUsedBean.setScanNames(scanNames);
		SharedPreUtil.put(context, ParamConst.REMEMBERED_SCAN_INFO, ParamConst.SCAN, scanBeansStr);
	}
	/**
	 * 将当前扫描出的系统信息存入集合中，并查重
	 * @param scanBean
	 */
	public static void saveScanBeans(ScanBean scanBean, Context context) {
		// 将当前选中的扫描信息存入bean中
		saveCurScanBean(scanBean, context);
		// 获取曾经保存的扫描信息集合
		List<ScanBean> scanBeans = getScanBeans(context);
		if (scanBeans.size() == 0) {
			// 如果没有记录，则将当前的扫描信息存入集合中
			scanBeans.add(scanBean);
		} else {
			// 如果有记录，则进行查重
			for (ScanBean bean : scanBeans) {
				if (bean.getScanId().equals(scanBean.getScanId())) {
					break;
				}
			}
			scanBeans.add(scanBean);
		}
		saveScanBeans(scanBeans, context);
	}
	/**
	 * 当前用户要向登录，必须要对应一个系统，此处存入的是当前对应的系统的信息
	 * @param curScanBean
	 */
	public static void saveCurScanBean(ScanBean curScanBean, Context context) {
		loginUserUsedBean.setCurScanBean(curScanBean);
		BeanParamsUtil.saveObject(curScanBean, context);
	}
	
	/**
	 * 将json格式的频道集合进行存入，其中包括curChannelBean、channelBeansTree、channelNamesTree
	 * @param channels
	 * @param context
	 */
	public static void saveChannel(JSONArray channels, Context context) {
		// 获取频道集合的长度进行遍历封装
		int length = channels.length();
		// 用作序列化频道集合
		String[] channelsStr = new String[length];
		String[] channelNames = new String[length];
		List<ChannelBean> channelBeans = new ArrayList<ChannelBean>();
		for (int i = 0; i < length; i++) {
			JSONObject channel = JsonUtil.getJSONObject(channels, i);
			ChannelBean channelBean = (ChannelBean) BeanParamsUtil.saveJsonToObject(channel, ChannelBean.class);
			channelBeans.add(channelBean);
			if (length == (i + 1)) {
				BeanParamsUtil.saveObject(channelBean, context);
				saveCurChannelBean(channelBean);
			}
			channelNames[i] = channelBean.getChannelName();
			String channelStr = SharedPreUtil.serializeObject(channelBean);
			channelsStr[i] = channelStr;
		}
		SharedPreUtil.put(context, ParamConst.CHANNEL, ParamConst.CHANNEL, channelsStr);
		SharedPreUtil.put(context, ParamConst.CHANNEL, ParamConst.CHANNEL_NAME_CONTENT, channelNames);
		// 将从根频道到当前登录用户对应的频道的bean的树型集合进行全局变量存入
		saveChannelBeansTree(channelBeans);
		// 将从根频道到当前登录用户对应的频道的频道名对应的树型集合进行全局变量存入
		saveChannelNamesTree(channelNames);
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
	
	/**
	 * 获取曾经扫描过的所有的系统的集合
	 * @param context
	 * @return
	 */
	public static List<ScanBean> getScanBeans(Context context) {
		if (loginUserUsedBean.getScanBeans() == null) {
			String[] scanStr = SharedPreUtil.getStringArray(context, ParamConst.REMEMBERED_SCAN_INFO, ParamConst.SCAN);
			List<ScanBean> scanBeans = new ArrayList<ScanBean>();
			for (String scan : scanStr) {
				if (StringUtil.isNotEmpty(scan)) {
					ScanBean scanBean = (ScanBean) SharedPreUtil.deSerializeObject(scan);
					scanBeans.add(scanBean);
				}
			}
			loginUserUsedBean.setScanBeans(scanBeans);
		}
		return loginUserUsedBean.getScanBeans();
	}
	
	/**
	 * 获取曾经扫描过的所有的系统名称
	 * @param context
	 * @return
	 */
	public static List<String> getScanNames(Context context) {
		if (loginUserUsedBean.getScanNames() == null) {
			List<ScanBean> scanBeans = getScanBeans(context);
			List<String> scanNames = new ArrayList<String>();
			for (ScanBean scanBean : scanBeans) {
				scanNames.add(scanBean.getScanName());
			}
			loginUserUsedBean.setScanNames(scanNames);
		}
		return loginUserUsedBean.getScanNames();
	}
	
	/**
	 * 获取当前要登录的系统
	 * @param context
	 * @return
	 */
	public static ScanBean getCurScanBean(Context context) {
		if (loginUserUsedBean.getCurScanBean() == null) {
			ScanBean scanBean = (ScanBean) BeanParamsUtil.getObject(ScanBean.class, context);
			loginUserUsedBean.setCurScanBean(scanBean);
		}
		return loginUserUsedBean.getCurScanBean();
	}
}
