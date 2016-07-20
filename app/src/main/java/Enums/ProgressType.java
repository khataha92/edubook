package Enums;

import com.google.gson.annotations.SerializedName;

/**
 * Created by KhalidTaha on 7/20/16.
 */
public enum  ProgressType {

    @SerializedName("assignment")
    ASSIGNMENT("assignment"),

    @SerializedName("absence")
    ABSENCE("absence"),

    @SerializedName("late")
    LATE("late"),

    @SerializedName("exam")
    EXAM("exam"),

    @SerializedName("excused")
    EXCUSED("exccused");

    public String val;

    ProgressType(String value){

        val = value;

    }

    public String getVal() {

        return val;

    }
}
