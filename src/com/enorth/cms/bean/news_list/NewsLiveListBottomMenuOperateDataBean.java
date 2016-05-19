package com.enorth.cms.bean.news_list;

import com.enorth.cms.bean.BottomMenuOperateDataBasicBean;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.view.R;
/**
 * 图文直播列表中的底部按键数据
 * @author Administrator
 *
 */
public class NewsLiveListBottomMenuOperateDataBean extends BottomMenuOperateDataBasicBean {
	public void initData() {
		/**
		 * 待编辑时底部菜单选中时的图片样式（按照修改、录入、批注、送签、删除的顺序排列）
		 */
		newsOperateBtnChecked1 = new int[] {
				R.drawable.operate_btn_xiugai_checked, 
				R.drawable.operate_btn_luru_checked,
				R.drawable.operate_btn_songqian_checked, 
				R.drawable.operate_btn_pizhu_checked,
				R.drawable.operate_btn_shanchu_checked };
		/**
		 * 待签发时底部菜单选中时的图片样式（按照修改、录入、批注、签发、退回的顺序排列）
		 */
		newsOperateBtnChecked2 = new int[]{
				R.drawable.operate_btn_xiugai_checked, 
				R.drawable.operate_btn_luru_checked,
				R.drawable.operate_btn_songqian_checked, 
				R.drawable.operate_btn_pizhu_checked,
				R.drawable.operate_btn_tuihui_checked};
		/**
		 * 已签发时底部菜单选中时的图片样式（按照修改、录入、批注、撤稿的顺序排列）
		 */
		newsOperateBtnChecked3 = new int[]{
				R.drawable.operate_btn_xiugai_checked,
				R.drawable.operate_btn_luru_checked,
				R.drawable.operate_btn_pizhu_checked, 
				R.drawable.operate_btn_chegao_checked};
		/**
		 * 待编辑时底部菜单禁止点击时的图片样式（按照修改、录入、批注、送签、删除的顺序排列）
		 */
		newsOperateBtnDisabled1 = new int[]{
				R.drawable.operate_btn_xiugai_disable, 
				R.drawable.operate_btn_luru_disable, 
				R.drawable.operate_btn_songqian_disable,
				R.drawable.operate_btn_pizhu_disable, 
				R.drawable.operate_btn_shanchu_disable};
		/**
		 * 待签发时底部菜单禁止点击时的图片样式（按照修改、录入、批注、签发、退回的顺序排列）
		 */
		newsOperateBtnDisabled2 = new int[]{
				R.drawable.operate_btn_xiugai_disable, 
				R.drawable.operate_btn_luru_disable, 
				R.drawable.operate_btn_pizhu_disable, 
				R.drawable.operate_btn_songqian_disable,
				R.drawable.operate_btn_tuihui_disable};
		/**
		 * 已签发时底部菜单禁止点击时的图片样式（按照修改、录入、批注、撤稿的顺序排列）
		 */
		newsOperateBtnDisabled3 = new int[]{
				R.drawable.operate_btn_xiugai_disable, 
				R.drawable.operate_btn_luru_disable,
				R.drawable.operate_btn_pizhu_disable, 
				R.drawable.operate_btn_chegao_disable};
		/**
		 * 底部菜单的文字描述
		 */
		newsOperateBtnTextContent1 = new String[]{"修改", "录入", "送签", "批注", "删除"};
		newsOperateBtnTextContent2 = new String[]{"修改", "录入", "签发", "批注", "退回"};
		newsOperateBtnTextContent3 = new String[]{"修改", "录入", "批注", "撤稿"};
		/**
		 * 底部菜单禁止点击时如果点击，所提示的信息
		 */
		disableHint1 = new String[]{
				"必须选择且仅选择一条待编辑新闻时才可以进行修改", 
				"必须选择且仅选择一条待编辑新闻时才可以进行录入", 
				"必须至少选择一条待编辑新闻才能进行送签", 
				"必须选择且仅选择一条待编辑新闻才能进行批注", 
				"必须至少选择一条待编辑新闻才能进行删除"};
		disableHint2 = new String[]{
				"必须选择且仅选择一条待签发新闻时才可以进行修改", 
				"必须选择且仅选择一条待编辑新闻时才可以进行录入",
				"必须至少选择一条待签发新闻才能进行签发", 
				"必须至少选择一条待签发新闻才能进行批注", 
				"必须至少选择一条待签发新闻才能进行退回"};
		disableHint3 = new String[]{
				"必须选择且仅选择一条已签发新闻时才可以进行修改", 
				"必须选择且仅选择一条待编辑新闻时才可以进行录入",
				"必须至少选择一条已签发新闻才能进行批注", 
				"必须至少选择一条已签发新闻才能进行撤稿"};
		/**
		 * 底部菜单可以变为可以点击的条件
		 * 跟新闻列表的选中个数进行联动，有三种状态：
		 * 1.都没选中【ParamConst.CAN_ENABLE_STATE_DEFAULT】
		 * 2.只选中了一个【ParamConst.CAN_ENABLE_STATE_SIMPLE】
		 * 3.选中了至少一个【ParamConst.CAN_ENABLE_STATE_MORE】
		 */
		newsOperateBtnCanEnableState1 = new int[]{
				ParamConst.CAN_ENABLE_STATE_SIMPLE, 
				ParamConst.CAN_ENABLE_STATE_SIMPLE, 
				ParamConst.CAN_ENABLE_STATE_MORE, 
				ParamConst.CAN_ENABLE_STATE_SIMPLE, 
				ParamConst.CAN_ENABLE_STATE_MORE};
		newsOperateBtnCanEnableState2 = new int[]{
				ParamConst.CAN_ENABLE_STATE_SIMPLE, 
				ParamConst.CAN_ENABLE_STATE_SIMPLE, 
				ParamConst.CAN_ENABLE_STATE_MORE, 
				ParamConst.CAN_ENABLE_STATE_SIMPLE, 
				ParamConst.CAN_ENABLE_STATE_MORE};
		newsOperateBtnCanEnableState3 = new int[]{
				ParamConst.CAN_ENABLE_STATE_SIMPLE, 
				ParamConst.CAN_ENABLE_STATE_SIMPLE, 
				ParamConst.CAN_ENABLE_STATE_MORE,  
				ParamConst.CAN_ENABLE_STATE_MORE};
		/**
		 * 当底部菜单可以点击时，文字显示的颜色（按照修改、批注、送签、删除的顺序排列）
		 */
//		newsOperateBtnColor = new int[]{R.color.bottom_text_color_blue, R.color.bottom_text_color_green, R.color.bottom_text_color_yellow, R.color.bottom_text_color_red};
		newsOperateBtnColor1 = new int[]{
				R.color.white, 
				R.color.white, 
				R.color.white, 
				R.color.white, 
				R.color.bottom_text_color_red};
		newsOperateBtnColor2 = new int[]{
				R.color.white, 
				R.color.white, 
				R.color.white, 
				R.color.white, 
				R.color.bottom_text_color_red};
		newsOperateBtnColor3 = new int[]{
				R.color.white, 
				R.color.white, 
				R.color.white, 
				R.color.bottom_text_color_red};
		/**
		 * 底部菜单存在于“更多”按钮中的菜单
		 */
		newsIsInMore1 = new boolean[] {
				false,
				false,
				false,
				true,
				true
		};
		newsIsInMore2 = new boolean[] {
				false,
				false,
				false,
				true,
				true
		};
		newsIsInMore3 = new boolean[] {
				false,
				false,
				false,
				false
		};
	}
}
