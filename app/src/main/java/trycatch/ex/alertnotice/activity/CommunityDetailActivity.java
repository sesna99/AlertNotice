package trycatch.ex.alertnotice.activity;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import trycatch.ex.alertnotice.R;

/**
 * Created by trycatch on 2018. 5. 10..
 */

public class CommunityDetailActivity extends BaseActivity {
    @BindView(R.id.body)
    WebView body;

    private String url;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_community);

        ButterKnife.bind(this);

        url = getIntent().getStringExtra("url");

        body.setWebViewClient(new WebViewClient());
        body.setWebChromeClient(new WebChromeClient());
        body.setNetworkAvailable(true);
        body.getSettings().setJavaScriptEnabled(true);
        body.getSettings().setDomStorageEnabled(true);
        body.setVerticalScrollBarEnabled(false);
        body.setOnTouchListener(new View.OnTouchListener() {
            float m_downX;
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        // save the x
                        m_downX = event.getX();
                    }
                    break;
                    case MotionEvent.ACTION_MOVE:
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP: {
                        // set x so that it doesn't move
                        event.setLocation(m_downX, event.getY());
                    }
                    break;
                }
                return false;
            }
        });

        body.loadUrl(url);
    }

    @Override
    protected void onResume() {
        super.onResume();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(0);
    }
}
