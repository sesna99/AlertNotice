package trycatch.ex.alertnotice.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import trycatch.ex.alertnotice.activity.CommunityDetailActivity;
import trycatch.ex.alertnotice.adapter.CommunityListAdapter;
import trycatch.ex.alertnotice.event.CommunityEvent;
import trycatch.ex.alertnotice.model.CommunityModel;
import trycatch.ex.alertnotice.util.ApiManager;

/**
 * Created by trycatch on 2018. 5. 16..
 */

public class CommunityFragment extends Fragment {
    @BindView(R.id.community_list)
    ListView community_list;

    private CommunityListAdapter communityListAdapter;
    private ArrayList<CommunityModel> data;
    private int page = 0;
    private boolean isRequest = false;
    private boolean EOF = false;
    private AlertDialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_community, null);

        ButterKnife.bind(this, view);

        init();

        return view;
    }

    public void init(){
        data = new ArrayList<>();
        communityListAdapter = new CommunityListAdapter(getContext(), getActivity().getLayoutInflater(), data);
        community_list.setAdapter(communityListAdapter);

        community_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), CommunityDetailActivity.class);
                intent.putExtra("url", data.get(i).getUrl());
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        community_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int count = totalItemCount - visibleItemCount;

                if(firstVisibleItem >= count && totalItemCount != 0 && !EOF) {
                    getCommunity();
                }
            }
        });

        getCommunity();
    }

    public void getCommunity(){
        if(!isRequest) {
            View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_loading, null);
            ProgressWheel progress = view.findViewById(R.id.progress);

            dialog = new AlertDialog.Builder(getActivity())
                    .setView(view)
                    .setCancelable(false)
                    .create();

            dialog.show();
            progress.spin();

            ApiManager.getInstance().getCommunity(page++);
            isRequest = true;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void CommunityEvent(CommunityEvent event){
        dialog.dismiss();
        if(event.isSuccess()){
            if(event.getData().size() == 0){
                EOF = true;
                return;
            }
            data.addAll(event.getData());
            communityListAdapter.setItem(data);
            isRequest = false;
        }
    }
}
