package trycatch.ex.alertnotice.model;

/**
 * Created by trycatch on 2018. 5. 10..
 */

public class NoticeModel {
    private String id;
    private String title;
    private String provider;

    public NoticeModel(String id, String title, String provider) {
        this.id = id;
        this.title = title;
        this.provider = provider;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getProvider() {
        return provider;
    }
}
