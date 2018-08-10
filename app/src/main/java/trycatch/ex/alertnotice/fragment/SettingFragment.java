package trycatch.ex.alertnotice.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import trycatch.ex.alertnotice.R;
import trycatch.ex.alertnotice.adapter.SettingListAdapter;
import trycatch.ex.alertnotice.model.ExchangeModel;
import trycatch.ex.alertnotice.util.Util;

public class SettingFragment extends Fragment {
    @BindView(R.id.setting_list)
    ListView setting_list;

    private SettingListAdapter settingListAdapter;
    private ArrayList<ExchangeModel> data;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_setting, null);

        ButterKnife.bind(this, view);

        init();

        return view;
    }

    public void init(){
        data = Util.getInstance(getContext()).getExchangeList();
        settingListAdapter = new SettingListAdapter(getContext(), getActivity().getLayoutInflater(), data);
        setting_list.setAdapter(settingListAdapter);
    }
}
