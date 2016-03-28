package com.enorth.cms.widget.view;

import com.enorth.cms.view.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

public class ViewShadow extends ImageView {

	private Context context;

	private Paint paint;

	private int resourceId;

	public ViewShadow(Context context, int resourceId) {
		super(context);
		this.context = context;
		this.resourceId = resourceId;
	}

	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int posX = 20;
		int posY = 50;
		int PicWidth, PicHegiht;

		Drawable drawable = ContextCompat.getDrawable(context, R.drawable.ma);
		Drawable dbe = ContextCompat.getDrawable(context, R.drawable.ma).mutate();// 如果不调用mutate方法，则原图也会被改变，因为调用的资源是同一个，所有对象是共享状态的。
//		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ma);

		PicWidth = drawable.getIntrinsicWidth();
		PicHegiht = drawable.getIntrinsicHeight();

		dbe.setBounds(posX, posY, posX + PicWidth, posY + PicHegiht);
		drawable.setBounds(0, 0, PicWidth, PicHegiht);
		/*drawable.setBounds(posX, posY, posX + PicWidth, posY + PicHegiht);
		dbe.setBounds(0, 0, PicWidth, PicHegiht);*/

		canvas.drawColor(Color.WHITE);// 设置画布颜色
		canvas.save(Canvas.MATRIX_SAVE_FLAG);
		// 取两层绘制交集。显示上层
		dbe.setColorFilter(0x39005A9C, PorterDuff.Mode.SRC_IN);
		canvas.translate(posX + (int) (0.1 * PicWidth), posY + PicHegiht / 10);// 图像平移为了刚好在原图后形成影子效果。
		canvas.skew(-0.1F, 0.0F);// 图像倾斜效果。
		canvas.scale(1.0f, 0.9f);// 图像（其实是画布）缩放，Y方向缩小为1/2。
//		canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));
		dbe.draw(canvas);// 此处为画原图像影子效果图，比原图先画，则会在下层。
		
//		canvas.drawBitmap(bitmap, matrix, paint);
		drawable.clearColorFilter();
		canvas.restore();

		canvas.save(Canvas.MATRIX_SAVE_FLAG);
		drawable.draw(canvas);// 此处为画原图像，由于canvas有层次效果，因此会盖在影子之上。
		canvas.restore();
	}

}
