package com.enorth.cms.bean.news_list;

import com.enorth.cms.activity.R;
import com.enorth.cms.consts.ParamConst;

public class BottomMenuOperateDataBasicBean {
	/**
	 * 待编辑时底部菜单选中时的图片样式（按照修改、批注、送签、删除的顺序排列）
	 */
	private int[] newsOperateBtnChecked1 = {R.drawable.operate_btn_xiugai_checked, 
			R.drawable.operate_btn_pizhu_checked, R.drawable.operate_btn_songqian_checked,
			R.drawable.operate_btn_shanchu_checked};
	/**
	 * 待签发时底部菜单选中时的图片样式（按照修改、批注、签发、退回的顺序排列）
	 */
	private int[] newsOperateBtnChecked2 = {R.drawable.operate_btn_xiugai_checked, 
			R.drawable.operate_btn_pizhu_checked, R.drawable.operate_btn_songqian_checked, 
			R.drawable.operate_btn_tuihui_checked};
	/**
	 * 已签发时底部菜单选中时的图片样式（按照修改、批注、撤稿的顺序排列）
	 */
	private int[] newsOperateBtnChecked3 = {R.drawable.operate_btn_xiugai_checked, 
			R.drawable.operate_btn_pizhu_checked, 0, R.drawable.operate_btn_chegao_checked};
	/**
	 * 底部菜单选中时的图片样式
	 */
	private int[][] newsOperateBtnChecked;
	/**
	 * 待编辑时底部菜单禁止点击时的图片样式（按照修改、批注、送签、删除的顺序排列）
	 */
	private int[] newsOperateBtnDisabled1 = {R.drawable.operate_btn_xiugai_uncheck, 
			R.drawable.operate_btn_pizhu_uncheck, R.drawable.operate_btn_songqian_uncheck,
			R.drawable.operate_btn_shanchu_uncheck};
	/**
	 * 待签发时底部菜单禁止点击时的图片样式（按照修改、批注、签发、退回的顺序排列）
	 */
	private int[] newsOperateBtnDisabled2 = {R.drawable.operate_btn_xiugai_uncheck, 
			R.drawable.operate_btn_pizhu_uncheck, R.drawable.operate_btn_songqian_uncheck,
			R.drawable.operate_btn_tuihui_unchecked};
	/**
	 * 已签发时底部菜单禁止点击时的图片样式（按照修改、批注、撤稿的顺序排列）
	 */
	private int[] newsOperateBtnDisabled3 = {R.drawable.operate_btn_xiugai_uncheck, 
			R.drawable.operate_btn_pizhu_uncheck, 0, R.drawable.operate_btn_chegao_uncheck};
	/**
	 * 底部菜单禁止点击时的图片样式
	 */
	private int[][] newsOperateBtnDisabled;
	/**
	 * 底部菜单的文字描述
	 */
	private String[] newsOperateBtnTextContent1 = {"修改", "批注", "送签", "删除"};
	private String[] newsOperateBtnTextContent2 = {"修改", "批注", "签发", "退回"};
	private String[] newsOperateBtnTextContent3 = {"修改", "批注", "", "撤稿"};
	private String[][] newsOperateBtnTextContent;
	/**
	 * 底部菜单禁止点击时如果点击，所提示的信息
	 */
	private String[] disableHint1 = {"必须选择且仅选择一条待编辑新闻时才可以进行修改", "必须至少选择一条待编辑新闻才能进行批注", 
			"必须至少选择一条待编辑新闻才能进行送签", "必须至少选择一条待编辑新闻才能进行删除"};
	private String[] disableHint2 = {"必须选择且仅选择一条待签发新闻时才可以进行修改", "必须至少选择一条待签发新闻才能进行批注", 
			"必须至少选择一条待签发新闻才能进行签发", "必须至少选择一条待签发新闻才能进行退回"};
	private String[] disableHint3 = {"必须选择且仅选择一条已签发新闻时才可以进行修改", "必须至少选择一条已签发新闻才能进行批注", 
			"", "必须至少选择一条已签发新闻才能进行撤稿"};
	private String[][] disableHint;
	/**
	 * 底部菜单可以变为可以点击的条件
	 * 跟新闻列表的选中个数进行联动，有三种状态：
	 * 1.都没选中【ParamConst.CAN_ENABLE_STATE_DEFAULT】
	 * 2.只选中了一个【ParamConst.CAN_ENABLE_STATE_SIMPLE】
	 * 3.选中了至少一个【ParamConst.CAN_ENABLE_STATE_MORE】
	 */
	private int[] newsOperateBtnCanEnableState1 = {ParamConst.CAN_ENABLE_STATE_SIMPLE, ParamConst.CAN_ENABLE_STATE_SIMPLE, ParamConst.CAN_ENABLE_STATE_MORE, ParamConst.CAN_ENABLE_STATE_MORE};
	private int[] newsOperateBtnCanEnableState2 = {ParamConst.CAN_ENABLE_STATE_SIMPLE, ParamConst.CAN_ENABLE_STATE_SIMPLE, ParamConst.CAN_ENABLE_STATE_MORE, ParamConst.CAN_ENABLE_STATE_MORE};
	private int[] newsOperateBtnCanEnableState3 = {ParamConst.CAN_ENABLE_STATE_SIMPLE, ParamConst.CAN_ENABLE_STATE_SIMPLE, ParamConst.CAN_ENABLE_STATE_DEFAULT, ParamConst.CAN_ENABLE_STATE_MORE};
	private int[][] newsOperateBtnCanEnableState;
	/**
	 * 当底部菜单可以点击时，文字显示的颜色（按照修改、批注、送签、删除的顺序排列）
	 */
	private int[] newsOperateBtnColor = {R.color.bottom_text_color_blue, R.color.bottom_text_color_green, R.color.bottom_text_color_yellow, R.color.bottom_text_color_red};
	
	
	public BottomMenuOperateDataBasicBean() {
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
