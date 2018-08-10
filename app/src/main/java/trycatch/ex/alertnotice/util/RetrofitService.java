package trycatch.ex.alertnotice.util;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import trycatch.ex.alertnotice.model.CommunityModel;
import trycatch.ex.alertnotice.model.ExchangeModel;
import trycatch.ex.alertnotice.model.NoticeDetailModel;
import trycatch.ex.alertnotice.model.NoticeModel;

/**
 * Created by trycatch on 2017. 11. 27..
 */

public interface RetrofitService {
    @GET("/api/getExchangeList")
    Call<ArrayList<ExchangeModel>> getExchangeList();

    @GET("/api/getNotice")
    Call<ArrayList<NoticeModel>> getNotice(@QueryMap Map<String, String> query);

    @GET("/api/getNoticeDetail")
    Call<NoticeDetailModel> getNoticeDetail(@QueryMap Map<String, String> query);

    @GET("/api/getCommunity")
    Call<ArrayList<CommunityModel>> getCommunity(@Query("page") int page);
}
