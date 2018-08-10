package trycatch.ex.alertnotice.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Timer;
import java.util.TimerTask;

import trycatch.ex.alertnotice.R;
import trycatch.ex.alertnotice.event.ExchangeListEvent;
import trycatch.ex.alertnotice.util.ApiManager;
import trycatch.ex.alertnotice.util.Util;

/**
 * Created by trycatch on 2018. 5. 13..
 */

public class SplashActivity extends BaseActivity {
    private Intent newIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        newIntent = getIntent();
        ApiManager.getInstance().getExchangeList();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }

    public void checkNotice(Intent notice_intent) {
        final Intent intent = new Intent(this, MainActivity.class);
        int time = 1300;
        if (notice_intent != null) {
            String provider = notice_intent.getStringExtra("provider");
            String id = notice_intent.getStringExtra("id");
            if (provider != null) {
                intent.putExtra("provider", provider);
                intent.putExtra("id", id);
                intent.putExtra("icon", Util.getInstance(getApplicationContext()).getIconMap().get(provider));
                time = 0;
            }
        }
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    finishAffinity();
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, time);
    }

    @Subscribe
    public void ExchangeListEvent(ExchangeListEvent event) {
        if (event.isSuccess()) {
            Util.getInstance(getApplicationContext()).setExchangeList(event.getData());
            checkNotice(newIntent);
        }
    }
}
