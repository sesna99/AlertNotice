package trycatch.ex.alertnotice.util;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import trycatch.ex.alertnotice.event.CommunityEvent;
import trycatch.ex.alertnotice.event.ExchangeListEvent;
import trycatch.ex.alertnotice.event.NoticeDetailEvent;
import trycatch.ex.alertnotice.event.NoticeEvent;
import trycatch.ex.alertnotice.model.CommunityModel;
import trycatch.ex.alertnotice.model.ExchangeModel;
import trycatch.ex.alertnotice.model.NoticeDetailModel;
import trycatch.ex.alertnotice.model.NoticeModel;

/**
 * Created by trycatch on 2017. 11. 27..
 */

public class ApiManager {
    public static ApiManager instance;
    private Retrofit retrofit;
    private RetrofitService retrofitService;

    public ApiManager() {
        init();
    }

    private void init() {
        try {
            retrofit = new Retrofit.Builder().baseUrl("http://108.160.130.250:8080").addConverterFactory(GsonConverterFactory.create()).build();
            retrofitService = retrofit.create(RetrofitService.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getExchangeList(){
        Call<ArrayList<ExchangeModel>> call = retrofitService.getExchangeList();
        call.enqueue(new Callback<ArrayList<ExchangeModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ExchangeModel>> call, Response<ArrayList<ExchangeModel>> response) {
                if(response.isSuccessful())
                    EventBus.getDefault().post(new ExchangeListEvent(true, response.body()));
                else
                    EventBus.getDefault().post(new ExchangeListEvent(false, null));
            }

            @Override
            public void onFailure(Call<ArrayList<ExchangeModel>> call, Throwable t) {
                EventBus.getDefault().post(new ExchangeListEvent(false, null));
            }
        });
    }

    public void getNotice(String provider, int page){
        Map<String, String> query = new HashMap<>();
        query.put("provider", provider);
        query.put("page", page + "");
        Call<ArrayList<NoticeModel>> call = retrofitService.getNotice(query);
        call.enqueue(new Callback<ArrayList<NoticeModel>>() {
            @Override
            public void onResponse(Call<ArrayList<NoticeModel>> call, Response<ArrayList<NoticeModel>> response) {
                if(response.isSuccessful())
                    EventBus.getDefault().post(new NoticeEvent(true, response.body()));
                else
                    EventBus.getDefault().post(new NoticeEvent(false, null));
            }

            @Override
            public void onFailure(Call<ArrayList<NoticeModel>> call, Throwable t) {
                EventBus.getDefault().post(new NoticeEvent(false, null));
                t.printStackTrace();
            }
        });
    }

    public void getNoticeDetail(String provider, String id){
        Map<String, String> query = new HashMap<>();
        query.put("provider", provider);
        query.put("id", id);
        Call<NoticeDetailModel> call = retrofitService.getNoticeDetail(query);
        call.enqueue(new Callback<NoticeDetailModel>() {
            @Override
            public void onResponse(Call<NoticeDetailModel> call, Response<NoticeDetailModel> response) {
                if(response.isSuccessful())
                    EventBus.getDefault().post(new NoticeDetailEvent(true, response.body()));
                else
                    EventBus.getDefault().post(new NoticeDetailEvent(false, null));
            }

            @Override
            public void onFailure(Call<NoticeDetailModel> call, Throwable t) {
                EventBus.getDefault().post(new NoticeDetailEvent(false, null));
                t.printStackTrace();
            }
        });
    }

    public void getCommunity(int page){
        Call<ArrayList<CommunityModel>> call = retrofitService.getCommunity(page);
        call.enqueue(new Callback<ArrayList<CommunityModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CommunityModel>> call, Response<ArrayList<CommunityModel>> response) {
                if(response.isSuccessful())
                    EventBus.getDefault().post(new CommunityEvent(true, response.body()));
                else
                    EventBus.getDefault().post(new CommunityEvent(false, null));
            }

            @Override
            public void onFailure(Call<ArrayList<CommunityModel>> call, Throwable t) {
                EventBus.getDefault().post(new CommunityEvent(false, null));
                t.printStackTrace();
            }
        });
    }

    public static ApiManager getInstance() {
        if (instance == null) {
            instance = new ApiManager();
        }
        return instance;
    }
}
