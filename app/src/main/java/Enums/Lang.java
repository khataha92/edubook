package Enums;

/**
 * Created by mac on 6/24/16.
 */
public enum  Lang {

    English("en"),Arabic("ar");

    private String value;

    Lang(String str){

        value = str;
    }

    public String getValue() {

        return value;

    }
}
