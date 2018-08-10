package trycatch.ex.alertnotice.activity;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import trycatch.ex.alertnotice.R;
import trycatch.ex.alertnotice.event.NoticeDetailEvent;
import trycatch.ex.alertnotice.model.NoticeDetailModel;
import trycatch.ex.alertnotice.util.ApiManager;

/**
 * Created by trycatch on 2018. 5. 10..
 */

public class NoticeDetailActivity extends BaseActivity {
    @BindView(R.id.root_view)
    LinearLayout root_view;

    @BindView(R.id.body)
    WebView body;

    @BindView(R.id.title)
    TextView title;

    private String provider;
    private String id;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_notice);

        ButterKnife.bind(this);

        provider = getIntent().getStringExtra("provider");
        id = getIntent().getStringExtra("id");

        Log.e("provider", provider);

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

        View view = getLayoutInflater().inflate(R.layout.dialog_loading, null);
        ProgressWheel progress = view.findViewById(R.id.progress);

        dialog = new AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(false)
                .create();

        dialog.show();
        progress.spin();

        ApiManager.getInstance().getNoticeDetail(provider, id);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(0);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void NoticeDetailEvent(NoticeDetailEvent noticeDetailEvent){
        dialog.dismiss();
        if(noticeDetailEvent.isSuccess()){
            NoticeDetailModel.Data data = noticeDetailEvent.getData().getData();
            if(noticeDetailEvent.getData().getType() == 1){
                title.setText(data.getTitle());
                body.loadData(data.getBody(), "text/html; charset=utf-8", "UTF-8");
            }
            else {
                body.loadUrl(data.getUrl());
                root_view.removeView(title);
                Log.e("url", data.getUrl());
            }
        }
    }
}
