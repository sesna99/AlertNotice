package trycatch.ex.alertnotice.event;

import java.util.ArrayList;

import trycatch.ex.alertnotice.model.ExchangeModel;

/**
 * Created by trycatch on 2018. 5. 10..
 */

public class ExchangeListEvent {
    private boolean success;
    private ArrayList<ExchangeModel> data;

    public ExchangeListEvent(boolean success, ArrayList<ExchangeModel> data) {
        this.success = success;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public ArrayList<ExchangeModel> getData() {
        return data;
    }
}
