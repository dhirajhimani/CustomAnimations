package com.animationlib.searchanim;

import android.support.v4.view.animation.PathInterpolatorCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;

public class AnimationUtils {

	// http://easings.net/
	private static String TAG = "AnimationUtils";
	private static Interpolator easeInOutQuart = PathInterpolatorCompat.create(0.77f, 0f, 0.175f, 1f);

	public static Animation expand(final View view) {
		int matchParentMeasureSpec = View.MeasureSpec.makeMeasureSpec(((View) view.getParent()).getWidth(), View.MeasureSpec.EXACTLY);
		int wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		view.measure(matchParentMeasureSpec, wrapContentMeasureSpec);
		final int targetHeight = view.getMeasuredHeight();

		// Older versions of android (pre API 21) cancel animations for views with a height of 0 so use 1 instead.
		view.getLayoutParams().height = 1;
		view.setVisibility(View.VISIBLE);

		Animation animation = new Animation() {
			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {

				view.getLayoutParams().height = interpolatedTime == 1
						? ViewGroup.LayoutParams.WRAP_CONTENT
						: (int) (targetHeight * interpolatedTime);

				view.requestLayout();
			}

			@Override
			public boolean willChangeBounds() {
				return true;
			}
		};

		animation.setInterpolator(easeInOutQuart);
		animation.setDuration(computeDurationFromHeight(view));
		view.startAnimation(animation);

		return animation;
	}

	private static int computeDurationFromHeight(View view) {
		// 1dp/ms * multiplier
		return (int) (view.getMeasuredHeight() / view.getContext().getResources().getDisplayMetrics().density);
	}

	public static Animation collapse(final View view, Animation.AnimationListener animationListener) {
		final int initialWidth = view.getMeasuredWidth();
		final int durationMillis = computeDurationFromWidth(view);
//		final int durationMillis = 1000;
		AnimationSet animationSet = new AnimationSet(true);
//		final int closingWidth = initialWidth / 5;

		final Animation animation = new Animation() {
			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {
				int width = initialWidth - (int) (initialWidth * interpolatedTime);
//				Log.d(TAG, "width = " + width);
				if (interpolatedTime == 1) {
					view.setVisibility(View.GONE);
				} else {
					view.getLayoutParams().width = width;
					view.setTranslationX((initialWidth - view.getLayoutParams().width)/2);
					view.requestLayout();
				}
			}


			@Override
			public boolean willChangeBounds() {
				return true;
			}
		};

		animation.setInterpolator(easeInOutQuart);
		animation.setDuration(durationMillis);
		animation.setAnimationListener(animationListener);
		animationSet.addAnimation(animation);

		view.startAnimation(animationSet);

		return animation;
	}

	private static int computeDurationFromWidth(View view) {
		// 1dp/ms * multiplier
		return (int) (view.getMeasuredWidth() / view.getContext().getResources().getDisplayMetrics().density);
	}
}
