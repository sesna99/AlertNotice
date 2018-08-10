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
import trycatch.ex.alertnotice.model.NoticeModel;

/**
 * Created by trycatch on 2018. 5. 10..
 */

public class NoticeListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<NoticeModel> data;
    private ExchangeModel exchange;

    public NoticeListAdapter(Context context, LayoutInflater inflater, ArrayList<NoticeModel> data, ExchangeModel exchange) {
        this.context = context;
        this.inflater = inflater;
        this.data = data;
        this.exchange = exchange;
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

    public void setItem(ArrayList<NoticeModel> data){
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null){
            view = inflater.inflate(R.layout.notice_list_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        else{
            holder = (ViewHolder) view.getTag();
        }

        String name = exchange.getName().substring(0,1).toUpperCase() + exchange.getName().substring(1);
        Glide.with(context).load(exchange.getIcon()).into(holder.provider_icon);
        holder.provider_name.setText(name);
        holder.notice_msg.setText(data.get(i).getTitle());

        return view;
    }

    class ViewHolder{
        @BindView(R.id.provider_icon)
        ImageView provider_icon;

        @BindView(R.id.provider_name)
        TextView provider_name;

        @BindView(R.id.notice_msg)
        TextView notice_msg;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
