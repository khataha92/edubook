package Enums;

/**
 * Created by lap on 6/19/16.
 */
public enum ResponseCode{

    SUCCESS(200),FAILED(500);

    int code;

    ResponseCode(int code){

        this .code = code;
    }

    public int getCode() {

        return code;

    }
}
