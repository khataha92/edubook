package Enums;

/**
 * Created by lap on 6/15/16.
 */
public enum ErrorType {

    TOKEN_EXPIRED("token_expired");

    private String value;

    ErrorType(String val){

        this.value = val;
    }

    public String getValue(){

        return value;
    }


}
