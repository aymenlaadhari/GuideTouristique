package adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

public class ImageWebView extends WebView {

	public ImageWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public ImageWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ImageWebView(Context context) {
		super(context);
		init();
	}

	private void init() {
		setClickable(false);
		setFocusable(false);

		getSettings().setBuiltInZoomControls(false);
		getSettings().setSupportZoom(false);
		getSettings().setJavaScriptEnabled(false);
		setInitialScale(68);
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return false;
	}

	public void loadImage(String url) {

		loadData("<body leftmargin=0 topmargin=0><img src='" + url
				+ "' /></body>", "text/html", "utf-8");
		getSettings().setUseWideViewPort(true);
	}
}
