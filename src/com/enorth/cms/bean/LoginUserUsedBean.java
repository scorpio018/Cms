package com.enorth.cms.bean;

import java.util.List;

import com.enorth.cms.bean.login.ChannelBean;
import com.enorth.cms.bean.login.LoginBean;
import com.enorth.cms.bean.login.ScanBean;

public class LoginUserUsedBean {
	/**
	 * 当前登录的用户bean
	 */
	private LoginBean loginBean;
	/**
	 * 当前登录的用户对应的频道bean
	 */
	private ChannelBean curChannelBean;
	/**
	 * 从根频道到当前登录的用户对应的频道的树型集合
	 */
	private List<ChannelBean> channelBeansTree;
	/**
	 * 从根频道到当前登录的用户对应的频道的频道名的树型数组
	 */
	private String[] channelNamesTree;
	/**
	 * 当前用户使用的系统bean
	 */
	private ScanBean curScanBean;
	/**
	 * 当前扫描过的已保存的系统bean集合
	 */
	private List<ScanBean> scanBeans;
	/**
	 * 当前扫描过的已保存的系统名称
	 */
	private List<String> scanNames;

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public ChannelBean getCurChannelBean() {
		return curChannelBean;
	}

	public void setCurChannelBean(ChannelBean curChannelBean) {
		this.curChannelBean = curChannelBean;
	}

	public List<ChannelBean> getChannelBeansTree() {
		return channelBeansTree;
	}

	public void setChannelBeansTree(List<ChannelBean> channelBeansTree) {
		this.channelBeansTree = channelBeansTree;
	}

	public String[] getChannelNamesTree() {
		return channelNamesTree;
	}

	public void setChannelNamesTree(String[] channelNamesTree) {
		this.channelNamesTree = channelNamesTree;
	}

	public ScanBean getCurScanBean() {
		return curScanBean;
	}

	public void setCurScanBean(ScanBean curScanBean) {
		this.curScanBean = curScanBean;
	}

	public List<ScanBean> getScanBeans() {
		return scanBeans;
	}

	public void setScanBeans(List<ScanBean> scanBeans) {
		this.scanBeans = scanBeans;
	}

	public List<String> getScanNames() {
		return scanNames;
	}

	public void setScanNames(List<String> scanNames) {
		this.scanNames = scanNames;
	}

}
