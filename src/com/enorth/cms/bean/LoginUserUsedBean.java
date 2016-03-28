package com.enorth.cms.bean;

import java.util.List;

import com.enorth.cms.bean.login.ChannelBean;
import com.enorth.cms.bean.login.LoginBean;

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

}
