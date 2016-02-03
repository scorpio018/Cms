package com.enorth.cms.bean;

import com.enorth.cms.activity.R;
import com.enorth.cms.consts.ParamConst;

public abstract class BottomMenuOperateDataBasicBean {
	/**
	 * 待编辑时底部菜单选中时的图片样式（按照修改、批注、送签、删除的顺序排列）
	 */
	protected int[] newsOperateBtnChecked1;
	/**
	 * 待签发时底部菜单选中时的图片样式（按照修改、批注、签发、退回的顺序排列）
	 */
	protected int[] newsOperateBtnChecked2;
	/**
	 * 已签发时底部菜单选中时的图片样式（按照修改、批注、撤稿的顺序排列）
	 */
	protected int[] newsOperateBtnChecked3;
	/**
	 * 底部菜单选中时的图片样式
	 */
	private int[][] newsOperateBtnChecked;
	/**
	 * 待编辑时底部菜单禁止点击时的图片样式（按照修改、批注、送签、删除的顺序排列）
	 */
	protected int[] newsOperateBtnDisabled1;
	/**
	 * 待签发时底部菜单禁止点击时的图片样式（按照修改、批注、签发、退回的顺序排列）
	 */
	protected int[] newsOperateBtnDisabled2;
	/**
	 * 已签发时底部菜单禁止点击时的图片样式（按照修改、批注、撤稿的顺序排列）
	 */
	protected int[] newsOperateBtnDisabled3;
	/**
	 * 底部菜单禁止点击时的图片样式
	 */
	private int[][] newsOperateBtnDisabled;
	/**
	 * 底部菜单的文字描述
	 */
	protected String[] newsOperateBtnTextContent1;
	protected String[] newsOperateBtnTextContent2;
	protected String[] newsOperateBtnTextContent3;
	private String[][] newsOperateBtnTextContent;
	/**
	 * 底部菜单禁止点击时如果点击，所提示的信息
	 */
	protected String[] disableHint1;
	protected String[] disableHint2;
	protected String[] disableHint3;
	private String[][] disableHint;
	/**
	 * 底部菜单可以变为可以点击的条件
	 * 跟新闻列表的选中个数进行联动，有三种状态：
	 * 1.都没选中【ParamConst.CAN_ENABLE_STATE_DEFAULT】
	 * 2.只选中了一个【ParamConst.CAN_ENABLE_STATE_SIMPLE】
	 * 3.选中了至少一个【ParamConst.CAN_ENABLE_STATE_MORE】
	 */
	protected int[] newsOperateBtnCanEnableState1;
	protected int[] newsOperateBtnCanEnableState2;
	protected int[] newsOperateBtnCanEnableState3;
	private int[][] newsOperateBtnCanEnableState;
	/**
	 * 当底部菜单可以点击时，文字显示的颜色（按照修改、批注、送签、删除的顺序排列）
	 */
	protected int[] newsOperateBtnColor;
	
	public abstract void initData();
	
	public BottomMenuOperateDataBasicBean() {
		initData();
		// 将底部菜单的所有内容进行初始化
		newsOperateBtnChecked = new int[3][4];
		newsOperateBtnChecked[0] = newsOperateBtnChecked1;
		newsOperateBtnChecked[1] = newsOperateBtnChecked2;
		newsOperateBtnChecked[2] = newsOperateBtnChecked3;
		newsOperateBtnDisabled = new int[3][4];
		newsOperateBtnDisabled[0] = newsOperateBtnDisabled1;
		newsOperateBtnDisabled[1] = newsOperateBtnDisabled2;
		newsOperateBtnDisabled[2] = newsOperateBtnDisabled3;
		newsOperateBtnTextContent = new String[3][4];
		newsOperateBtnTextContent[0] = newsOperateBtnTextContent1;
		newsOperateBtnTextContent[1] = newsOperateBtnTextContent2;
		newsOperateBtnTextContent[2] = newsOperateBtnTextContent3;
		disableHint = new String[3][4];
		disableHint[0] = disableHint1;
		disableHint[1] = disableHint2;
		disableHint[2] = disableHint3;
		newsOperateBtnCanEnableState = new int[3][4];
		newsOperateBtnCanEnableState[0] = newsOperateBtnCanEnableState1;
		newsOperateBtnCanEnableState[1] = newsOperateBtnCanEnableState2;
		newsOperateBtnCanEnableState[2] = newsOperateBtnCanEnableState3;
	}


	public int[][] getNewsOperateBtnChecked() {
		return newsOperateBtnChecked;
	}
	
	public int[] getNewsOperateBtnChecked(int num) {
		return newsOperateBtnChecked[num];
	}


	public void setNewsOperateBtnChecked(int[][] newsOperateBtnChecked) {
		this.newsOperateBtnChecked = newsOperateBtnChecked;
	}


	public String[][] getNewsOperateBtnTextContent() {
		return newsOperateBtnTextContent;
	}

	public String[] getNewsOperateBtnTextContent(int num) {
		return newsOperateBtnTextContent[num];
	}

	public void setNewsOperateBtnTextContent(String[][] newsOperateBtnTextContent) {
		this.newsOperateBtnTextContent = newsOperateBtnTextContent;
	}


	public String[][] getDisableHint() {
		return disableHint;
	}
	
	public String[] getDisableHint(int num) {
		return disableHint[num];
	}


	public void setDisableHint(String[][] disableHint) {
		this.disableHint = disableHint;
	}


	public int[][] getNewsOperateBtnCanEnableState() {
		return newsOperateBtnCanEnableState;
	}
	
	public int[] getNewsOperateBtnCanEnableState(int num) {
		return newsOperateBtnCanEnableState[num];
	}


	public void setNewsOperateBtnCanEnableState(int[][] newsOperateBtnCanEnableState) {
		this.newsOperateBtnCanEnableState = newsOperateBtnCanEnableState;
	}


	public int[] getNewsOperateBtnColor() {
		return newsOperateBtnColor;
	}

	public void setNewsOperateBtnColor(int[] newsOperateBtnColor) {
		this.newsOperateBtnColor = newsOperateBtnColor;
	}

	public int[][] getNewsOperateBtnDisabled() {
		return newsOperateBtnDisabled;
	}
	
	public int[] getNewsOperateBtnDisabled(int num) {
		return newsOperateBtnDisabled[num];
	}

	public void setNewsOperateBtnDisabled(int[][] newsOperateBtnDisabled) {
		this.newsOperateBtnDisabled = newsOperateBtnDisabled;
	}
	
}
