package DataModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lap on 6/12/16.
 */
public class Assignment {

    String title;

    String description;

    String duedate;

    @SerializedName("lock")
    String lockAfterDueDate;

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

    public String getDuedate() {

        return duedate;

    }

    public void setDuedate(String duedate) {

        this.duedate = duedate;

    }

    public String getLockAfterDueDate() {

        return lockAfterDueDate;

    }

    public void setLockAfterDueDate(String lockAfterDueDate) {

        this.lockAfterDueDate = lockAfterDueDate;

    }
}
