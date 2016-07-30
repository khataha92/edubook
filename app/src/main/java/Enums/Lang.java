package Enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mac on 6/24/16.
 */
public enum  Lang {

    English("en"),Arabic("ar");

    private String value;

    static Map<String,Lang> map = new HashMap<>();

    static {

        map.put("ar",Arabic);

        map.put("en",English);

    }

    public static Lang getValueOf(String lang){

        return map.get(lang);

    }

    Lang(String str){

        value = str;
    }

    public String getValue() {

        return value;

    }
}
