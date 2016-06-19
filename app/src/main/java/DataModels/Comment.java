package DataModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lap on 6/12/16.
 */
public class Comment {

    String id;

    String comment;

    @SerializedName("created_at")
    String createdAt;

    String age;

    User creator;

    public String getId() {

        return id;

    }

    public void setId(String id) {

        this.id = id;

    }

    public String getComment() {

        return comment;

    }

    public void setComment(String comment) {

        this.comment = comment;

    }

    public String getCreatedAt() {

        return createdAt;

    }

    public void setCreatedAt(String createdAt) {

        this.createdAt = createdAt;

    }

    public String getAge() {

        return age;

    }

    public void setAge(String age) {

        this.age = age;

    }

    public User getCreator() {

        return creator;

    }

    public void setCreator(User creator) {

        this.creator = creator;

    }
}
