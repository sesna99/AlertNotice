package trycatch.ex.alertnotice.event;

import java.util.ArrayList;

import trycatch.ex.alertnotice.model.NoticeModel;

/**
 * Created by trycatch on 2018. 5. 10..
 */

public class NoticeEvent {
    private boolean success;
    private ArrayList<NoticeModel> data;

    public NoticeEvent(boolean success, ArrayList<NoticeModel> data) {
        this.success = success;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public ArrayList<NoticeModel> getData() {
        return data;
    }
}
