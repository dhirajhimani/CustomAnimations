package com.animationlib.bellanim;

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
 * This custom helper class intialize the image form the xml layout and shows the badges count as per the
 * notifications.
 */
public class NotificationAnimView extends RelativeLayout {

	private ImageView image_notification;
	private TextView text_badge;
	private int count = 1;
	private Animation zoominAnim;
	private Animation zoomoutAnim;
	private Animation shakeAnim;
	private int default_dimen_image = 200;
	private int default_dimen_textview = 50;
	private int default_dimen_image_pad= 5;
	private int default_dimen_textview_textsize= 5;
	private int default_dimen_textview_margin= 20;

	public NotificationAnimView(Context context) {
		super(context, null);
		init(context, null, 0);
	}

	public NotificationAnimView(Context context, AttributeSet attrs) {
		super(context, attrs, 0);
		init(context, attrs, 0);
	}

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

			float image_width = attributes.getDimension(R.styleable.NotificationView_image_width, default_dimen_image);
			float image_height = attributes.getDimension(R.styleable.NotificationView_image_height, default_dimen_image);
			float image_padding = attributes.getDimension(R.styleable.NotificationView_image_padding, default_dimen_image_pad);

			float textview_width = attributes.getDimension(R.styleable.NotificationView_textview_width, default_dimen_textview);
			float textview_height = attributes.getDimension(R.styleable.NotificationView_textview_height, default_dimen_textview);
			float textview_textsize = attributes.getDimension(R.styleable.NotificationView_textview_textsize, default_dimen_textview_textsize);
			float textview_margin = attributes.getDimension(R.styleable.NotificationView_textview_margin, default_dimen_textview_margin);

			image_notification.setPadding((int) image_padding, (int) image_padding, (int) image_padding, (int) image_padding);
			RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) image_width, (int) image_height);
			layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
			layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
			image_notification.setLayoutParams(layoutParams);

			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) textview_width, (int) textview_height);
			params.addRule(RelativeLayout.ALIGN_TOP, R.id.image_notification);
			params.addRule(RelativeLayout.ALIGN_RIGHT, R.id.image_notification);
			params.setMargins((int) textview_margin, 0, (int) textview_margin, (int) textview_margin);

			text_badge.setLayoutParams(params);
			text_badge.setTextSize(textview_textsize);
		} finally {
			attributes.recycle();
		}

	}

	/**
	 * Initialise Views and assign deafult values
	 */
	private void initViews() {
		image_notification = (ImageView) findViewById(R.id.image_notification);
		text_badge = (TextView) findViewById(R.id.text_badge);
		// Anims for img_badge
		zoominAnim = AnimationUtils.loadAnimation(getContext(), R.anim.zoomin);
		zoomoutAnim = AnimationUtils.loadAnimation(getContext(), R.anim.zoomout);
		shakeAnim = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
		//init listener
		shakeAnim.setAnimationListener(animationListener);
	}

	private void startAnim() {
		image_notification.startAnimation(shakeAnim);
	}

	Animation.AnimationListener animationListener = new Animation.AnimationListener() {

		@Override
		public void onAnimationStart(Animation animation) {

		}

		@Override
		public void onAnimationEnd(Animation animation) {
			text_badge.setVisibility(View.VISIBLE);
			text_badge.startAnimation(zoominAnim);
			text_badge.startAnimation(zoomoutAnim);
			text_badge.setText(count + "");
		}

		@Override
		public void onAnimationRepeat(Animation animation) {

		}
	};

	public void resetBadgeCount() {
		count = 1;
		text_badge.setVisibility(View.GONE);
	}

	public void notifyUser(int totalNotifications) {
		startAnim();
		if (totalNotifications != -1) {
			count = totalNotifications;
		}
	}


}
