package trycatch.ex.alertnotice.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import trycatch.ex.alertnotice.model.ExchangeModel;

/**
 * Created by trycatch on 2018. 5. 12..
 */

public class Util {
    public static Util instance;
    private ArrayList<ExchangeModel> exchangeList;
    private Map<String, String> iconMap;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Gson gson;

    public Util(Context context) {
        pref = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        editor = pref.edit();
        gson = new GsonBuilder().create();
    }

    public ArrayList<ExchangeModel> getExchangeList() {
        return exchangeList;
    }

    public void setExchangeList(ArrayList<ExchangeModel> exchangeList) {
        this.exchangeList = exchangeList;
        iconMap = new HashMap<>();
        Map<String, Boolean> subscribe = getSubscribe();
        if(subscribe == null) subscribe = new HashMap<>();
        for(ExchangeModel exchage: exchangeList) {
            iconMap.put(exchage.getName(), exchage.getIcon());
            if(subscribe.get(exchage.getName()) == null) {
                subscribe.put(exchage.getName(), true);
            }
        }
        setSubscribe(subscribe);
    }

    public void setSubscribe(Map<String, Boolean> setting){
        for(String key : setting.keySet()){
            if(setting.get(key))
                FirebaseMessaging.getInstance().subscribeToTopic(key);
            else
                FirebaseMessaging.getInstance().unsubscribeFromTopic(key);
        }
        editor.putString("setting", gson.toJson(setting));
        editor.commit();
    }

    public Map<String, Boolean> getSubscribe(){
        Map<String, Boolean> setting = gson.fromJson(pref.getString("setting", null), new TypeToken<HashMap<String, Boolean>>(){}.getType());
        return setting;
    }

    public Map<String, String> getIconMap() {
        return iconMap;
    }

    public static Util getInstance(Context context) {
        if(instance == null)
            instance = new Util(context);
        return instance;
    }
}
