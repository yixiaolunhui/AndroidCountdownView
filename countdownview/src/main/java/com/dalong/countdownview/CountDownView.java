package com.dalong.countdownview;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;


/**
 * 倒计时view  圆形 长方形广告倒计时
 * Created by dalong on 2016/11/14.
 */

public class CountDownView  extends View implements View.OnClickListener {

    //默认文字
    private final String mDefultText="点击跳过";
    //背景颜色
    private int mBackgroundColor;
    //圆环颜色
    private int mRoundColor;
    //进度颜色
    private int mRoundProgressColor;
    //圆环的宽度
    private float mRoundWidth;
    //文字
    private String mText;
    //文字的大小
    private float mTextSize;
    //文字的颜色
    private int mTextColor;
    //倒计时的时间
    private int mCountdownTime;
    //背景圆画笔
    private Paint mBackgroundPaint;
    // 圆环和进度画笔
    private Paint mBorderPaint;
    //文字画笔
    private TextPaint mTextPaint;
    //文字换行的一个工具类 实现了文本绘制换行处理
    private StaticLayout mStaticLayout;
    //中间坐标
    private int centerX,centerY;
    //进度值
    private int mRoundProgress;
    //一秒添加的度数
    private int mProgressPart;


    public CountDownView(Context context) {
        this(context,null);
    }

    public CountDownView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CountDownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.CountDownView);
        mBackgroundColor=typedArray.getColor(R.styleable.CountDownView_backgroundColor, Color.GRAY);
        mRoundColor=typedArray.getColor(R.styleable.CountDownView_roundColor,Color.RED);
        mRoundProgressColor=typedArray.getColor(R.styleable.CountDownView_roundProgressColor,Color.YELLOW);
        mRoundWidth=typedArray.getDimension(R.styleable.CountDownView_roundWidth,15f);
        mText=typedArray.getString(R.styleable.CountDownView_text);
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.CountDownView_textSize, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
        mTextColor=typedArray.getColor(R.styleable.CountDownView_textColor,Color.RED);
        mCountdownTime=typedArray.getInt(R.styleable.CountDownView_countdownTime,3000);
        typedArray.recycle();

        init();
    }

    private void init() {
        if(TextUtils.isEmpty(mText))mText=mDefultText;
        setOnClickListener(this);
        mProgressPart=360/(mCountdownTime/1000);
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setDither(true);
        mBackgroundPaint.setColor(mBackgroundColor);
        mBackgroundPaint.setStyle(Paint.Style.FILL);

        mBorderPaint = new Paint();
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setDither(true);
        mBorderPaint.setStrokeWidth(mRoundWidth);
        mBorderPaint.setStyle(Paint.Style.STROKE);



        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setDither(true);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setTextAlign(Paint.Align.CENTER);


        int textWidth = (int) mTextPaint.measureText(mText.substring(0, (mText.length() + 1) / 2));
        mStaticLayout = new StaticLayout(mText, mTextPaint, textWidth, Layout.Alignment.ALIGN_NORMAL, 1F, 0, false);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);

        //如果没有设置宽度就使用文字的宽度来设置
        if (widthMode != MeasureSpec.EXACTLY) {
            widthSize = mStaticLayout.getWidth()+getPaddingRight()+getPaddingLeft();
        }
        //如果没有设置高度就使用文字的高度来设置
        if (heightMode != MeasureSpec.EXACTLY) {
            heightSize = mStaticLayout.getHeight()+getPaddingBottom()+getPaddingTop();
        }
        int max=Math.max(widthSize,heightSize);
        setMeasuredDimension(max, max);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        centerX=getMeasuredWidth()/2;
        centerY=getMeasuredHeight()/2;
        drawBackground(canvas);
        drawRound(canvas);
        drawRoundProgress(canvas);
        drawText(canvas);
    }

    /**
     * 绘制背景圆
     * @param canvas
     */
    private void drawBackground(Canvas canvas) {
        canvas.drawCircle(centerX,centerY,Math.max(centerX,centerY),mBackgroundPaint);
    }


    /**
     * 绘制圆环
     * @param canvas
     */
    private void drawRound(Canvas canvas) {
        mBorderPaint.setColor(mRoundColor);
        int min=Math.max(centerX,centerY);
        canvas.drawCircle(centerX,centerY,min-mRoundWidth/2,mBorderPaint);
    }


    /**
     * 绘制进度条
     * @param canvas
     */
    private void drawRoundProgress(Canvas canvas) {
        mBorderPaint.setColor(mRoundProgressColor);
        int min=Math.min(centerX,centerY);
        int radius = (int) (min - mRoundWidth / 2);
        RectF rectF = new RectF(centerX -radius,centerY -radius, centerX + radius, centerY + radius);
        canvas.drawArc(rectF, 0, mRoundProgress, false, mBorderPaint);
    }


    /**
     * 绘制文字
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        canvas.translate(centerX,centerY- mStaticLayout.getHeight() / 2);
        mStaticLayout.draw(canvas);
    }

    /**
     * 开始倒计时
     */
    public void startCountdown() {
        if(mRoundProgress>=360)mRoundProgress=0;
        if(mOnCountDownListener!=null)mOnCountDownListener.start();
        if(mHandler!=null)mHandler.sendEmptyMessageDelayed(mMsgWhat,1000);
    }
    /**
     * 停止倒计时
     */
    public void stopCountdown() {
        if(mHandler!=null)mHandler.removeMessages(mMsgWhat);
    }

    /**
     * 更新进度条
     */
    public final  int mMsgWhat=1000;
    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case mMsgWhat:
                    mRoundProgress+=mProgressPart;
                    if(mRoundProgress>=360){
                        mHandler.removeMessages(mMsgWhat);
                        if(mOnCountDownListener!=null)mOnCountDownListener.finish();
                    }else{
                        mHandler.sendEmptyMessageDelayed(mMsgWhat,1000);
                    }
                    postInvalidate();
                    break;
            }
        }
    };

    OnCountDownListener mOnCountDownListener;

    /**
     * 接口实现
     * @param l
     */
    public void setOnCountDownListener(OnCountDownListener l) {
        this.mOnCountDownListener=l;
    }

    /**
     * 点击直接结束
     * @param v
     */
    @Override
    public void onClick(View v) {
        if(mHandler!=null) mHandler.removeMessages(mMsgWhat);
        if(mOnCountDownListener!=null)mOnCountDownListener.finish();
    }

    /**
     * 倒计时回调接口
     */
    public interface  OnCountDownListener{
        void  start();
        void  finish();
    }


}
