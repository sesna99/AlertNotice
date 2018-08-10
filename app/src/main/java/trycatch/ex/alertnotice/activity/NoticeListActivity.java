package trycatch.ex.alertnotice.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pnikosis.materialishprogress.ProgressWheel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import trycatch.ex.alertnotice.R;
import trycatch.ex.alertnotice.adapter.NoticeListAdapter;
import trycatch.ex.alertnotice.event.NoticeEvent;
import trycatch.ex.alertnotice.model.ExchangeModel;
import trycatch.ex.alertnotice.model.NoticeModel;
import trycatch.ex.alertnotice.util.ApiManager;

/**
 * Created by trycatch on 2018. 5. 10..
 */

public class NoticeListActivity extends BaseActivity {
    @BindView(R.id.notice_list)
    ListView notice_list;

    private NoticeListAdapter noticeListAdapter;
    private ArrayList<NoticeModel> data;
    private ExchangeModel exchange;
    private String provider;
    private String id;
    private String icon;
    private int page = 1;
    private boolean isRequest = false;
    private boolean EOF = false;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_list);

        ButterKnife.bind(this);

        id = getIntent().getStringExtra("id");
        provider = getIntent().getStringExtra("provider");
        icon = getIntent().getStringExtra("icon");
        exchange = new ExchangeModel(icon, provider);

        if(id != null){
            Intent intent = new Intent(NoticeListActivity.this, NoticeDetailActivity.class);
            intent.putExtra("provider", provider);
            intent.putExtra("id", id);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }

        data = new ArrayList<>();

        noticeListAdapter = new NoticeListAdapter(getApplicationContext(), getLayoutInflater(), data, exchange);
        notice_list.setAdapter(noticeListAdapter);

        notice_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int count = totalItemCount - visibleItemCount;

                if(firstVisibleItem >= count && totalItemCount != 0 && !EOF) {
                    getNotice();
                }
            }
        });

        notice_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(NoticeListActivity.this, NoticeDetailActivity.class);
                intent.putExtra("provider", data.get(i).getProvider());
                intent.putExtra("id", data.get(i).getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        getNotice();
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

    public void getNotice(){
        if(!isRequest) {
            View view = getLayoutInflater().inflate(R.layout.dialog_loading, null);
            ProgressWheel progress = view.findViewById(R.id.progress);

            dialog = new AlertDialog.Builder(this)
                    .setView(view)
                    .setCancelable(false)
                    .create();

            dialog.show();
            progress.spin();

            ApiManager.getInstance().getNotice(provider, page++);
            isRequest = true;
        }
    }

    @Subscribe
    public void NoticeEvent(NoticeEvent event){
        dialog.dismiss();
        if(event.isSuccess()){
            if(event.getData().size() == 0){
                EOF = true;
                return;
            }
            data.addAll(event.getData());
            noticeListAdapter.setItem(data);
            isRequest = false;
        }
    }
}
