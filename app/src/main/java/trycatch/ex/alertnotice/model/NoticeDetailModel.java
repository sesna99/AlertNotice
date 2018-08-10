package trycatch.ex.alertnotice.model;

/**
 * Created by trycatch on 2018. 5. 11..
 */

public class NoticeDetailModel {
    private boolean success;
    private int type;
    private Data data;

    public NoticeDetailModel(boolean success, int type, Data data) {
        this.success = success;
        this.type = type;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getType() {
        return type;
    }

    public Data getData() {
        return data;
    }

    public class Data {
        private String title;
        private String body;
        private String url;

        public Data(String title, String body) {
            this.title = title;
            this.body = body;
        }

        public Data(String url) {
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public String getBody() {
            return body;
        }

        public String getUrl() {
            return url;
        }
    }
}

