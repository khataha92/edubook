package DataModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lap on 6/10/16.
 */
public class Event {

    int id;

    @SerializedName("start_date")
    String startDate;

    @SerializedName("end_date")
    String endDate;

    String title;

    String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {

        this.id = id;

    }

    public String getStartDate() {

        return startDate;

    }

    public void setStartDate(String startDate) {

        this.startDate = startDate;

    }

    public String getEndDate() {

        return endDate;

    }

    public void setEndDate(String endDate) {

        this.endDate = endDate;

    }

    public String getTitle() {

        return title;

    }

    public void setTitle(String title) {

        this.title = title;

    }

    public String getDescription() {

        return description;

    }

    public void setDescription(String description) {

        this.description = description;

    }
}
