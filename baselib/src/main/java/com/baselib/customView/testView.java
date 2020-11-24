package com.baselib.customView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.baselib.R;

/**
 * 动态画圆
 */
public class testView extends View {
    private Paint paint;
    private int view_style;
    private int view_color;
    private int view_height;
    private int view_width;
    private int progerss = 360;
    private int mAnimateValue = 0;
    private ValueAnimator animator;
    private RectF mRectF;
    private int[] colors = {Color.RED, Color.BLUE, Color.YELLOW, Color.BLACK};
    private int[] angels = {90, 90, 90, 90};
    private int startAngle = 270;

    public testView(Context context) {
        this(context, null);
    }

    public testView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public testView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        initPaint();
        start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //检查高宽
        view_width = resolveSize(view_width, widthMeasureSpec);
        view_height = resolveSize(view_height, heightMeasureSpec);
        setMeasuredDimension(view_width, view_height);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //invilidate()会重新调用
        float start = startAngle;
        //圆形角度顺时针0 、 90 、270 、180 、360   startAngle 目标角度  useCenter 中心开始画 true 一般用来画扇形
        for (int i = 0; i < angels.length; i++) {
            float end = angels[i] * 1.0f / progerss * 360;
            //计算结束角度，随着 mAnimateValue 增加改变画笔颜色
            if
            (Math.min(end, mAnimateValue + startAngle - start) >= 0 && mAnimateValue > start - startAngle) {
                //防止传入的颜色跟角度不一致空指针
                //1
                Log.e("onDraw", i + "===" + mAnimateValue + "==" + start);
                if (i < colors.length)
                    paint.setColor(colors[i]);
                else
                    paint.setColor(Color.RED);
                canvas.drawArc(mRectF, start, Math.min(end, mAnimateValue + startAngle - start), true, paint);
            }
/*
            if (mAnimateValue < end)
                canvas.drawArc(mRectF, start, mAnimateValue, true, paint);
*/
            start += end;
        }
    }

    public void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);
        //画笔颜色
        paint.setColor(view_color);
        // 设置空心
        //     paint.setStyle(Paint.Style.STROKE);
        mRectF = new RectF(0, 0, view_width, view_height);

    }

    public void initAttrs(AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.testView);
        view_color = array.getColor(R.styleable.testView_view_color, Color.BLUE);
        view_style = array.getColor(R.styleable.testView_view_style, 0);
        view_width = array.getDimensionPixelSize(R.styleable.testView_view_height, getContext().getResources().getDimensionPixelOffset(R.dimen.x50));
        view_height = array.getDimensionPixelSize(R.styleable.testView_view_height, getContext().getResources().getDimensionPixelOffset(R.dimen.x50));
        array.recycle();
    }

    public void setProgerss(int pro) {
        progerss = pro;
        invalidate();
    }

    //设置初始角度0、90、180、270
    public void setStartAngle(int startAngle) {
        this.startAngle = startAngle;
    }

    //设置颜色、角度
    public void setcolors(int[] colorList, int[] angelsList) {
        colors = colorList;
        angels = angelsList;
        invalidate();
    }

    //设置宽
    public void setWidth(int size) {
        view_width = size;
        //重新绘制高度 onMeasure、onLayout、onDraw(view位置改变会调用)
        requestLayout();
    }

    private ValueAnimator.AnimatorUpdateListener mUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            //监听动画根据设置的值来决定
            mAnimateValue = (int) animation.getAnimatedValue();
            //非UI postInvalidate();
            //刷新重绘试图UI
            invalidate();
        }
    };

    public void start() {
        if (animator == null) {
            animator = ValueAnimator.ofInt(0, progerss);
            animator.addUpdateListener(mUpdateListener);
            animator.setRepeatMode(ValueAnimator.RESTART);
            animator.setDuration(6000);
            animator.start();
        } else if (!animator.isStarted()) {
            animator.start();
        }
    }

    public void stop() {
        if (animator != null) {
            animator.removeUpdateListener(mUpdateListener);
            animator.removeAllUpdateListeners();
            animator.cancel();
            animator = null;
        }
    }
}
