package DataModels;

import java.io.Serializable;

import Enums.ProgressType;

/**
 * Created by mac on 7/11/16.
 */
public class Progress implements Serializable {

    String id;

    String title;

    String details;

    String date;

    ProgressType type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ProgressType getType() {
        return type;
    }

    public void setType(ProgressType type) {
        this.type = type;
    }
}
