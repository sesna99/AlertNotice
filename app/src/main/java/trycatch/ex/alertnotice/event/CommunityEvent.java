package trycatch.ex.alertnotice.event;

import java.util.ArrayList;

import trycatch.ex.alertnotice.model.CommunityModel;

/**
 * Created by trycatch on 2018. 5. 10..
 */

public class CommunityEvent {
    private boolean success;
    private ArrayList<CommunityModel> data;

    public CommunityEvent(boolean success, ArrayList<CommunityModel> data) {
        this.success = success;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public ArrayList<CommunityModel> getData() {
        return data;
    }
}
