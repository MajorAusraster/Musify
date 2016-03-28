package com.timon.gugb.musify.views;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Property;
import android.view.animation.DecelerateInterpolator;

import com.timon.gugb.musify.R;

/**
 * Created by Timon on 03.03.2016.
 */
public class FloatingActionButton extends android.support.design.widget.FloatingActionButton {


    private static final Property<FloatingActionButton, Integer> COLOR =
            new Property<FloatingActionButton, Integer>(Integer.class, "color") {
                @Override
                public Integer get(FloatingActionButton v) {
                    return v.getColor();
                }

                @Override
                public void set(FloatingActionButton v, Integer value) {
                    v.setColor(value);
                }
            };


    private static final long PLAY_PAUSE_ANIMATION_DURATION = 300;

    private PlayPauseDrawable mDrawable;
    private final Paint mPaint = new Paint();
    private int mPauseBackgroundColor;
    private int mPlayBackgroundColor;

    private AnimatorSet mAnimatorSet;
    private int mBackgroundColor;

    public FloatingActionButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(context);
    }

    public FloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public FloatingActionButton(Context context) {
        super(context);
        init(context);

    }

    private void init(Context context){
        int btn_color=getResources().getColor(R.color.colorPrimary);

        mBackgroundColor = btn_color;//  getResources().getColor(R.color.spotify_grey);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mDrawable = new PlayPauseDrawable(context);
        mDrawable.setCallback(this);

        mPauseBackgroundColor = btn_color;//getResources().getColor(R.color.spotify_grey);
        mPlayBackgroundColor = btn_color;
        setImageDrawable(mDrawable);

    }

    public void toggle() {
        if (mAnimatorSet != null) mAnimatorSet.cancel();

        mAnimatorSet = new AnimatorSet();
        final boolean isPlay = mDrawable.isPlay();
        final ObjectAnimator colorAnim = ObjectAnimator.ofInt(this, COLOR, isPlay ? mPauseBackgroundColor : mPlayBackgroundColor);
        colorAnim.setEvaluator(new ArgbEvaluator());
        final Animator pausePlayAnim = mDrawable.getPausePlayAnimator();
        mAnimatorSet.setInterpolator(new DecelerateInterpolator());
        mAnimatorSet.setDuration(PLAY_PAUSE_ANIMATION_DURATION);
        mAnimatorSet.playTogether(colorAnim, pausePlayAnim);
        mAnimatorSet.start();


    }

    public boolean isPlay(){
        return mDrawable.isPlay();
    }

    private void setColor(int color) {
        mBackgroundColor = color;
        invalidate();
    }

    private int getColor() {
        return mBackgroundColor;
    }
}
