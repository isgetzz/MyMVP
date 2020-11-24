package com.baselib.customView;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

/**
 * 控间添加阴影
 */
public class ShadowDrawable extends Drawable {
	private Paint mShadowPaint;
	private Paint mBgPaint;
	private int mShadowRadius;
	private int mShape;
	private int mShapeRadius;
	private int mOffsetX;
	private int mOffsetY;
	private int mBgColor[];
	private RectF mRect;
	//指定位置阴影
	public static int top=0;
	public static int bottom=1;
	public static int left=2;
	public static int right=3;
	private static int Type=-1;

	private final static int SHAPE_ROUND = 1;
	public final static int SHAPE_CIRCLE = 2;

	/**
	 *
	 * @param shape 角度
	 * @param bgColor 颜色
	 * @param shapeRadius 阴影圆角
	 * @param shadowColor 阴影颜色
	 * @param shadowRadius 阴影高度
	 * @param offsetX 偏移X
	 * @param offsetY 偏移Y
	 */
	private ShadowDrawable(int shape, int[] bgColor, int shapeRadius, int shadowColor, int shadowRadius, int offsetX, int offsetY) {
		this.mShape = shape;
		this.mBgColor = bgColor;
		this.mShapeRadius = shapeRadius;
		this.mShadowRadius = shadowRadius;
		this.mOffsetX = offsetX;
		this.mOffsetY = offsetY;
		mShadowPaint = new Paint();
		mShadowPaint.setColor(Color.TRANSPARENT);
		mShadowPaint.setAntiAlias(true);
		mShadowPaint.setShadowLayer(shadowRadius, offsetX, offsetY, shadowColor);
		mShadowPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));
		mBgPaint = new Paint();
		mBgPaint.setAntiAlias(true);
	}
	@Override
	public void setBounds(int left, int top, int right, int bottom) {
		super.setBounds(left, top, right, bottom);
		switch (Type){
			case 0:
				mRect = new RectF(left, top + mShadowRadius - mOffsetY, right, bottom);
				break;
				default:
					mRect = new RectF(left + mShadowRadius - mOffsetX, top + mShadowRadius - mOffsetY, right - mShadowRadius - mOffsetX,
							bottom - mShadowRadius - mOffsetY);
					break;

		}
	}
	@Override
	public void draw(@NonNull Canvas canvas) {
		if (mBgColor != null) {
			if (mBgColor.length == 1) {
				mBgPaint.setColor(mBgColor[0]);
			} else {
				mBgPaint.setShader(new LinearGradient(mRect.left, mRect.height() / 2, mRect.right,
						mRect.height() / 2, mBgColor, null, Shader.TileMode.CLAMP));
			}
		}

		if (mShape == SHAPE_ROUND) {
			canvas.drawRoundRect(mRect, mShapeRadius, mShapeRadius, mShadowPaint);
			canvas.drawRoundRect(mRect, mShapeRadius, mShapeRadius, mBgPaint);
		} else {
			canvas.drawCircle(mRect.centerX(), mRect.centerY(), Math.min(mRect.width(), mRect.height())/ 2, mShadowPaint);
			canvas.drawCircle(mRect.centerX(), mRect.centerY(), Math.min(mRect.width(), mRect.height())/ 2, mBgPaint);
		}
	}
	@Override
	public void setAlpha(int alpha) {
		mShadowPaint.setAlpha(alpha);
	}
	@Override
	public void setColorFilter(@Nullable ColorFilter colorFilter) {
		mShadowPaint.setColorFilter(colorFilter);
	}
	@Override
	public int getOpacity() {
		return PixelFormat.TRANSLUCENT;
	}
	public static void setShadowDrawable(View view, Drawable drawable) {
		view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		ViewCompat.setBackground(view, drawable);
	}
	public static void setShadowDrawable(View view, int shapeRadius, int shadowColor, int shadowRadius, int offsetX, int offsetY) {
		ShadowDrawable drawable = new Builder()
				.setShapeRadius(shapeRadius)
				.setShadowColor(shadowColor)
				.setShadowRadius(shadowRadius)
				.setOffsetX(offsetX)
				.setOffsetY(offsetY)
				.builder();
		view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		ViewCompat.setBackground(view, drawable);
	}

	public static void setShadowDrawable(View view, int bgColor, int shapeRadius, int shadowColor, int shadowRadius, int offsetX, int offsetY,int shadowType) {
		Type=shadowType;
		ShadowDrawable drawable = new Builder()
				.setBgColor(bgColor)
				.setShapeRadius(shapeRadius)
				.setShadowColor(shadowColor)
				.setShadowRadius(shadowRadius)
				.setOffsetX(offsetX)
				.setOffsetY(offsetY)
				.builder();
		view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		ViewCompat.setBackground(view, drawable);
	}

	public static void setShadowDrawable(View view, int shape, int bgColor, int shapeRadius, int shadowColor, int shadowRadius, int offsetX, int offsetY,int shadwo) {
		ShadowDrawable drawable = new Builder()
				.setShape(shape)
				.setBgColor(bgColor)
				.setShapeRadius(shapeRadius)
				.setShadowColor(shadowColor)
				.setShadowRadius(shadowRadius)
				.setOffsetX(offsetX)
				.setOffsetY(offsetY)
				.builder();
		view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		ViewCompat.setBackground(view, drawable);
	}

	public static void setShadowDrawable(View view, int[] bgColor, int shapeRadius, int shadowColor, int shadowRadius, int offsetX, int offsetY) {
		ShadowDrawable drawable = new Builder()
				.setBgColor(bgColor)
				.setShapeRadius(shapeRadius)
				.setShadowColor(shadowColor)
				.setShadowRadius(shadowRadius)
				.setOffsetX(offsetX)
				.setOffsetY(offsetY)
				.builder();
		view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		ViewCompat.setBackground(view, drawable);
	}

	public static class Builder {
		private int mShape;
		private int mShapeRadius;
		private int mShadowColor;
		private int mShadowRadius;
		private int mOffsetX ;
		private int mOffsetY ;
		private int[] mBgColor;

		private Builder() {
			mShape = ShadowDrawable.SHAPE_ROUND;
			mShapeRadius = 12;
			mShadowColor = Color.parseColor("#4d000000");
			mShadowRadius = 18;
			mOffsetX = 0;
			mOffsetY = 0;
			mBgColor = new int[1];
			mBgColor[0] = Color.TRANSPARENT;
		}

		public Builder setShape(int mShape) {
			this.mShape = mShape;
			return this;
		}

		private Builder setShapeRadius(int ShapeRadius) {
			this.mShapeRadius = ShapeRadius;
			return this;
		}

		private Builder setShadowColor(int shadowColor) {
			this.mShadowColor = shadowColor;
			return this;
		}

		private Builder setShadowRadius(int shadowRadius) {
			this.mShadowRadius = shadowRadius;
			return this;
		}

		private Builder setOffsetX(int OffsetX) {
			this.mOffsetX = OffsetX;
			return this;
		}

		private Builder setOffsetY(int OffsetY) {
			this.mOffsetY = OffsetY;
			return this;
		}

		private Builder setBgColor(int BgColor) {
			this.mBgColor[0] = BgColor;
			return this;
		}

		private Builder setBgColor(int[] BgColor) {
			this.mBgColor = BgColor;
			return this;
		}

		private ShadowDrawable builder() {
			return new ShadowDrawable(mShape, mBgColor, mShapeRadius, mShadowColor, mShadowRadius, mOffsetX, mOffsetY);
		}
	}
}