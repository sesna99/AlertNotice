package trycatch.ex.alertnotice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import trycatch.ex.alertnotice.R;
import trycatch.ex.alertnotice.model.ExchangeModel;

/**
 * Created by trycatch on 2018. 5. 10..
 */

public class ExchangeListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<ExchangeModel> data;

    public ExchangeListAdapter(Context context, LayoutInflater inflater, ArrayList<ExchangeModel> data) {
        this.context = context;
        this.inflater = inflater;
        this.data = data;
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
            view = inflater.inflate(R.layout.exchange_list_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        else{
            holder = (ViewHolder) view.getTag();
        }

        ExchangeModel exchange = data.get(i);
        String name = exchange.getName().substring(0,1).toUpperCase() + exchange.getName().substring(1);
        Glide.with(context).load(exchange.getIcon()).into(holder.exchange_icon);
        holder.exchange_name.setText(name);

        return view;
    }

    class ViewHolder{
        @BindView(R.id.exchange_icon)
        ImageView exchange_icon;

        @BindView(R.id.exchange_name)
        TextView exchange_name;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
