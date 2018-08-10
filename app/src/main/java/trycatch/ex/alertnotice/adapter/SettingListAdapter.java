package trycatch.ex.alertnotice.adapter;

import android.content.Context;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import trycatch.ex.alertnotice.R;
import trycatch.ex.alertnotice.model.ExchangeModel;
import trycatch.ex.alertnotice.util.Util;

/**
 * Created by trycatch on 2018. 5. 10..
 */

public class SettingListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<ExchangeModel> data;
    private Map<String, Boolean> setting;

    public SettingListAdapter(Context context, LayoutInflater inflater, ArrayList<ExchangeModel> data) {
        this.context = context;
        this.inflater = inflater;
        this.data = data;
        setting = Util.getInstance(context).getSubscribe();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null){
            view = inflater.inflate(R.layout.setting_list_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        else{
            holder = (ViewHolder) view.getTag();
        }

        final ExchangeModel exchange = data.get(i);
        String name = exchange.getName().substring(0,1).toUpperCase() + exchange.getName().substring(1);
        Glide.with(context).load(exchange.getIcon()).into(holder.exchange_icon);
        holder.exchange_name.setText(name);
        if(setting.get(exchange.getName()))
            holder.subscribe_btn.setChecked(true);
        else
            holder.subscribe_btn.setChecked(false);

        holder.subscribe_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                setting.put(exchange.getName(), b);
                Util.getInstance(context).setSubscribe(setting);
            }
        });

        return view;
    }

    class ViewHolder{
        @BindView(R.id.exchange_icon)
        ImageView exchange_icon;

        @BindView(R.id.exchange_name)
        TextView exchange_name;

        @BindView(R.id.subscribe_btn)
        SwitchCompat subscribe_btn;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
