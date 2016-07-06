package Enums;

/**
 * Created by lap on 6/15/16.
 */
public enum GroupUserStatus {

    READ_ONLY(3),MEMBER(2);

    private int status;

    GroupUserStatus(int state){

        status = state;
    }

    public int getStatus() {

        return status;

    }

    public static GroupUserStatus getStatusWithCode(int code){

        if(code == 2) return MEMBER;

        return READ_ONLY;

    }
}
