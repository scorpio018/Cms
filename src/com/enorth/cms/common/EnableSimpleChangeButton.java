package com.enorth.cms.common;

import com.enorth.cms.bean.ViewColorBasicBean;
import com.enorth.cms.utils.ColorUtil;
import com.enorth.cms.view.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.Button;

public class EnableSimpleChangeButton extends Button {
	
	private Context context;
	/**
	 * 用于表示当前button在点击的时候所代表的id值
	 */
	private int buttonId;
	/**
	 * 是否被选中（用于获取对应的ID）
	 */
	private boolean isChecked = false;
	/**
	 * 初始化ColorStateList时需要传入的states值
	 */
	private int[][] allState;
	/**
	 * 当按压按钮时的state（从按下动作到可选的状态？）
	 */
	private int[] mPressState = new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled };
	/**
	 * 当选中按钮时的state（从可选的状态到选中？）
	 */
	private int[] mFocusState = new int[] { android.R.attr.state_enabled, android.R.attr.state_focused };
	/**
	 * 默认按钮时的state
	 */
	private int[] mNormalState = new int[] { android.R.attr.state_enabled };
	/**
	 * 当选中按钮时的state（单纯的选中）
	 */
	private int[] mFocusStateSecond = new int[] {android.R.attr.state_focused};
	/**
	 * 当不可用时的state
	 */
	private int[] mUnableState = new int[] {android.R.attr.state_window_focused};
	/**
	 * 默认按钮时的state（空的）
	 */
	private int[] mNormalStateNone = new int[] {};
	/**
	 * 所有的背景颜色数组
	 */
	private int[] allBgColor;
	/**
	 * 所有的文字颜色数组
	 */
	private int[] allTextColor;

//	public static int[] mSelectedState = new int[] { android.R.attr.state_selected, android.R.attr.state_enabled };
	/**
	 * 默认的圆角半径
	 */
	private int mRadius = 20;
	
	private float outRectr[];
	
	private boolean needRefreshOutRectr = true;
	
	/**
	 * 默认文字和背景颜色bean
	 */
	private ViewColorBasicBean colorBasicBean;

	public EnableSimpleChangeButton(Context context) {
		super(context);
		this.colorBasicBean = new ViewColorBasicBean(context);
		initUI(context);
	}
	
	public EnableSimpleChangeButton(Context context, ViewColorBasicBean colorBasicBean) throws Exception {
		super(context);
		this.colorBasicBean = colorBasicBean;
		initUI(context);
	}
	
	public EnableSimpleChangeButton(Context context, AttributeSet attrs) throws Exception {
        super(context, attrs);
        this.colorBasicBean = new ViewColorBasicBean(context);
		initUI(context);
	}
	
	public EnableSimpleChangeButton(Context context, AttributeSet attrs, int defStyleAttr) throws Exception {
		super(context, attrs, defStyleAttr);
		this.colorBasicBean = new ViewColorBasicBean(context);
		initUI(context);
	}
	
	public EnableSimpleChangeButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) throws Exception {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.colorBasicBean = new ViewColorBasicBean(context);
		initUI(context);
	}
	
	/**
	 * 初始化按钮UI
	 * @param context
	 */
	private void initUI(Context context) {
//		initPadding(context);
		this.context = context;
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
	 * 按照上-右-下-左来进行圆角的设置，true表示对应的方向有圆角，false表示没有
	 * @param top
	 * @param right
	 * @param bottom
	 * @param left
	 */
	public void needRaduisPosition(boolean top, boolean right, boolean bottom, boolean left) {
		// 将outRectr归零，然后按照需要圆角的位置存入圆角
		outRectr = new float[]{0, 0, 0, 0, 0, 0, 0, 0};
		int length = outRectr.length;
		// 按照左上-右上-右下-左下的顺序确定需要圆角的位置，默认都有圆角
		for (int i = 0; i < length; i++) {
			switch(i) {
			case 0:
				// 左上角
				if (left || top) {
					outRectr[0] = mRadius;
					outRectr[1] = mRadius;
				}
				break;
			case 2:
				// 右上角
				if (right || top) {
					outRectr[2] = mRadius;
					outRectr[3] = mRadius;
				}
				break;
			case 4:
				// 右下角
				if (right || bottom) {
					outRectr[4] = mRadius;
					outRectr[5] = mRadius;
				}
				break;
			case 6:
				// 左下角
				if (left || bottom) {
					outRectr[6] = mRadius;
					outRectr[7] = mRadius;
				}
				break;
			}
		}
		needRefreshOutRectr = false;
//		buildDraweableState();
	}
	
	/**
	 * 构建背景Drawble
	 */
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	private void buildDraweableState() {
		initAllBgColor();
		if (needRefreshOutRectr) {
			outRectr = new float[] { mRadius, mRadius, mRadius, mRadius, mRadius, mRadius, mRadius, mRadius };
		}
		// 创建状态管理器
		StateListDrawable drawable = new StateListDrawable();
		/**
		 * 注意StateListDrawable的构造方法我们这里使用的
		 * 是第一参数它是一个float的数组保存的是圆角的半径，它是按照top-left顺时针保存的八个值
		 */
		// 创建圆弧形状
//		RoundRectShape rectShape = new RoundRectShape(outRectr, null, null);
		int length = allBgColor.length;
		for (int i = 0; i < length; i++) {
			/*// 创建drawable
			ShapeDrawable shapeDrawable = new ShapeDrawable(rectShape);
			// 设置我们按钮背景的颜色
			shapeDrawable.getPaint().setColor(allBgColor[i]);
			// 添加到状态管理里面
			drawable.addState(allState[i], shapeDrawable);*/
			// 创建drawable
			GradientDrawable shapeDrawable = new GradientDrawable();
			shapeDrawable.setCornerRadii(outRectr);
			// 设置我们按钮背景的颜色
			shapeDrawable.setColor(allBgColor[i]);
			shapeDrawable.setStroke(1, ColorUtil.getCommonBlueColor(context));
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
		needRefreshOutRectr = true;
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
	
	public void setColorBasicBean(ViewColorBasicBean colorBasicBean) {
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

	public int getButtonId() {
		return buttonId;
	}

	public void setButtonId(int buttonId) {
		this.buttonId = buttonId;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

}
