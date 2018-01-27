package com.thread.demo5;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Email 2185134304@qq.com
 * Created by JackChen on 2018/1/26.
 * Version 1.0
 * Description: 圆形的进度条
 */
public class ProgressBar extends View {


    //设置内圆弧默认颜色
    private int mInnerBackground = Color.RED ;
    //设置外圆弧默认颜色
    private int mOuterBackground = Color.RED ;
    //设置默认圆弧宽度
    private int mRoundWidth = 10 ; //10px
    //设置默认文字大小
    private float mProgressTextSize = 15 ;
    //设置默认文字颜色
    private int mProgressTextColor = Color.RED ;

    //设置3个画笔 分别是 内圆弧画笔、外圆弧画笔、文字画笔
    private Paint mInnerPaint , mOuterPaint , mTextPaint ;

    //设置最大进度
    private int mMax = 100 ;
    private int mProgress = 0 ;
    private float percent;

    public ProgressBar(Context context) {
        this(context, null);
    }

    public ProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressBar);
        mInnerBackground = typedArray.getColor(R.styleable.ProgressBar_innerBackground,mInnerBackground) ;
        mOuterBackground = typedArray.getColor(R.styleable.ProgressBar_outerBackground , mOuterBackground) ;
        mRoundWidth = typedArray.getDimensionPixelSize(R.styleable.ProgressBar_roundWidth, (int) dip2px(mRoundWidth)) ;
        mProgressTextSize = typedArray.getDimensionPixelSize(R.styleable.ProgressBar_progressTextSize, sp2px(mProgressTextSize)) ;
        mProgressTextColor = typedArray.getColor(R.styleable.ProgressBar_progressTextColor, mProgressTextColor) ;
        typedArray.recycle();

        //初始化3个画笔
        initPaint() ;

    }

    private void initPaint() {
        //内圆弧画笔
        mInnerPaint = new Paint() ;
        mInnerPaint.setAntiAlias(true); //设置抗锯齿
        mInnerPaint.setColor(mInnerBackground); //设置内圆弧颜色
        mInnerPaint.setStrokeWidth(mRoundWidth); //设置圆弧宽度
        mInnerPaint.setStyle(Paint.Style.STROKE); //只绘制图形轮廓（描边）  Paint.Style.FILL 只绘制图形内容 Paint.Style.FILL_AND_STROKE 既绘制轮廓也绘制内容

        //外圆弧画笔
        mOuterPaint = new Paint() ;
        mOuterPaint.setAntiAlias(true);
        mOuterPaint.setColor(mOuterBackground);
        mOuterPaint.setStrokeWidth(mRoundWidth); //设置圆弧宽度
        mOuterPaint.setStyle(Paint.Style.STROKE);

        //文字画笔
        mTextPaint = new Paint() ;
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mProgressTextSize);
        mTextPaint.setColor(mProgressTextColor);
    }


    private int sp2px(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

    private float dip2px(int dip) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        /* 只保证它是正方形 */

        //获取宽度和高度
        int width = MeasureSpec.getSize(widthMeasureSpec) ;
        int height = MeasureSpec.getSize(heightMeasureSpec) ;
        //Math.min(width , height) :表示取宽和高其中的最小值
        setMeasuredDimension(Math.min(width, height), Math.min(width, height));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //1.画内圆[是圆形，不是圆弧]
        drawCircle(canvas) ;

        //2.画外圆弧
        drawArc(canvas) ;

        //3.画文字
        drawText(canvas) ;
    }


    /**
     * 画内圆[不是圆弧]:
     *          以下为固定写法，有需要可以直接拷贝去用
     * @param canvas
     */
    private void drawCircle(Canvas canvas) {
        //中心点
        int center = getWidth() / 2 ;
        /* 参数一：圆心的x坐标  参数二：圆心的y坐标 参数三：圆的半径 参数四：绘制时所使用的画笔
        public void drawCircle(float cx, float cy, float radius, @NonNull Paint paint) {
        }*/
        canvas.drawCircle(center, center, center - mRoundWidth / 2, mInnerPaint) ;
    }


    /**
     * 画外圆弧:
     *          以下为固定写法，有需要可以直接拷贝去用
     * @param canvas
     */
    private void drawArc(Canvas canvas){
        /*
        //参数一：定义圆弧的形状和大小的范围 参数二：开始的角度 参数三：扫过的角度 参数四：设置我们的圆弧在绘画的时候，是否经过圆形 参数五：画笔
        public void drawArc(@NonNull RectF oval, float startAngle, float sweepAngle, boolean useCenter,
        }*/

        RectF rect = new RectF(0 + mRoundWidth/2 , 0+mRoundWidth/2 , getWidth()-mRoundWidth/2 , getHeight()-mRoundWidth/2) ;
        if (mProgress == 0){
            return;
        }

        percent = (float)mProgress / mMax;
        canvas.drawArc(rect , 0 , percent *360 , false , mOuterPaint);

    }
    /**
     *  画文字 :
     *          以下为固定写法，有需要可以直接拷贝去用
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        //要画的文字
        String text = (int)(percent*100)+"%" ;
        Rect bounds = new Rect() ;
        /*public void getTextBounds(String text, int start, int end, Rect bounds)*/
        mTextPaint.getTextBounds(text , 0 , text.length() , bounds);

        /*参数一：要画的文字 参数二：字体的宽度 参数三：基线 参数四：画笔
          public void drawText(@NonNull String text, float x, float y, @NonNull Paint paint) {
        }*/

        int dx = getWidth() /2 - bounds.width()/2 ;

        Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt() ;
        int dy = (fontMetrics.bottom - fontMetrics.top)/2 - fontMetrics.bottom ;
        int baseLine = getHeight()/2+dy ;
        canvas.drawText(text , dx , baseLine , mTextPaint);
    }


    //synchronized防止多个线程同时操作这个进度：设置最大值
    public synchronized void setMax(int max){
        if (max < 0){

        }
        this.mMax = max ;
    }

    //synchronized防止多个线程同时操作这个进度：
    public synchronized void setProgress(int progress){
        if(progress < 0){

        }
        this.mProgress = progress ;
        //刷新 invalidate
        invalidate();

    }
}
