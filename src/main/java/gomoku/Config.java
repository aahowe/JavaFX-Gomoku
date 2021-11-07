package gomoku;


import java.awt.*;

public class Config {
    private int viewPortWidth, viewPortHeight;
    private String databaseURL, databaseName, databasePassword;

    public Config() {
        this.viewPortWidth = 1400;
        this.viewPortHeight = 900;
        this.databaseURL = "jdbc:mysql://49.232.68.139:3306/gomoku";
        this.databaseName = "gomoku";
        this.databasePassword = "GxYm6na45G7rxK75";
    }

    public int getViewPortWidth() {
        return viewPortWidth;
    }

    public void setViewPortWidth(int viewPortWidth) {
        this.viewPortWidth = viewPortWidth;
    }

    public int getViewPortHeight() {
        return viewPortHeight;
    }

    public void setViewPortHeight(int getViewPortHeight) {
        this.viewPortHeight = getViewPortHeight;
    }

    public String getDatabaseURL() {
        return databaseURL;
    }

    public void setDatabaseURL(String databaseURL) {
        this.databaseURL = databaseURL;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public void setDatabasePassword(String databasePassword) {
        this.databasePassword = databasePassword;
    }
}
