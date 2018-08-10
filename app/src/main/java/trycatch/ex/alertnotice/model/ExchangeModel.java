package trycatch.ex.alertnotice.model;

/**
 * Created by trycatch on 2018. 5. 10..
 */

public class ExchangeModel {
    private String icon;
    private String name;

    public ExchangeModel(String icon, String name) {
        this.icon = icon;
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }
}
