package Enums;

import android.content.Context;

import UserUtils.Application;
import edubook.edubook.R;

/**
 * Created by lap on 6/15/16.
 */
public enum ErrorType {

    TOKEN_EXPIRED("token_expired"),
    OLD_PASS_EQUALS_NEW(Application.getContext().getString(R.string.old_password_equals_new)),
    VALID("valid"),
    NEW_PASS_NOT_EQUAL_CONFIRMATION(Application.getContext().getString(R.string.new_password_not_equal_confirmation)),
    NEW_PASSWORD_LENGHT(Application.getContext().getString(R.string.password_length)),
    WRONG_CURRENT_PASSWORD(Application.getContext().getString(R.string.wrong_current_password))
    ;

    private Context context = Application.getContext();

    private String value;

    ErrorType(String val){

        this.value = val;
    }

    public String getValue(){

        return value;
    }


}
