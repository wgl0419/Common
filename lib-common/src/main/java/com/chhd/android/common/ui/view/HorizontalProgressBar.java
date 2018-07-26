package com.chhd.android.common.ui.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.chhd.android.common.R;
import com.chhd.android.common.ui.activity.H5Activity;
import com.chhd.android.common.util.UiUtils;

/**
 * 水平进度条，配合WebView使用，参考{@link H5Activity}
 *
 * @author : 葱花滑蛋 (2018/03/12)
 */
public class HorizontalProgressBar extends View {

    private final String TAG = "HorizontalProgressBar";

    private int mDefaultColor = Color.BLACK;
    private Paint mProgressPaint = null;
    private Paint mProgressCircle = null;
    private int mCurrentProgress = 0;
    private int mTotalProgress = 0;
    private boolean mIsHide = false;

    public HorizontalProgressBar(Context context) {
        this(context, null);
    }

    public HorizontalProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        if (attrs != null) {
            TypedValue outValue = new TypedValue();
            getContext().getTheme().resolveAttribute(R.attr.colorAccent, outValue, true);
            mDefaultColor = outValue.data;
        }
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        mProgressPaint = new Paint();
        mProgressPaint.setColor(mDefaultColor);
        mProgressPaint.setAntiAlias(true);

        mProgressCircle = new Paint();
        mProgressCircle.setColor(mDefaultColor);
        mProgressCircle.setAntiAlias(true);
        mProgressCircle.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.SOLID));
    }

    int mViewWidth = 0;
    int mViewHeight = 0;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获取控件的宽度、高度
        int width = getRealSize(widthMeasureSpec);
        int height = getRealSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    /**
     * MeasureSpec.EXACTLY：边界已经确定，match_parent、50dp
     * <p>
     * MeasureSpec.AT_MOST：wrap_content
     * <p>
     * MeasureSpec.UNSPECIFIED：未指定大小，情况不多，一般父控件是AdapterView
     */

    private int getRealSize(int measureSpec) {
        int result = -1;

        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        if (mode == MeasureSpec.AT_MOST || mode == MeasureSpec.UNSPECIFIED) {
            result = UiUtils.dp2px(getContext(), 2);
        } else {
            result = size;
        }
        return result;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        mViewHeight = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mCurrentProgress <= 100 && mIsHide) {
            mIsHide = false;
            this.setAlpha(1);
        }
        canvas.drawRect(0, 0, (float) (mViewWidth * (mCurrentProgress / 100.0)),
                mViewHeight, mProgressPaint);
        canvas.drawCircle((float) (mViewWidth * (mCurrentProgress / 100.0)) - mViewHeight / 2,
                mViewHeight / 2, mViewHeight, mProgressCircle);
        if (mCurrentProgress >= 100) {
            hideSelf();
        }
    }

    private void hideSelf() {
        this.postDelayed(new Runnable() {
            @Override
            public void run() {
                ViewCompat.animate(HorizontalProgressBar.this).alpha(0);
                mIsHide = true;
//                HorizontalProgressBar.this.mCurrentProgress = 0;
            }
        }, 100);

    }

    private ValueAnimator mAnimator;

    public void setProgress(int progress) {
        if (progress > mTotalProgress) {
            mTotalProgress = progress;
            if (mAnimator != null) {
                if (mAnimator.isRunning()) {
                    mAnimator.cancel();
                }
            }
            mAnimator = ValueAnimator.ofInt(mCurrentProgress, mTotalProgress);
            mAnimator.setDuration(300);
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mCurrentProgress = (int) animation.getAnimatedValue();
                    invalidate();
                }
            });
            mAnimator.start();
        }
    }
}
