package trycatch.ex.alertnotice.event;

import trycatch.ex.alertnotice.model.NoticeDetailModel;

/**
 * Created by trycatch on 2018. 5. 10..
 */

public class NoticeDetailEvent {
    private boolean success;
    private NoticeDetailModel data;

    public NoticeDetailEvent(boolean success, NoticeDetailModel data) {
        this.success = success;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public NoticeDetailModel getData() {
        return data;
    }
}
