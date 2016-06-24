package Enums;

/**
 * Created by mac on 6/24/16.
 */
public enum  Lang {

    English("English"),Arabic("Arabic");

    private String value;

    Lang(String str){

        value = str;
    }

    public String getValue() {

        return value;

    }
}
