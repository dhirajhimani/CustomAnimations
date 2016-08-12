package com.animationlib.notification;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.animationlib.R;

/**
 * This custom helper class intialize the image form the xml layout and shows the badges mBadgeCount as per the
 * notifications.
 */
public class NotificationAnimView extends RelativeLayout {

	private ImageView mImageNotification;
	private TextView mTextBadge;
	private int mBadgeCount = 1;
	private Animation mZoomInAnim;
	private Animation mZoomOutAnim;
	private Animation mShakeAnim;

	/**
	 * Instantiates a new Notification anim view.
	 *
	 * @param context the context
	 */
	public NotificationAnimView(Context context) {
		super(context, null);
		init(context, null, 0);
	}

	/**
	 * Instantiates a new Notification anim view.
	 *
	 * @param context the context
	 * @param attrs   the attrs
	 */
	public NotificationAnimView(Context context, AttributeSet attrs) {
		super(context, attrs, 0);
		init(context, attrs, 0);
	}

	/**
	 * Instantiates a new Notification anim view.
	 *
	 * @param context      the context
	 * @param attrs        the attrs
	 * @param defStyleAttr the def style attr
	 */
	public NotificationAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attrs, int defStyleAttr) {
		inflate(getContext(), R.layout.notification_view, this);
		initViews();

		if (attrs == null) {
			return;
		}
		TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.NotificationView, 0, 0);
		try {
			int defaultImageWidth = R.dimen.notification_default_image_width;
			int defaultBadgeTextWidth = R.dimen.notification_default_badge_text_width;
			int defaultImagePad= R.dimen.notification_default_image_pad;
			int defaultBadgeTextSize= R.dimen.notification_default_badge_text_size;
			int defaultBadgeTextMargin= R.dimen.notification_default_badge_text_margin;

			float imageWidth = attributes.getDimension(R.styleable.NotificationView_image_width, defaultImageWidth);
			float imageHeight = attributes.getDimension(R.styleable.NotificationView_image_height, defaultImageWidth);
			float imagePadding = attributes.getDimension(R.styleable.NotificationView_image_padding, defaultImagePad);

			float textViewWidth = attributes.getDimension(R.styleable.NotificationView_textview_width, defaultBadgeTextWidth);
			float textViewHeight = attributes.getDimension(R.styleable.NotificationView_textview_height, defaultBadgeTextWidth);
			float textViewTextSize = attributes.getDimension(R.styleable.NotificationView_textview_textsize, defaultBadgeTextSize);
			float textViewMargin = attributes.getDimension(R.styleable.NotificationView_textview_margin, defaultBadgeTextMargin);

			updateNotificationParam((int)imagePadding, (int)imageWidth, (int)imageHeight);

			updateBadgeParam((int)textViewWidth, (int)textViewHeight , (int)textViewTextSize , (int)textViewMargin);

		} finally {
			attributes.recycle();
		}

	}

	/*
	 *	Notification image param are updated.
	 */
	private void updateNotificationParam(int imagePadding, int imageWidth, int imageHeight){
		mImageNotification.setPadding(imagePadding, imagePadding, imagePadding,  imagePadding);
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams( imageWidth, imageHeight);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
		mImageNotification.setLayoutParams(layoutParams);
	}
	/*
	 *	Notification badge param are updated.
	 */
	private void updateBadgeParam(int textViewWidth, int textViewHeight, int textViewTextSize,int textViewMargin){

		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(textViewWidth, textViewHeight);
		params.addRule(RelativeLayout.ALIGN_TOP, R.id.image_notification);
		params.addRule(RelativeLayout.ALIGN_RIGHT, R.id.image_notification);
		params.setMargins(textViewMargin, 0, textViewMargin, textViewMargin);

		mTextBadge.setLayoutParams(params);
		mTextBadge.setTextSize(textViewTextSize);
	}

	/**
	 * Initialise Views and assign deafult values
	 */
	private void initViews() {
		mImageNotification = (ImageView) findViewById(R.id.image_notification);
		mTextBadge = (TextView) findViewById(R.id.text_badge);
		// Anims for img_badge
		mZoomInAnim = AnimationUtils.loadAnimation(getContext(), R.anim.zoomin);
		mZoomOutAnim = AnimationUtils.loadAnimation(getContext(), R.anim.zoomout);
		mShakeAnim = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
		//init listener
		mShakeAnim.setAnimationListener(animationListener);
	}

	private void startAnim() {
		mImageNotification.startAnimation(mShakeAnim);
	}

	/**
	 * The Animation listener.
	 */
	Animation.AnimationListener animationListener = new Animation.AnimationListener() {

		@Override
		public void onAnimationStart(Animation animation) {

		}

		@Override
		public void onAnimationEnd(Animation animation) {
			mTextBadge.setVisibility(View.VISIBLE);
			mTextBadge.startAnimation(mZoomInAnim);
			mTextBadge.startAnimation(mZoomOutAnim);
			mTextBadge.setText(String.valueOf(mBadgeCount));
		}

		@Override
		public void onAnimationRepeat(Animation animation) {

		}
	};

	/**
	 * Reset badge count.
	 */
	public void resetBadgeCount() {
		mBadgeCount = 1;
		mTextBadge.setVisibility(View.GONE);
	}

	/**
	 * Notify user.
	 *
	 * @param totalNotifications the total notifications
	 */
	public void notifyUser(int totalNotifications) {
		startAnim();
		if (totalNotifications != -1) {
			mBadgeCount = totalNotifications;
		}
	}


}
