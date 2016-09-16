package DataModels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by KhalidTaha on 9/10/16.
 */
public class NotificationResponse {

    int total;

    @SerializedName("next_page_url")
    String nextPageUrl;

    @SerializedName("data")
    List<NotificationModel> notifications;

    public int getTotal() {
        return total;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public List<NotificationModel> getNotifications() {
        return notifications;
    }
}
