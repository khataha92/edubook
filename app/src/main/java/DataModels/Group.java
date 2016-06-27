package DataModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lap on 3/26/16.
 */
public class Group {
    String id, name, description,institute,groupClass;

    @SerializedName("user_id")
    String userId;

    public void setUserId(String userId) {

        this.userId = userId;

    }

    public String getUserId() {

        return userId;

    }

    public String getId() {

        return id;

    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getGroupClass() {
        return groupClass;
    }

    public void setGroupClass(String groupClass) {
        this.groupClass = groupClass;
    }
}
