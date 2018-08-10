package trycatch.ex.alertnotice.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import trycatch.ex.alertnotice.R;
import trycatch.ex.alertnotice.activity.NoticeListActivity;
import trycatch.ex.alertnotice.adapter.ExchangeListAdapter;
import trycatch.ex.alertnotice.model.ExchangeModel;
import trycatch.ex.alertnotice.util.Util;

/**
 * Created by trycatch on 2018. 5. 16..
 */

public class NoticeFragment extends Fragment {
    @BindView(R.id.exchange_list)
    ListView exchange_list;

    private ExchangeListAdapter exchangeListAdapter;
    private ArrayList<ExchangeModel> data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_notice, null);

        ButterKnife.bind(this, view);

        init();

        return view;
    }

    public void init(){
        data = Util.getInstance(getContext()).getExchangeList();
        exchangeListAdapter = new ExchangeListAdapter(getContext(), getActivity().getLayoutInflater(), data);
        exchange_list.setAdapter(exchangeListAdapter);

        exchange_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), NoticeListActivity.class);
                intent.putExtra("provider", data.get(i).getName());
                intent.putExtra("icon", data.get(i).getIcon());
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
    }
}
