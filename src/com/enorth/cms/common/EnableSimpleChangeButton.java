package com.enorth.cms.common;

import com.enorth.cms.activity.R;
import com.enorth.cms.bean.ButtonColorBasicBean;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.view.Gravity;
import android.widget.Button;

public class EnableSimpleChangeButton extends Button {
	/**
	 * 初始化ColorStateList时需要传入的states值
	 */
	public int[][] allState;
	/**
	 * 当按压按钮时的state（从按下动作到可选的状态？）
	 */
	public int[] mPressState = new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled };
	/**
	 * 当选中按钮时的state（从可选的状态到选中？）
	 */
	public int[] mFocusState = new int[] { android.R.attr.state_enabled, android.R.attr.state_focused };
	/**
	 * 默认按钮时的state
	 */
	public int[] mNormalState = new int[] { android.R.attr.state_enabled };
	/**
	 * 当选中按钮时的state（单纯的选中）
	 */
	public int[] mFocusStateSecond = new int[] {android.R.attr.state_focused};
	/**
	 * 当不可用时的state
	 */
	public int[] mUnableState = new int[] {android.R.attr.state_window_focused};
	/**
	 * 默认按钮时的state（空的）
	 */
	public int[] mNormalStateNone = new int[] {};
	/**
	 * 所有的背景颜色数组
	 */
	public int[] allBgColor;
	/**
	 * 所有的文字颜色数组
	 */
	public int[] allTextColor;

//	public static int[] mSelectedState = new int[] { android.R.attr.state_selected, android.R.attr.state_enabled };
	/**
	 * 默认的圆角半径
	 */
	private int mRadius = 20;
	/**
	 * 默认文字和背景颜色bean
	 */
	private ButtonColorBasicBean colorBasicBean;

	public EnableSimpleChangeButton(Context context) throws Exception {
		super(context);
		this.colorBasicBean = new ButtonColorBasicBean(context);
		initUI(context);
	}
	
	public EnableSimpleChangeButton(Context context, ButtonColorBasicBean colorBasicBean) {
		super(context);
		this.colorBasicBean = colorBasicBean;
		initUI(context);
	}

	/**
	 * 初始化按钮UI
	 * @param context
	 */
	private void initUI(Context context) {
		initPadding(context);
		initAllState();
		setGravity(Gravity.CENTER);
		buildDraweableState();
//		buildColorDrawableState();
	}
	
	public void initPadding(Context context) {
		Resources resources = context.getResources();
		int top = (int) resources.getDimension(R.dimen.news_title_change_viewpager_btn_padding_top);
		int right = (int) resources.getDimension(R.dimen.news_title_change_viewpager_btn_padding_right);
		int bottom = (int) resources.getDimension(R.dimen.news_title_change_viewpager_btn_padding_bottom);
		int left = (int) resources.getDimension(R.dimen.news_title_change_viewpager_btn_padding_left);
		this.setPadding(left, top, right, bottom);
	}
	
	/**
	 * 按照press、focus、normal、focus、unable、normal的顺序进行存入
	 */
	private void initAllState() {
		allState = new int[][]{
			mPressState, mFocusState, mNormalState, 
			mFocusStateSecond, mUnableState, mNormalStateNone};
	}
	
	/**
	 * 按照initAllState中的顺序定义对应的背景颜色
	 */
	private void initAllBgColor() {
		allBgColor = new int[] {
				colorBasicBean.getmBgPressedColor(), colorBasicBean.getmBgFocusedColor(),
				colorBasicBean.getmBgNormalColor(), colorBasicBean.getmBgFocusedColor(),
				colorBasicBean.getmBgUnabledColor(), colorBasicBean.getmBgNormalColor()
			};
	}
	
	/**
	 * 按照initAllState中的顺序定义对应的文本颜色
	 */
	private void initAllTextColor() {
		allTextColor = new int[] {
			colorBasicBean.getmTextPressedColor(), colorBasicBean.getmTextFocusedColor(),
			colorBasicBean.getmTextNormalColor(), colorBasicBean.getmTextFocusedColor(),
			colorBasicBean.getmTextUnabledColor(), colorBasicBean.getmTextNormalColor()
		};
	}
	
	/**
	 * 构建图片drawble
	 */
	private void buildColorDrawableState() {
		initAllTextColor();
//		int[] colors = new int[] { pressed, focused, normal, focused, unable, normal };
		ColorStateList colorStateList = new ColorStateList(allState, allTextColor);
		setTextColor(colorStateList);
	}

	/**
	 * 构建背景Drawble
	 */
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	private void buildDraweableState() {
		initAllBgColor();
		float outRectr[] = new float[] { mRadius, mRadius, mRadius, mRadius, mRadius, mRadius, mRadius, mRadius };
		// 创建状态管理器
		StateListDrawable drawable = new StateListDrawable();
		/**
		 * 注意StateListDrawable的构造方法我们这里使用的
		 * 是第一参数它是一个float的数组保存的是圆角的半径，它是按照top-left顺时针保存的八个值
		 */
		// 创建圆弧形状
		RoundRectShape rectShape = new RoundRectShape(outRectr, null, null);
		int length = allBgColor.length;
		for (int i = 0; i < length; i++) {
			// 创建drawable
			ShapeDrawable shapeDrawable = new ShapeDrawable(rectShape);
			// 设置我们按钮背景的颜色
			shapeDrawable.getPaint().setColor(allBgColor[i]);
			// 添加到状态管理里面
			drawable.addState(allState[i], shapeDrawable);
		}
		/*ShapeDrawable pressedDrawable = new ShapeDrawable(rectShape);
		// 设置我们按钮背景的颜色
		pressedDrawable.getPaint().setColor(colorBasicBean.getmBgPressedColor());
		// 添加到状态管理里面
		drawable.addState(mPressState, pressedDrawable);

		// ShapeDrawable disableDrawable = new ShapeDrawable(rectShape);
		// disableDrawable.getPaint().setColor(prssedClor);
		// disableDrawable.getPaint().setAlpha(125);
		// drawable.addState(mDisableState, disableDrawable);

		ShapeDrawable normalDrawable = new ShapeDrawable(rectShape);
		normalDrawable.getPaint().setColor(colorBasicBean.getmBgNormalColor());
		drawable.addState(mNormalState, normalDrawable);*/
		// 设置我们的背景，就是xml里面的selector
		/**
		 * 由于setBackgroundDrawable已经被废弃，但是老版本的SDK还是要使用这个方法的 而API
		 * Level低于16的SDK版本无法使用setBackground方法，所以进行版本判断
		 * PS：我看那个横线也很难受，不过为了能动态实现Button颜色和圆角弧度的改变，只能忍了。。
		 */
		int sdk = android.os.Build.VERSION.SDK_INT;
		if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
			setBackgroundDrawable(drawable);
		} else {
			setBackground(drawable);
		}
		buildColorDrawableState();
	}

	/**
	 * 设置圆角矩形
	 * 
	 * @param radius
	 */
	public void setRadius(int radius) {
		this.mRadius = radius;
		buildDraweableState();
	}
	
	/**
	 * 设置按钮按下时的背景颜色
	 * 
	 * @param normalColor
	 * @param prssedColor
	 */
	public void setBgNormalPressedColor(int normalColor, int pressedColor) {
		colorBasicBean.setmBgNormalColor(normalColor);
		colorBasicBean.setmBgPressedColor(pressedColor);
		buildDraweableState();
	}
	
	/**
	 * 设置按钮选中的背景颜色
	 * @param normalColor
	 * @param focusedColor
	 */
	public void setBgNormalFocusedColor(int normalColor, int focusedColor) {
		colorBasicBean.setmBgNormalColor(normalColor);
		colorBasicBean.setmBgFocusedColor(focusedColor);
		buildDraweableState();
	}
	
	/**
	 * 设置按钮禁用时的背景颜色
	 * @param normalColor
	 * @param unableColor
	 */
	public void setBgNormalUnabledColor(int normalColor, int unableColor) {
		colorBasicBean.setmBgNormalColor(normalColor);
		colorBasicBean.setmBgUnabledColor(unableColor);
		buildDraweableState();
	}
	
	/**
	 * 设置按钮的所有背景颜色
	 * @param normalColor
	 * @param pressedColor
	 * @param focusedColor
	 * @param unableColor
	 */
	public void setBgColor(int normalColor, int pressedColor, int focusedColor, int unableColor) {
		colorBasicBean.setmBgNormalColor(normalColor);
		colorBasicBean.setmBgPressedColor(pressedColor);
		colorBasicBean.setmBgFocusedColor(focusedColor);
		colorBasicBean.setmBgUnabledColor(unableColor);
		buildDraweableState();
	}
	
	public void setColorBasicBean(ButtonColorBasicBean colorBasicBean) {
		this.colorBasicBean = colorBasicBean;
		buildDraweableState();
	}
	
	/**
	 * 设置按钮按下时的文字颜色
	 * 
	 * @param normalColor
	 * @param prssedColor
	 */
	public void setTextNormalPressedColor(int normalColor, int pressedColor) {
		colorBasicBean.setmTextNormalColor(normalColor);
		colorBasicBean.setmTextPressedColor(pressedColor);
		buildDraweableState();
	}
	
	/**
	 * 设置按钮选中的文字颜色
	 * @param normalColor
	 * @param focusedColor
	 */
	public void setTextNormalFocusedColor(int normalColor, int focusedColor) {
		colorBasicBean.setmTextNormalColor(normalColor);
		colorBasicBean.setmTextFocusedColor(focusedColor);
		buildDraweableState();
	}
	
	/**
	 * 设置按钮禁用时的文字颜色
	 * @param normalColor
	 * @param unableColor
	 */
	public void setTextNormalUnabledColor(int normalColor, int unableColor) {
		colorBasicBean.setmTextNormalColor(normalColor);
		colorBasicBean.setmTextUnabledColor(unableColor);
		buildDraweableState();
	}

	/**
	 * 设置按钮的所有文字颜色
	 * @param normalColor
	 * @param pressedColor
	 * @param focusedColor
	 * @param unableColor
	 */
	public void setTextColor(int normalColor, int pressedColor, int focusedColor, int unableColor) {
		colorBasicBean.setmTextNormalColor(normalColor);
		colorBasicBean.setmTextPressedColor(pressedColor);
		colorBasicBean.setmTextFocusedColor(focusedColor);
		colorBasicBean.setmTextUnabledColor(unableColor);
		buildColorDrawableState();
	}

}
