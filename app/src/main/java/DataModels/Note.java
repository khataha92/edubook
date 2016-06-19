package DataModels;

import com.google.gson.annotations.SerializedName;


public class Note {

    @SerializedName("post_id")
    int id;

    String description;

    public void setId(int id) {

        this.id = id;

    }

    public void setDescription(String description) {

        this.description = description;

    }

    public int getId() {

        return id;

    }

    public String getDescription() {

        return description;

    }
}

