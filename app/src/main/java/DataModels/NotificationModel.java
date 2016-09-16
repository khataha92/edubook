package DataModels;

import com.google.gson.annotations.SerializedName;

import UserUtils.UserDefaultUtil;

/**
 * Created by KhalidTaha on 9/10/16.
 */
public class NotificationModel {

    @SerializedName("item_id")
    int itemId;

    public int getItemId() {

        return itemId;

    }

    User creator;

    boolean seen;

    String age;

    NotificationType type;

    public User getCreator() {

        return creator;
    }

    public boolean isSeen() {
        return seen;
    }

    public String getAge() {

        return age;

    }

    public String getNotificationText(){

        return type.getNotificationText();

    }

    public NotificationType getType() {

        return type;

    }

    public class NotificationType{

        String name;

        @SerializedName("notification_text")
        String notificationText;

        @SerializedName("notifications_text_ar")
        String notificationTextAr;

        public String getNotificationText() {

            if(UserDefaultUtil.getUserLanguage().equalsIgnoreCase("en")) {

                return notificationText;

            }

            return notificationTextAr;

        }
    }
}
