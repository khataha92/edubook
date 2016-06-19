package Enums;

/**
 * Created by lap on 6/15/16.
 */
public enum GroupUserStatus {

    READ_ONLY(1),MEMBER(0);

    private int status;

    GroupUserStatus(int state){

        status = state;
    }

    public int getStatus() {

        return status;

    }
}
