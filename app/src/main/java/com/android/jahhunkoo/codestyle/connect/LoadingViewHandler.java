package com.android.jahhunkoo.codestyle.connect;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

import com.android.jahhunkoo.codestyle.R;


public class LoadingViewHandler extends Handler {

	public static final int SHOW_LOADING_LAYOUT = 0;
	public static final int HIDE_LOADING_LAYOUT = 1;
    public static final int SHOW_TOAST = 2;

    public static final String DATA_KEY = "data_key";

	private View loadingView;
	private Activity activity;

	public LoadingViewHandler(Context context) {
        super(Looper.getMainLooper());
		activity = (Activity) context;
        loadingView = LayoutInflater.from(context).inflate(R.layout.connect_loading, null);
        loadingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {}
        });
		activity.addContentView(loadingView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	}


	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);

		switch (msg.what) {
		case SHOW_LOADING_LAYOUT:
			loadingView.bringToFront();
			loadingView.setVisibility(View.VISIBLE);
			break;
		case HIDE_LOADING_LAYOUT:
			if(loadingView.isShown())loadingView.setVisibility(View.GONE);
			break;
        case SHOW_TOAST:
            Toast.makeText(activity, msg.getData().getString(DATA_KEY), Toast.LENGTH_SHORT).show();
            break;

		default:
			break;
		}
	}
	
}
