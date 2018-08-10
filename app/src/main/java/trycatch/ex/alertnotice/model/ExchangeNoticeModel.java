package trycatch.ex.alertnotice.model;

/**
 * Created by trycatch on 2018. 5. 10..
 */

public class ExchangeNoticeModel {
    private String icon;
    private String name;
    private boolean isSubscribe;

    public ExchangeNoticeModel(String icon, String name, boolean isSubscribe) {
        this.icon = icon;
        this.name = name;
        this.isSubscribe = isSubscribe;
    }

    public String getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public void setSubscribe(boolean subscribe) {
        isSubscribe = subscribe;
    }

    public boolean isSubscribe() {
        return isSubscribe;
    }
}
