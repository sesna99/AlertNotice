package trycatch.ex.alertnotice.model;

/**
 * Created by trycatch on 2018. 5. 10..
 */

public class CommunityModel {
    private String url;
    private String title;

    public CommunityModel(String url, String title) {
        this.url = url;
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }
}
