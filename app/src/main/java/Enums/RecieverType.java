package Enums;

/**
 * Created by lap on 6/15/16.
 */
public enum RecieverType {

    GROUPS("groups"),ALL_GROUPS("all_groups"),ONLY_ME("users"),ALL_USERS("all_users"),STUDENTS("users");

    private String value;

    RecieverType(String val){

        value = val;
    }

    public String getValue() {

        return value;

    }
}
